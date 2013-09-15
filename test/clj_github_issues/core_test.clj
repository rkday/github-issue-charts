(ns clj-github-issues.core-test
  (:require [clojure.test :refer :all]
            [clj-github-issues.core :refer :all]
            [tentacles.issues :as ghi]
            [clj-github-issues.test-data :refer :all]))

(deftest test-convert-potential-date
  (is (= (convert-potential-date "biscuit")
         "biscuit"))
  (is (= (convert-potential-date "2013-09-14T10:56:41")
         "2013-09-14T10:56:41"))
  (is (= (convert-potential-date "2013-09-14T10:56:41Z")
         "2013-09-14"))
  (is (= (convert-potential-date "2013-09-14")
         "2013-09-14")))

(defn dummy-ghi-issues [user repo options]
  (if (= (:state options) "closed")
    test-data-closed
    test-data-open))

(deftest test-get-issue-data
  (with-redefs-fn {#'ghi/issues dummy-ghi-issues}
    #(is (= (get-issue-data "dummy" "dummy")
           {:new {"2013-09-14" 1 "2013-09-12" 3}
            :closed {"2013-09-12" 2}
            :total {"2013-09-12" 1 "2013-09-14" 2}} ))))

(deftest test-issue-data-for-each-date
  (with-redefs-fn {#'ghi/issues dummy-ghi-issues}
    #(is (= (issue-data-for-each-date "dummy" "dummy")
            [["2013-09-12" 3 2 1]
             ["2013-09-14" 1 0 2]]))))
