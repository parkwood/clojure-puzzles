(ns euler5)

(def denominators (reverse (range 2 11)))
(comment 20 [232792560 12326489] )

(defn smallest-denominator [[largest & numerators]]
  (let [ops (atom 0)]
    (loop [[current-multiple & multiples] (map #(* largest %1) (iterate inc 1))]
      (if (= (count numerators)
             (count (take-while #(do (swap! ops inc) (if (= 0 (mod current-multiple %1)) %1))  numerators)))
        [current-multiple @ops]
        (recur multiples)
        ))))
(comment 
  2 4 6 8
  3 6 9 12
  4 8 12 16

  5 10 45 50 90
  9 18 27  45 54 90)
