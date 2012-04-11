(ns tictactoe)

(defn lines [[first second third :as rows]]
  (let [horizontal [first second third]
        vertical (apply map (fn [& args] (apply vector args)) rows)
        diagonaler (fn [diagonal row] (conj diagonal (row (count diagonal))))
        l-diagonal (reduce diagonaler  [] rows)
        r-diagonal (reduce diagonaler  [] (map (comp vec reverse ) rows))
        ]
    
    (concat [r-diagonal] [l-diagonal] horizontal vertical)))

