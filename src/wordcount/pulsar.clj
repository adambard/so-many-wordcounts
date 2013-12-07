;; Implement parallel word count in pulsar
;; 1) Each actor is assigned a page
;; 2) The actor reads the page
;; 3) The actor sends the result to the master counter actors
;; 4) The counter prints the word count and quits upon receiving results
;;    from all pages.
(ns wordcount.pulsar
  (:require [wordcount.utils :refer :all]
            [co.paralleluniverse.pulsar.core :refer [defsfn]]
            [co.paralleluniverse.pulsar.actors :refer [receive ! spawn] ]))

(defsfn wc-actor []
  (receive [:page page counter]
           (let [count (wc (text page))]
             (! counter :count page count))))

(defsfn counter-actor [expected]
  (let [received (atom 0)
        word-count (atom 0)]
    (while (< @received expected)
      (receive
       [:count page count]
       (do
         (println page ": " count)
         (swap! word-count + count)
         (swap! received inc))))
    (println "Word count: " @word-count)))


(defn -main []
  (let [counter (spawn counter-actor (count pages))]
    (doseq [page pages]
      (! (spawn wc-actor) :page page counter))))
