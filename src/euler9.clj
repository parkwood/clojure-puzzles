(ns euler9)

(defn sq [x] (Math/pow x 2))

(comment c is less than half of (+ a b c)
         a and b must be less than c )

(defn gen-sum-constituents [x]
  (take-while (fn [[a _]] (>= a (/ x 2))) (iterate (fn [[a b]] [ (dec a) (inc b) ]) [ (dec x) 1 ])))

(defn gen-as-and-bs [c]
  (let [sum-bc (- 1000 c)] (gen-sum-constituents sum-bc)))

(defn ab-less-than-c [c [a b]]
  (< a c))

(comment [[375 200] 425])
(def cs (range 1 499))
(some #(some
        (fn [ab] (if  (= (sq %) (apply + (map sq ab))) [ab % ] ))
        (filter (partial  ab-less-than-c %)  (gen-as-and-bs %)))
      (reverse  cs))