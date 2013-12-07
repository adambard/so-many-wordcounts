(defproject wordcount "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [co.paralleluniverse/pulsar "0.3.0"]
                 [lamina "0.5.0"]
                 [org.clojure/core.async "0.1.242.0-44b1e3-alpha"]
                 ]
  :java-agents [[co.paralleluniverse/quasar-core "0.3.0"]]
:main "wordcount.lamina")
