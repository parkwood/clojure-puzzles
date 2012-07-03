(ns bowling
  ( :use [clojure.test]
         ))

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


(defn create-frame [[ a b & remainder :as frames]]
  (let [[bowls-to-take bowls-to-drop]
        (cond
               (nil? b)      [1 1]
               (= 10 a)      [3 1]
               (= 10 (+ a b)) [3 2]
               :else         [2 2])]
    { :frame (take bowls-to-take frames) :remaining-bowls (drop bowls-to-drop frames)})
  )


(defn create-frames [points-tally]
                                        ;(lazy-seq)
  (if (seq? (seq  points-tally))
    (let [{ :keys [frame remaining-bowls]} (create-frame points-tally)]
      (cons frame (create-frames remaining-bowls)))
    []))

(defn- game2
  ([points-tally]
                                        ;(prn points-tally)
     (let [frames (take 10 (create-frames points-tally))
           scored-frames (map (partial apply +) frames)]
       (prn scored-frames)
       (apply + scored-frames)
       ))
  ( [points-tally score]
      (partial game2 (conj points-tally score))))

(defn new-game2 []
  (partial game2 []))

(deftest check-score-starts-at-0
  (is (= 0 ( (new-game2)))))

(deftest check-score-after-strike-contains-next-two-points
  (is (= 28 (((( (new-game2) 10) 4 ) 5)))))

(deftest check-score-after-knocking-all-pins-down
  (is (= 20 ((reduce #(%1 %2) (new-game2) [6 4 5 0])))))

(deftest perfect-game
  (is (= 300 ((reduce (fn [game score] (game score)) (new-game2) (take 12 (repeat 10)) )))))

(deftest hearbreak-game
  (is (= 299 (((reduce (fn [game score] (game score)) (new-game2) (take 11 (repeat 10)))9 )))))

(defn x [a]
  (cond
   (= 1 a) 2
   (= 2 a) 3
   :else   0))

(defn create-frame-points
  ([[]] {:points-for-frame [] :remaining-points []})
  ([[x]]  {:points-for-frame [x] :remaining-points []})
  ([[x y & remaining :as points]]))

(defn create-frames [points]
  (let [{:keys [points-for-frame remaining-points]} (create-frame-points points)]
    (cons points-for-frame (create-frames remaining-points))))

(defn bowl-score [points]
  (let [frames-with-points (take 10  (create-frames points))]
    (apply + (flatten frames-with-points))))

(deftest check-score-starts-at-0
  (is (= 0  (bowl-score []))))

(deftest check-score-after-strike-contains-next-two-points
  (is (= 28  (bowl-score [ 10 4  5])))

  (deftest check-score-after-knocking-all-pins-down
    (is (= 20  (bowl-score [6 4 5 0])))))

(deftest perfect-game
  (is (= 300  (bowl-score (take 12 (repeat 10))) )))

(deftest hearbreak-game
  (is (= 299  (bowl-score (take 11 (repeat 10)))9 )))
