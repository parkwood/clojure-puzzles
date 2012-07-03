(ns euler12)

(defn divisors [n]
  (let [half (int (/ n 2))]
    (reduce (fn [known next] (if (= 0  (mod n next)) (conj known next) known)) [] (range 1 half))))

(def triangles (map second (iterate (fn
[ [n tot]] [(inc n) (+ n tot)]) [2 1] )))

(comment  (some #(> (count (divisors %)) 500) (map second (iterate (fn
                                                                               [ [n tot]] [(inc n) (+ n tot)]) [2 1] ))))

