(ns bowling)

(defn null+ [& args]
  (apply + (filter identity args)))

(defn- game
  ([all-bowls-so-far]
     (loop [[first-in-frame second-in-frame & remaining-bowls :as bowls] all-bowls-so-far
            score 0
            frame-count 1]
       ;(prn "point" score remaining-bowls)
       (if (or (nil? first-in-frame) (= 11 frame-count))
         score
         (if (= 10 first-in-frame)
           (recur (rest bowls) (apply null+ score 10 second-in-frame (first remaining-bowls)) (inc frame-count))
           (recur
            remaining-bowls
            (cond
             (= 10 (null+ first-in-frame second-in-frame)) (apply null+ score 10 (first remaining-bowls))
             :else             (null+ score first-in-frame second-in-frame)
                  )
             (inc frame-count))
             ))))
  ([points-tally score]
     (partial game (conj points-tally score) )))

(defn new-game []
  (partial game []))