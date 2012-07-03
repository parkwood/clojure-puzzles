(ns euler18)


(def triangle
"75
95 64
17 47 82
18 35 87 10
20 04 82 47 65
19 01 23 75 03 34
88 02 77 73 07 63 67
99 65 04 28 06 16 70 92
41 41 26 56 83 40 80 70 33
41 48 72 33 47 32 37 16 94 29
53 71 44 65 25 43 91 52 97 51 14
70 11 33 28 77 73 17 78 39 68 17 57
91 71 52 38 17 14 91 43 58 50 27 29 48
63 66 04 68 89 53 67 30 73 16 69 87 40 31
04 62 98 27 23 09 70 98 73 93 38 53 60 04 23")

(defn split-space [row]
  (.split row " "))

(defn triangle-row [split-row]
  (vec (map #(let [le-num (Integer/valueOf %)] ( hash-map :val le-num  :max le-num :route [le-num])) split-row)))

(defn read-triangle [triangle]
  (map (comp  triangle-row split-space) (.split triangle "\n")))

(defn get-children [idx row]
  [(row idx) (row (inc idx))])

(defn get-max-child [[c1 c2 :as children]]
  (if (> (:max c1) (:max c2))
    c1 c2))

(defn replace-row [ row-below row]
  (vec (map-indexed (fn [idx {:keys [val max]}]
                      (let [children (get-children idx row-below)
                            max-child (get-max-child children)]
                        {:val val :max (+ val (:max max-child)) :route (conj  (:route max-child)  val )}))  row)))

(defn max-route [tri]
  (reduce replace-row (reverse tri)))

(defn answer [t]
  (let [tri (read-triangle t)]
    ;(max-route tri)
    (->> (max-route tri) first :route  reverse (apply +))))