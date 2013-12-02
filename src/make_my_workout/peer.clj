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

(defn workouts []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :workout/label ?c]] (d/db conn))))

(defn muscle_groups []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :muscle_group/label ?c]] (d/db conn))))

(defn exercises []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :exercise/label ?c]] (d/db conn))))
