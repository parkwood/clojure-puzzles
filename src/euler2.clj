(ns euler2)

(defn fib [n]
  (let [a (atom 0)
        b (atom 1)]
    (take-while #(< %1 n)
                (repeatedly (fn []
                              (let [new-b (+ @a @b)
                                    new-a @b]
                                ;(prn @a @b)
                                (swap! a #(second %&) new-a)
                                (swap! b #(second %&) new-b)))))))

(defn fib-even-sum [n]
         (apply + (filter even? (fib n))))