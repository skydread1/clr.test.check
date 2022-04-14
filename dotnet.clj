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

  (:require [clojure.test :refer [run-all-tests]]
            [magic.flags :as mflags]))

(def prod-namespaces
  '[clojure.test.check
    clojure.test.check.clojure-test])

(def test-namespaces
  '[clojure.test.check.clojure-test-test
    clojure.test.check.random-test
    clojure.test.check.results-test
    clojure.test.check.rose-tree-test
    clojure.test.check.test])

(defn build
  "Compiles the project to dlls.
  This function is used by `nostrand` and is called from the terminal in the root folder as:
  nos dotnet/build"
  []
  (binding [*compile-path* "build"
            *unchecked-math* *warn-on-reflection*
            mflags/*strongly-typed-invokes* true
            mflags/*direct-linking*         true
            mflags/*elide-meta*             false
            mflags/*emit-il2cpp-workaround* true]
    (println "Compile into DLL To : " *compile-path*)
    (doseq [ns prod-namespaces]
      (println (str "Compiling " ns))
      (compile ns))))

(defn run-tests
  "Run all the tests on the CLR.
  This function is used by `nostrand` and is called from the terminal in the root folder as:
  nos dotnet/run-tests"
  []
  (binding [*unchecked-math* *warn-on-reflection*
            mflags/*strongly-typed-invokes* true
            mflags/*direct-linking*         true
            mflags/*elide-meta*             false]
    (doseq [ns (concat prod-namespaces test-namespaces)]
      (require ns))
    (run-all-tests)))
