(ns wordcount.utils
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def pages ["page1.txt" "page2.txt" "page3.txt" "page4.txt"])

(defn text [filename]
  (slurp (io/resource filename)))

(defn wc [text]
  (count (str/split text #"\s" )))

(defmacro time- [msg & body]
  `(let [tick# (System/currentTimeMillis)
         return# (do ~@body)]
     (println ~msg ": " (- (System/currentTimeMillis) tick#) "ms")
     return#))
