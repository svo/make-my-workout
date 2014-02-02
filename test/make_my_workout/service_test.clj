(ns make-my-workout.service-test
	(:require [clojure.test :refer :all]
	          [io.pedestal.service.test :refer :all]
	          [io.pedestal.service.http :as bootstrap]
	          [make-my-workout.service :as service]))

(def service
	(::bootstrap/service-fn (bootstrap/create-servlet service/service)))

(deftest home-page-test
	(is (=
		(:body (response-for service :get "/"))
		(slurp "test-resources/home.html")))

	(is (=
		(:headers (response-for service :get "/"))
		{"Content-Type" "text/html;charset=UTF-8"})))

(deftest about-page-test
	(is (.contains
		(:body (response-for service :get "/about"))
		"Clojure 1.5"))
	(is (=
		(:headers (response-for service :get "/about"))
		{"Content-Type" "text/html;charset=UTF-8"})))

(deftest workout-exercise
  (is (=
       (:body (response-for service :get "/workout-exercise"))
       (slurp "test-resources/workout_exercises.json"))))
