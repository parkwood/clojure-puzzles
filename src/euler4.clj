(ns euler4)

; start at (* 999 999) => 998001. highest pali 997 799

(def pal  (fn [prev] (let [new-first-half (dec prev)
                          second-half-builder (StringBuilder.)
                          reversed-second-half (reduce #(.append %1 %2) second-half-builder (reverse (str new-first-half)))]
                      (java.lang.Integer/valueOf (str new-first-half reversed-second-half)))))

(defn three-digit-factor [n]
  (some  #(if (and
               (= 0 (mod n %1))
               (< (/ n %1) 1000))
            [%1  (/ n %1) n])
         (take-while #(> %1 99) (iterate dec 1000))))

(defn pali-with-2-three-digit-factors []
  (some three-digit-factor (map pal (iterate dec 997))))

