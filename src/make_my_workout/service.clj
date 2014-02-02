(ns make-my-workout.service
	(:require [io.pedestal.service.http :as bootstrap]
		[io.pedestal.service.http.route :as route]
		[io.pedestal.service.http.body-params :as body-params]
		[io.pedestal.service.http.route.definition :refer [defroutes]]
		[ring.util.response :as ring-resp]
                [clojure.data.json :as json]
		[selmer.parser :as selmer-parser]
		[make-my-workout.peer :as peer :refer [
			workouts muscle_groups exercises workout_exercises]]))

(defn about-page
  "The About Page"
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))
(defn workout-exercise-for-json
  "Takes a query result for a workout_exercise and shapes it for json"
  [workout_exercise]
  {:workout (nth workout_exercise 0)
   :muscleGroup (nth workout_exercise 1)
   :exercise (nth workout_exercise 2)})

(defn home-page
  "The Home Page"
  [request]
  (ring-resp/response
   (selmer-parser/render-file "home.html" {:workout_exercises (str (workout_exercises))})))

(defn workout-exercise
  "REST end-point for workout-exercise"
  [request]
  (ring-resp/response
   (json/write-str (map workout-exercise-for-json (seq (workout_exercises))))))

(defroutes routes
        [[["/" {:get home-page}
           ;; Set default interceptors for /about and any other paths under /
           ^:interceptors [(body-params/body-params) bootstrap/html-body]
           ["/about" {:get about-page}]
           ["/workout-exercise" {:get workout-exercise}]]]])

;; Consumed by make-my-workout.server/create-server
;; See bootstrap/default-interceptors for additional options you can configure
(def service "Service" {:env :prod
	;; You can bring your own non-default interceptors. Make
	;; sure you include routing and set it up right for
	;; dev-mode. If you do, many other keys for configuring
	;; default interceptors will be ignored.
	;; :bootstrap/interceptors []
	::bootstrap/routes routes

	;; Uncomment next line to enable CORS support, add
	;; string(s) specifying scheme, host and port for
	;; allowed source(s):
	;;
	;; "http://localhost:8080"
	;;
	;;::bootstrap/allowed-origins ["scheme://host:port"]

	;; Root for resource interceptor that is available by default.
	::bootstrap/resource-path "/public"

	;; Either :jetty or :tomcat (see comments in project.clj
	;; to enable Tomcat)
	;;::bootstrap/host "localhost"
	::bootstrap/type :jetty
	::bootstrap/port 8080})
