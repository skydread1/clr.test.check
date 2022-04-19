(ns dotnet

  "Dotnet related tasks to be called by `nostrand`.
  Nostrand uses the `magic` compiler.

  ## Motivation

  This namespace provides convenient functions to:
  - compile the prod namespaces to .net assemblies
  - run the tests in the CLR
  - pack and push NuGet Packages to a host repo"

  (:require [clojure.test :refer [run-all-tests]]
            [magic.flags :as mflags]
            [nostrand.deps.nuget :as nuget]))

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
  nos dotnet/build"
  []
  (binding [*compile-path*                  "build"
            *unchecked-math*                *warn-on-reflection*
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
  nos dotnet/run-tests"
  []
  (binding [*unchecked-math*                *warn-on-reflection*
            mflags/*strongly-typed-invokes* true
            mflags/*direct-linking*         true
            mflags/*elide-meta*             false]
    (doseq [ns (concat prod-namespaces test-namespaces)]
      (require ns))
    (run-all-tests)))

(defn nuget-push
  "Pack and Push NuGet Package to git host repo.
  nos dotnet/nuget-push"
  []
  (binding [*compile-path* "build"]
    (nuget/pack-and-push-nuget "github")))
