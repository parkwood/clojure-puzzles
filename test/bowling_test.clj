(ns bowling-test
  ( :use [clojure.test]
         [bowling]))

(deftest check-score-starts-at-0
  (is (= 0 ( (new-game)))))

(deftest check-score-after-strike-contains-next-two-points
  (is (= 28 (((( (new-game) 10) 4 ) 5)))))

(deftest check-score-after-knocking-all-pins-down
  (is (= 20 (((( (new-game) 6) 4 ) 5)))))

(deftest perfect-game
  (is (= 300 ((reduce (fn [game score] (game score)) (new-game) (take 12 (repeat 10)) )))))

(deftest hearbreak-game
  (is (= 299 (((reduce (fn [game score] (game score)) (new-game) (take 11 (repeat 10)))9 )))))
(run-tests)