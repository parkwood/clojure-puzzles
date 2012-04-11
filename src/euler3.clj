(ns euler3)

(comment non-idiomatic but still cool to know lazy-cat  (def naturals 
                                                          (lazy-cat [0] (map inc naturals))))

(defn fibo [] "best fibonacci. head not retained"
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1]))) 

(defn first-unmarked [coll prev-p]
  (some #(if (> %1 prev-p) %1) coll))

(defn mark [p coll]
  (let [p-sq (* p p)]
    (filter #(or (< %1 p-sq) (not (= 0 (mod %1 p))))  coll)
    ))

(comment)
(defn primes [n]
             (let [numbers-containing-primes (range 3 n 2)]
               (loop [remaining numbers-containing-primes
                      p 3]
                 (prn p)
                 (if (> (* p p) n) remaining ;(not p) 
                     (let [marked-numbers (mark p remaining)]
                       ;(prn p)
                       (recur marked-numbers (first-unmarked marked-numbers p)))))))


(defn largest-prime-factor [n]
  (let [primes (primes (int (Math/sqrt n)))]
    (some #(if (= 0 (mod n %1)) %1)  (reverse primes))))

