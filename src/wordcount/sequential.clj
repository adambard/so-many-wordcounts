;; The usual way of obtaining the desired result. Plus, reducers!
(ns wordcount.sequential
  (:require [wordcount.utils :refer :all]
            [clojure.core.reducers :as r]))

(defn page-count [page]
  (let [count (wc (text page))]
    (println page ": " count)
    count))

(defn main- []
  (println "Word Count:" (r/fold + (r/map page-count pages))))
