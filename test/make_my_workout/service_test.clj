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
		; (str "<html>\n<body>\n"
		; 	"Workout: [[&quot;Back and Chest&quot;],"
		; 	" [&quot;Biceps, Triceps and Shoulders&quot;],"
		; 	" [&quot;Legs and Abs&quot;]]"
		; 	"<br /><br />\nMuscle Group: [[&quot;Chest&quot;],"
		; 	" [&quot;Abs&quot;], [&quot;Legs&quot;], [&quot;Triceps&quot;],"
		; 	" [&quot;Back&quot;], [&quot;Shoulders&quot;],"
		; 	" [&quot;Biceps&quot;]]"
		; 	"<br /><br />\nExercise: [[&quot;Crunch&quot;],"
		; 	" [&quot;Barbell Bench Press&quot;],"
		; 	" [&quot;Running&quot;], [&quot;Pendlay Row&quot;],"
		; 	" [&quot;Arnold Press&quot;], [&quot;Barbell Squat&quot;],"
		; 	" [&quot;Concentration Curl&quot;], [&quot;Skullcrusher&quot;],"
		; 	" [&quot;Barbell Shrug&quot;]]"
		; 	"<br /><br /><br />\nCompleted Workout:"
		; 	" [[&quot;Biceps, Triceps and Shoulders&quot; &quot;Biceps&quot;"
		; 	" &quot;Concentration Curl&quot;]]\n</body>\n</html>")))
		(format (str "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\">
<html>
	<head>
		<title></title>
	</head>
	<body>
		<p>
			Workout: %s<br />
			<br />
			Muscle Group: %s<br />
			<br />
			Exercise: %s<br />
			<br />
			<br />
			Completed Workout: %s
		</p>
	</body>
</html>
")
(str "[[&quot;Back and Chest&quot;],"
	" [&quot;Biceps, Triceps and Shoulders&quot;], [&quot;Legs and Abs&quot;]]")
(str "[[&quot;Chest&quot;], [&quot;Abs&quot;], [&quot;Legs&quot;],"
	" [&quot;Triceps&quot;], ""[&quot;Back&quot;], [&quot;Shoulders&quot;],"
	" [&quot;Biceps&quot;]]")
(str "[[&quot;Crunch&quot;], [&quot;Barbell Bench Press&quot;],"
	" [&quot;Running&quot;], [&quot;Pendlay Row&quot;],"
	" [&quot;Arnold Press&quot;], [&quot;Barbell Squat&quot;],"
	" [&quot;Concentration Curl&quot;], [&quot;Skullcrusher&quot;],"
	" [&quot;Barbell Shrug&quot;]]")
(str "[[&quot;Biceps, Triceps and Shoulders&quot; &quot;Biceps&quot;"
	" &quot;Concentration Curl&quot;]]"))))

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
