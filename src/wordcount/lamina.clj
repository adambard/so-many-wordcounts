;; Implement word count with channels
;; 1) Send the pages over a channel
;; 2) Two workers consume from the channels and send counts to a count channel
;; 3) A counter worker consumes counts from the channel and prints overall word counts
(ns wordcount.lamina
  (:require [wordcount.utils :refer :all]
            [lamina.core :refer [channel enqueue read-channel]]))

(def pages-chan (channel))
(def counts-chan (channel))

(defn pages-worker []
  (loop [page @(read-channel pages-chan)]
    (enqueue counts-chan [page (wc (text page))])
    (recur @(read-channel pages-chan))))

(defn counter-worker [expected]
  (let [received (atom 0)
        word-count (atom 0)]
    (loop [[page page-count] @(read-channel counts-chan)]
      (swap! word-count + page-count)
      (swap! received inc)
      (println page ": " page-count)
      (if (< @received expected)
        (recur @(read-channel counts-chan))
        ))
    @word-count))

(defn -main []
  (future (pages-worker))
  (future (pages-worker))
  (doseq [page pages]
    (enqueue pages-chan page))
  (println "Word Count:" 
           @(future (counter-worker (count pages)))))
