(ns clj-github-issues.core
  (:require [clj-github-issues.pure :refer :all]
            [clj-time.format :as tf]
            [clojure.edn :as edn]
            [clojure-csv.core :as csv]
            [tentacles.issues :as ghi]
            [tentacles.repos :as ghr]
            [environ.core :refer [env]]
            [clojure.core.memoize :refer [ttl]]
            ))

(def auth (env :github-credentials))
(def oauth (env :github-token))

(defn convert-potential-date [potential-date]
  "If potential-date is a string in the '2013-09-14T10:56:41Z' Github
date format, converts it to a simple '2013-09-14' format. Otherwise,
returns potential-date untouched."
  (let [github-date (tf/formatters :date-time-no-ms)
        ymd (tf/formatters :year-month-day)]
    (try
      (tf/unparse ymd (tf/parse github-date potential-date))
      (catch Exception e
        potential-date))))

(defn issues-for-repo [user repo state]
  ; Cache the Github issue data for an hour (3,600,000 ms)
  (let [data (ghi/issues user repo
                              {:state state
                               :oauth_token oauth
                               :all-pages 1})
        is-valid (:created_at (second data))]
    (if is-valid
      (map #(update-values convert-potential-date %1) data)
      [])))

(def cached-issues-for-repo (ttl issues-for-repo :ttl/threshold 3600000))

(defn all-repos-for-user [user state]
  (let [data (ghr/user-repos user {:oauth_token oauth :all-pages 1})
        repo-names (remove nil? (map :name data))]
    (flatten (pmap #(cached-issues-for-repo user %1 state)
                   repo-names))))

(defn get-github-data-in-state [user repo state]
  (if repo
    (cached-issues-for-repo user repo state)
    (all-repos-for-user user state)))

(defn get-issue-data [user repo]
  (let [raw-open-data (get-github-data-in-state user repo "open")
        raw-closed-data (get-github-data-in-state user repo "closed")
        open_created (count-of-key :created_at raw-open-data)
        closed_created (count-of-key :created_at raw-closed-data)
        closed_closed (count-of-key :closed_at raw-closed-data)
        all_created (add-two-maps open_created closed_created)
        total_increase (add-two-maps (negative closed_closed) all_created)]
    {:new all_created
     :closed closed_closed
     :total (into {} (periodic-to-cumulative (sort-by-key total_increase)))}))

(defn issue-data-for-each-date [user repo]
  (let [data (get-issue-data user repo)
        new_i (:new data)
        closed (:closed data)
        total (:total data)
        all-dates (->> (keys total)
                       (into (keys closed))
                       (into (keys new_i))
                       (distinct)
                       (remove nil?))]
    (->> all-dates
         (map (fn [k]
                [k
                 (new_i k 0)
                 (closed k 0)
                 (total k)]))
             (sort-by first))))

(defn to-csv [ss]
  (csv/write-csv (conj  (map #(map str %1) ss) ["Date" "New" "Closed" "Total"])))

