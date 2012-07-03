(ns euler6)

(def answer  (- (Math/pow  (apply + (range 1 101)) 2)
                (apply + (map #(Math/pow % 2) (range 1 101)))
                ))
