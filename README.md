clojure.data.zip
========================================

System for filtering trees, and XML trees in particular.

Formerly known as clojure.contrib.zip-filter.

Releases and Dependency Information
========================================

This project follows the version scheme MAJOR.MINOR.PATCH where each component provides some relative indication of the size of the change, but does not follow semantic versioning. In general, all changes endeavor to be non-breaking (by moving to new names rather than by breaking existing names).

Latest stable release: 1.0.0

* [All Released Versions](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.clojure%22%20AND%20a%3A%22data.zip%22)
* [Development Snapshot Versions](https://oss.sonatype.org/index.html#nexus-search;gav~org.clojure~data.zip~~~)

deps.edn dependency information:
```clojure
org.clojure/data.zip {:mvn/version "1.0.0"}
```

[Leiningen](https://github.com/technomancy/leiningen) dependency information:
```clojure
[org.clojure/data.zip "1.0.0"]
```
[Maven](https://maven.apache.org/) dependency information:
```xml
<dependency>
  <groupId>org.clojure</groupId>
  <artifactId>data.zip</artifactId>
  <version>1.0.0</version>
</dependency>
```

Example Usage
========================================

For more detail see the [generated documentation on github](https://clojure.github.com/data.zip/).

Developer Information
========================================

* [GitHub project](https://github.com/clojure/data.zip)
* [Bug Tracker](https://clojure.atlassian.net/browse/DZIP)
* [Continuous Integration](https://build.clojure.org/job/data.zip/)
* [Compatibility Test Matrix](https://build.clojure.org/job/data.zip-test-matrix/)

Change Log
====================

* Release 1.0.0 on 2020-02-18
  * [DZIP-9](https://clojure.atlassian.net/browse/DZIP-9) - Fix invalid and inappropriate private metadata
* Release 0.1.3 on 2019-03-07
  * [DZIP-8](https://clojure.atlassian.net/browse/DZIP-8) - Use unicode char for non-breaking space in regex pattern
* Release 0.1.2 on 2016-04-28
  * [DZIP-3](https://clojure.atlassian.net/browse/DZIP-3) - Not returning expected result for tag= 
  * [DZIP-5](https://clojure.atlassian.net/browse/DZIP-5) - Add ClojureScript support
* Release 0.1.1 on 2012-03-30
* Release 0.1.0 on 2011-08-24

Copyright and License
========================================

Copyright (c) Aaron Bedra and Rich Hickey, 2011-2020. All rights reserved.  The use and
distribution terms for this software are covered by the Eclipse Public
License 1.0 (https://opensource.org/licenses/eclipse-1.0.php) which can
be found in the file epl-v10.html at the root of this distribution.
By using this software in any fashion, you are agreeing to be bound by
the terms of this license.  You must not remove this notice, or any
other, from this software.
