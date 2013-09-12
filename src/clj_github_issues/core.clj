(ns clj-github-issues.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clj-time.format :as tf]
            [clojure.string :as string]
            [clostache.parser :as stache]
            ))

(def built-in-formatter (tf/formatters :date-time-no-ms))

(def ymd-formatter (tf/formatters :year-month-day))

(defn convert-times [m]
  (map (fn [[k v]] (if (and v (some #{k} ["created_at" "closed_at" "updated_at"]))
                    [k (tf/unparse ymd-formatter (tf/parse built-in-formatter v))]
                    [k v]))
       m))

(defn get-right-keys [m]
  (into (empty m) (convert-times
                   (select-keys m ["created_at"
                                   "closed_at"]))))

(defn map-map [f m]
  (into (empty m) (map (fn [[k v]] [k (f v)]) m)))

(defn add-two-maps [map1 map2]
  (into map2 (map (fn [[k v]]
                    [k (+ v (map2 k 0))])
                  map1)))

(defn negative [m]
  (map-map #(* -1 %1) m))

(defn periodic-to-cumulative
  ([m accum blank]
     (if (nil? m)
       blank
       (let [total (+ accum (second (first m)))]
         (recur (next m) total (conj blank [(first (first m)) total])))))
  ([m]
     (periodic-to-cumulative m 0 [])))

(defn count-of-key [key m]
  (frequencies (map #(%1 key) m)))

(defn get-raw-github-data-in-state [state path]
  (map get-right-keys (json/read-str (:body
                                      (client/get
                                       (str "https://api.github.com" path "&state=" state))))))

(defn get-github-data [path]
  (let [raw-open-data (get-raw-github-data-in-state "open" path)
        raw-closed-data (get-raw-github-data-in-state "closed" path)
        open_created (count-of-key "created_at" raw-open-data)
        closed_created (count-of-key "created_at" raw-closed-data)
        closed_closed (count-of-key "closed_at" raw-closed-data)
        all_created (add-two-maps open_created closed_created)
        total_increase (add-two-maps (negative closed_closed) all_created)]
    {:new all_created
     :closed closed_closed
     :total (periodic-to-cumulative (sort-by first total_increase))}))

(defn to-js-data [path]
  (let [data (get-github-data path)
        new_i (into {} (:new data))
        closed (into {} (:closed data))
        total (into {}  (:total data))
        all-dates (set (into (keys new_i) (into (keys closed) (keys total))))
        data-for-each-date (sort-by first
                                    (map (fn [k] [k
                                                 (new_i k 0)
                                                 (closed k 0)
                                                 (total k)])
                                         all-dates))]
    (str (string/join "\n"
                      (map #(str \" %1 "\\n" \" "+")
                           (map #(string/join "," %1)
                                data-for-each-date)))
         "\"\"")))

(comment
  (stache/render-resource "../resources/test.html"
                          {:data (to-js-data
                                  "/repos/Metaswitch/sprout/issues")}))
