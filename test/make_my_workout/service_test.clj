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
			"<html><body>Workout: [[\"Back and Chest\"], [\"Biceps, Triceps and Shoulders\"], [\"Legs and Abs\"]]<br /><br />Muscle Group: [[\"Chest\"], [\"Abs\"], [\"Legs\"], [\"Triceps\"], [\"Back\"], [\"Shoulders\"], [\"Biceps\"]]<br /><br />Exercise: [[\"Crunch\"], [\"Barbell Bench Press\"], [\"Running\"], [\"Pendlay Row\"], [\"Arnold Press\"], [\"Barbell Squat\"], [\"Concentration Curl\"], [\"Skullcrusher\"], [\"Barbell Shrug\"]]<br /><br /><br />Completed Workout: [[\"Biceps, Triceps and Shoulders\" \"Biceps\" \"Concentration Curl\"]]</body></html>"))
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
