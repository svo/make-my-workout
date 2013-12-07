(ns make-my-workout.peer
	(:require [datomic.api :as d :refer (q)]))

(def uri "URI" "datomic:mem://make-my-workout")

(def schema-tx "Build schema" (read-string (slurp "resources/schema.edn")))
(def data-tx "Populate base data"
	(read-string (slurp "resources/seed-data.edn")))

(defn init-db "Initialise database" []
	(when (d/create-database uri)
		(let [conn (d/connect uri)]
			@(d/transact conn schema-tx)
			@(d/transact conn data-tx))))

(defn workouts "List workouts" []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :workout/label ?c]] (d/db conn))))

(defn muscle_groups "List muscle groups" []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :muscle_group/label ?c]] (d/db conn))))

(defn exercises "List exercises" []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?c :where [?e :exercise/label ?c]] (d/db conn))))

(defn workout_exercises "List workout exercises" []
	(init-db)
	(let [conn (d/connect uri)]
		(q '[:find ?w_label ?m_label ?e_label
			:where
			[?c :workout_exercise/workout ?w]
			[?w :workout/label ?w_label]
			[?c :workout_exercise/muscle_group ?m]
			[?m :muscle_group/label ?m_label]
			[?c :workout_exercise/exercise ?e]
			[?e :exercise/label ?e_label]] (d/db conn))))
