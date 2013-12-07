;; Core.async word count
;; 1) (Asynchronously) dump all the word counts into a channel
;; 2) Read 'em back out and sum them up
;; 3) Print the result
(ns wordcount.async
  (:require [wordcount.utils :refer :all]
            [clojure.core.async :refer [chan >! <! go <!!]]))

(defn put-pages
  "Put word counts into the channel <counts>"
  [counts]
  (doseq [page pages]
    (go (let [count (wc (text page))]
          (println page ": " count)
          (>! counts count)))))

(defn count-words
  "Read word counts from the channel <counts>"
  [counts]
  (let [word-count (atom 0)]
    (doseq [_ pages]
      (<!!
       (go (swap! word-count + (<! counts)))))
    word-count))

(defn -main []
  (let [counts (chan)]
    (put-pages counts)
    (println "Word Count: " (count-words counts))))
