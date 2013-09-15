(ns clj-github-issues.pure-test
  (:require [clojure.test :refer :all]
            [clj-github-issues.pure :refer :all]))

(deftest test-update-values
  (is (= (update-values #(+ 3 %1) {:a 1, :b 2, :c 3, :d 4}))
      {:a 4, :b 5, :c 6, :d 7}))

(deftest test-negative
  (is (= (negative {:a 1, :b 2, :c 3, :d 4}))
      {:a -1, :b -2, :c -3, :d -4}))

(deftest test-periodic-to-cumulative
  (is (= (periodic-to-cumulative {"2007" 2 "2008" 3 "2009" 2})
         [["2007" 2] ["2008" 5] ["2009" 7]]))
  (is (= (periodic-to-cumulative {"2007" 2 "2008" -1 "2009" 1})
         [["2007" 2] ["2008" 1] ["2009" 2]])))

(deftest test-count-of-key
  (let [test-data [{:opened_at "01-01-2007" :closed_at "08-01-2007"}
                   {:opened_at "01-01-2007" :closed_at "04-01-2007"}
                   {:opened_at "06-01-2007" :closed_at "08-01-2007"}]]
    (is (= (count-of-key :opened_at test-data)
           {"01-01-2007" 2, "06-01-2007" 1}))
    (is (= (count-of-key :closed_at test-data)
           {"08-01-2007" 2, "04-01-2007" 1}))))
