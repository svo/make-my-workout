(ns make-my-workout.peer
	(:require [datomic.api :as d :refer (q)]))

(def uri "datomic:mem://make-my-workout")

(def schema-tx (read-string (slurp "resources/schema.edn")))
(def data-tx (read-string (slurp "resources/seed-data.edn")))

(defn init-db []
	(when (d/create-database uri)
		(let [conn (d/connect uri)]
			@(d/transact conn schema-tx)
			@(d/transact conn data-tx))))

(defn results []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :make_my_workout/description ?c]] (d/db conn))))
