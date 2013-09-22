(ns clj-github-issues.web
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-github-issues.core :as core]
            [clojure.java.io :as io]))

(defroutes handler
  (GET "/" [] "Hello world!")
  (GET "/:user/graph" [user]
       (slurp (io/resource "graph.html")))
  (GET "/:user/csv" [user repo]
       {:status 200
        :headers {"Content-Type" "text/plain; charset=utf-8"}
        :body (core/to-csv (core/issue-data-for-each-date user nil))})
  (GET "/:user/:repo/graph" [user repo]
       (slurp (io/resource "graph.html")))
  (GET "/:user/:repo/csv" [user repo]
       {:status 200
        :headers {"Content-Type" "text/plain; charset=utf-8"}
        :body (core/to-csv (core/issue-data-for-each-date user repo))})
  (route/resources "/"))
