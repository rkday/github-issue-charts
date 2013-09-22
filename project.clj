(defproject clj-github-issues "0.1.0"
  :description "A Clojure webapp for showing charts of Github issue counts over time"
  :url "http://github.com/rkday/github-issue-charts"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-time "0.6.0"]
                 [tentacles "0.2.5"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [compojure "1.1.5"]
                 [environ "0.4.0"]
                 [org.clojure/core.memoize "0.5.6"]]
  :plugins [[quickie "0.2.1"]
            [lein-ring "0.8.7"]]
  :ring {:handler clj-github-issues.web/handler})
