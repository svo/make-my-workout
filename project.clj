(defproject make-my-workout "0.0.1-SNAPSHOT"
	:description "Application that generates up randomised workouts"
	:url "http://workout.qual.is/"
	:license {:name "Apache License, version 2.0" :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

	:dependencies [[org.clojure/clojure "1.5.1"]
		[io.pedestal/pedestal.service "0.2.2"]
		[io.pedestal/pedestal.service-tools "0.2.2"]
		[com.datomic/datomic-free "0.8.4270"]

		;; Remove this line and uncomment the next line to
		;; use Tomcat instead of Jetty:
		[io.pedestal/pedestal.jetty "0.2.2"]
		;; [io.pedestal/pedestal.tomcat "0.2.2"]
	]

	:plugins [
		[lein-bikeshed "0.1.3"]
		[lein-vanity "0.2.0"]
		[lein-clique "0.1.1"]
		[lein-cloverage "1.0.2"]
	]

	:min-lein-version "2.0.0"

	:resource-paths ["config", "resources"]

	:aliases {"run-dev" ["trampoline" "run" "-m" "make-my-workout.server/run-dev"]}

	:repl-options  {:init-ns user
		:init (try
			(use 'io.pedestal.service-tools.dev)
			(require 'make-my-workout.service)
			;; Nasty trick to get around being unable to reference non-clojure.core symbols in :init
			(eval '(init make-my-workout.service/service #'make-my-workout.service/routes))
			(catch Throwable t
				(println "ERROR: There was a problem loading io.pedestal.service-tools.dev")
				(clojure.stacktrace/print-stack-trace t)
				(println)))
		:welcome (println "Welcome to pedestal-service! Run (tools-help) to see a list of useful functions.")}

	:main ^{:skip-aot true} make-my-workout.server)
