(ns dotnet

  "Dotnet related tasks such as compiling and testing on the clr using `magic` and `nostrand`

  ## Motivation

  This namespace is dedicated to
  - compiling the `test.check` project into .NET dll
  - running the `test.check` tests on the cl

  ## Use case

  To compile a project from clojure to .net, we need the `nasser/magic` project.
  Another project called `nasser/nostrand` was made to simplify the compiler setup and use.

  `nasser/nostrand` runs a clojure function that describes the files to be compiled."

  (:require [clojure.test :refer [run-all-tests]]))

(def tc-namespaces
  '[ ;; SRC
    clojure.test.check
    ;; TEST
    clojure.test.check.clojure-test-test
    clojure.test.check.random-test
    clojure.test.check.results-test
    clojure.test.check.rose-tree-test
    clojure.test.check.test])

(defn build-tc
  "Compiles the tc project to dlls.
  This function is used my `nostrand` and is called from terminal in the root folder as:
  nos dotnet-tasks/build-tc"
  []
  (binding [*compile-path* "build"
            *unchecked-math* *warn-on-reflection*]
    (println "Compile into DLL To : " *compile-path*)
    (doseq [ns tc-namespaces]
      (println (str "Compiling " ns))
      (compile ns))))

(defn test-tc
  "Run all the tc tests.
  This function is used my `nostrand` and is called from terminal in the root folder as:
  nos dotnet-tasks/test-tc"
  []
  (binding [ *unchecked-math* *warn-on-reflection*]
    (doseq [ns tc-namespaces]
      (require ns))
    (run-all-tests)))
