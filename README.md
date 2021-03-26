# clr.test.check with MAGIC

A port of [clojure/test/check](https://github.com/clojure/test.check) library to `magic`.

## What is Magic

From the [README](https://github.com/nasser/magic) :
```
MAGIC is a compiler for Clojure written in Clojure targeting the Common Language Runtime. Its goals are to:

Take full advantage of the CLR's features to produce better byte code
Leverage Clojure's functional programming and data structures to be more tunable, composeable, and maintainable
```

## Why do we need a specific port

We could think that the [clojure/clr.test.check](https://github.com/clojure/clr.tools.nrepl) is enough as it replaces the Java native function with the C# equivalents. In theory yes, the `magic` compiler should be able to compile what `clojureCLR` compile.

However, `magic` is not fully stable and some minors changes are necessary to make it work, hence this fork.

## Platform independent

This fork allows you to compile on both `JVM` and `CLR`. The reader conditionals were added to:
- be able to run with either compiler
- ease the debug and tests comparisons between environment
- highlight better the native code port (instead of just putting the equivalent in comments)

## Use the library

### With the CLR (using Magic)

To compile, run or test a Clojure project in CLR with `magic`, you need to use [nostrand](https://github.com/nasser/nostrand).

`nostrand` is just a tool that let you use `magic` without having to take care of the bootrapping and configuration.

`nostrand` has its own project manager in a file name `project.edn` that has a syntax similar to the lein project manager `project.clj`.

To add the library to your project, just add the dependency to your `project.edn` :

```clojure
{:name "My Clojure Project"
 :dependencies [[:github skydread1/clr.test.check "magic"
                 :paths ["src"]]}
```
### With the JVM

Same as for the regular Clojure library. Add it to your preferred project manager such as tool `deps.end` or lein `project.clj`.

## License

Original:

> Copyright © 2014 Rich Hickey, Reid Draper and contributors

Distributed under the Eclipse Public License, the same as Clojure.
