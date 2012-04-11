(ns infinite-ascending)
                                        ; apply (use 'clojure.repl) on starting repl?

(defn ascendin [coll]
  (map first  (take-while
               (fn [[ x y]] (> y x))
               (map (fn [x y] [x y]) coll (rest coll)))))

