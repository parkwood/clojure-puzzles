(ns barber-shop)

(defn hair-cut [cut-count waiting-customers]
;(prn "waiting " cut-count @waiting-customers (.getName (Thread/currentThread)))
 
(if (dosync (if (> (count @waiting-customers) 0)
              (alter waiting-customers pop)))
  (do    
    (Thread/sleep 20)        
    (inc cut-count))     
  cut-count))

(defn go-to-wait-room [waiting-customers cut-count]
  (dosync (alter waiting-customers conj 1))
  (send cut-count hair-cut waiting-customers))

(defn run []
  (let [waiting-customers (ref (clojure.lang.PersistentQueue/EMPTY))
        cut-count (agent 0)
        thread-pool (java.util.concurrent.Executors/newCachedThreadPool)
        start (System/currentTimeMillis)
        attempted-cuts (atom 0)
        ]    
    (comment)  (while (< (- (System/currentTimeMillis) start) 5000)
                 (do
                   (Thread/sleep (+ 10 (rand-int 20)))
                   (swap! attempted-cuts inc)
                   (.submit thread-pool (partial go-to-wait-room waiting-customers cut-count)))
                 )
    (Thread/sleep 1000)
    (prn "attempted " @attempted-cuts "claimed " @cut-count))
  )