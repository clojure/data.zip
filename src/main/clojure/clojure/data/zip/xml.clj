; Copyright (c) Chris Houser, April 2008. All rights reserved.
; The use and distribution terms for this software are covered by the
; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
; which can be found in the file epl-v10.html at the root of this distribution.
; By using this software in any fashion, you are agreeing to be bound by
; the terms of this license.
; You must not remove this notice, or any other, from this software.
; Specialization of zip-filter for xml trees.

(ns clojure.data.zip.xml
  (:require [clojure.data.zip :as zf]
            [clojure.zip :as zip]
            [clojure.xml :as xml]))

(declare xml->)

(defn attr
  "Returns the xml attribute named attrname, of the xml node at location loc."
  ([attrname]     (fn [loc] (attr loc attrname)))
  ([loc attrname] (when (zip/branch? loc) (-> loc zip/node :attrs attrname))))

(defn attr=
  "Returns a query predicate that matches a node when it has an
  attribute named attrname whose value is attrval."
  [attrname attrval] (fn [loc] (= attrval (attr loc attrname))))

(defn tag=
  "Returns a query predicate that matches a node when its is a tag
  named tagname."
  [tagname]
  (fn [loc]
    (filter #(and (zip/branch? %) (= tagname (:tag (zip/node %))))
            (if (zf/auto? loc)
              (zf/children-auto loc)
              (list (zf/auto true loc))))))

(defn text
  "Returns the textual contents of the given location, similar to
  xpaths's value-of"
  [loc]
  (.replaceAll
   ^String (apply str (xml-> loc zf/descendants zip/node string?))
   (str "[\\s" (char 160) "]+") " "))

(defn text=
  "Returns a query predicate that matches a node when its textual
  content equals s."
  [s] (fn [loc] (= (text loc) s)))

(defn seq-test
  "Returns a query predicate that matches a node when its xml content
  matches the query expresions given."
  ^{:private true}
  [preds] (fn [loc] (and (seq (apply xml-> loc preds)) (list loc))))

(defn xml->
  "The loc is passed to the first predicate.  If the predicate returns
  a collection, each value of the collection is passed to the next
  predicate.  If it returns a location, the location is passed to the
  next predicate.  If it returns true, the input location is passed to
  the next predicate.  If it returns false or nil, the next predicate
  is not called.

  This process is repeated, passing the processed results of each
  predicate to the next predicate.  xml-> returns the final sequence.
  The entire chain is evaluated lazily.

  There are also special predicates: keywords are converted to tag=,
  strings to text=, and vectors to sub-queries that return true if
  they match.

  See the footer of zip-query.clj for examples."
  [loc & preds]
  (zf/mapcat-chain loc preds
                   #(cond (keyword? %) (tag= %)
                          (string?  %) (text= %)
                          (vector?  %) (seq-test %))))

(defn xml1->
  "Returns the first item from loc based on the query predicates
  given.  See xml->"
  [loc & preds] (first (apply xml-> loc preds)))
