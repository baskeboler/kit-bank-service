(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require [baskeboler.kit-bank-service.config :as config]
            [clojure.pprint]
            [clojure.spec.alpha :as s]
            [clojure.tools.namespace.repl :as repl] ;; benchmarking
            [expound.alpha :as expound]
            [integrant.core :as ig]
            [integrant.repl :refer [go reset]]
            [integrant.repl.state :as state]
            [lambdaisland.classpath.watch-deps :as watch-deps] ;; hot loading for deps
            [migratus.core :as migratus]))

;; uncomment to enable hot loading for deps
(watch-deps/start! {:aliases [:dev :test]})

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn dev-prep!
  []
  (integrant.repl/set-prep! (fn []
                              (-> (config/system-config {:profile :dev})
                                  (ig/prep)))))

(defn test-prep!
  []
  (integrant.repl/set-prep! (fn []
                              (-> (config/system-config {:profile :test})
                                  (ig/prep)))))

;; Can change this to test-prep! if want to run tests as the test profile in your repl
;; You can run tests in the dev profile, too, but there are some differences between
;; the two profiles.
(dev-prep!)

(repl/set-refresh-dirs "src/clj")

(def refresh repl/refresh)

(comment
  (go)
  (reset))

(defn create-migration [n]
  (migratus/create (:db.sql/migrations state/system) n))
