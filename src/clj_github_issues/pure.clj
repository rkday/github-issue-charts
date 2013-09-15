(ns clj-github-issues.pure)

(defn update-values [f m]
  (into (empty m) (map (fn [[k v]] [k (f v)]) m)))

(defn add-two-maps [map1 map2]
  (into map2 (map (fn [[k v]]
                    [k (+ v (map2 k 0))])
                  map1)))

(defn negative [m]
  (update-values #(* -1 %1) m))

(defn sort-by-key [m]
  (sort-by first m))

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
