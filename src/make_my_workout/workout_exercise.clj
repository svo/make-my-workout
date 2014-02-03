(ns make-my-workout.workout-exercise
  (:require
   [ring.util.response :as ring-resp]
   [clojure.data.json :as json]
   [make-my-workout.peer :as peer :refer [workout_exercises]]))

(defn workout-exercise-for-json
  "Takes a query result for a workout_exercise and shapes it for json"
  [workout_exercise]
  {:workout (nth workout_exercise 0)
   :muscleGroup (nth workout_exercise 1)
   :exercise (nth workout_exercise 2)})

(defn workout-exercise
  "REST end-point for workout-exercise"
  [request]
  (ring-resp/response
   (json/write-str (map workout-exercise-for-json (seq (workout_exercises))))))
