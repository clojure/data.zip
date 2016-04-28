(ns clojure.data.zip.xml-test
  (:require [clojure.zip :as zip]
            [clojure.data.zip.xml :refer [xml-> xml1-> attr attr= text text= tag=]]
            [cljs.test :refer-macros [deftest is testing run-tests]]))


(def atom1
  (zip/xml-zip {:tag :feed
                :attrs {:xmlns "http://www.w3.org/2005/Atom"}
                :content [{:tag :id
                           :attrs {}
                           :content ["tag:blogger.com1999:blog-28403206"]}
                          {:tag :updated
                           :attrs {}
                           :content ["2008-02-14T08:00:58.567-08:00"]}
                          {:tag :title
                           :attrs {:type "text"}
                           :content ["n01senet"]}
                          {:tag :link
                           :attrs {:rel "alternate"
                                   :type "text/html"
                                   :href "http://n01senet.blogspot.com/"}
                           :content []}
                          {:tag :entry
                           :attrs {}
                           :content [{:tag :id
                                      :attrs {}
                                      :content ["1"]}
                                     {:tag :published
                                      :attrs {}
                                      :content ["2008-02-13"]}
                                     {:tag :title
                                      :attrs {:type text}
                                      :content ["clojure is the best lisp yet"]}
                                     {:tag :author
                                      :attrs {}
                                      :content [{:tag :name
                                                 :attrs {}
                                                 :content ["Chouser"]}]}]}
                          {:tag :entry
                           :attrs {}
                           :content [{:tag :id
                                      :attrs {}
                                      :content ["2"]}
                                     {:tag :published
                                      :attrs {}
                                      :content ["2008-02-07"]}
                                     {:tag :title
                                      :attrs {:type "text"}
                                      :content ["experimenting with vnc"]}
                                     {:tag :author
                                      :attrs {}
                                      :content [{:tag :name
                                                 :attrs {}
                                                 :content ["agriffis"]}]}]}]}))

(deftest test-simple-single-function filter
  (is (= (xml-> atom1 #(:tag (zip/node %)))
         '(:feed))))

(deftest test-two-stage-filter
  (testing "using helpful query prediates"
    (is (= (xml-> atom1 (tag= :title) text)
           '("n01senet"))))
  (testing "keyword shortcut"
    (is (= (xml-> atom1 :title text)
           '("n01senet")))))

(deftest test-multi-stage-filter
  (testing "basic functionality"
    (is (= (xml-> atom1 :entry :author :name text)
           '("Chouser" "agriffis"))))
  (testing "subquery specified using a vector"
    (is (= (xml-> atom1 :entry [:author :name (text= "agriffis")]
                  :id text)
           '("2"))))
  (testing "with string shortcut"
    (is (= (xml-> atom1 :entry [:author :name "agriffis"] :id text)
           '("2")))))

(deftest test-xml1->
  (is (= (xml1-> atom1 :entry :author :name text)
         "Chouser")))

(deftest test-attribute-access
  (is (= (xml-> atom1 :title (attr :type))
         '("text"))))

(deftest test-attribute-filtering
  (is (= (xml-> atom1 :link [(attr= :rel "alternate")] (attr :type))
         '("text/html"))))

;; This was in the original code under a comment, but is fails
#_(deftest test-ancestors
  (testing "basic function"
    (is (= (xml-> atom1 descendants :id "2" ancestors zip/node #(:tag %))
           '(:id :entry :feed))))
  (testing "with non-auto tag= (:entry), followed by auto tag= (:id)"
    (is (= (xml-> atom1 descendants :name "Chouser" ancestors
                  :entry :id text)
           '("1")))))

(defrecord Node [tag attrs content])

(def record
  (zip/xml-zip
   (Node. :root {}
          [(Node. :entry {} [(Node. :id {} ["1"])
                             (Node. :author {:type "text"} ["John"])])
           (Node. :entry {} [(Node. :id {} ["2"])
                             (Node. :author {:type "text"} ["Jane"])])])))

(deftest test-records
  (is (= (xml-> record :entry :author text)
         '("John" "Jane"))))

(def atom2 
	(zip/xml-zip
		{:tag :feed
		 :content [{:tag :foo
			          :content "bar"}]}))

(deftest dzip-3-first-node
	(is (not= (xml-> atom2 :feed) '())))

(deftest dzip-3-second-node
	(is (= (xml-> atom2 :foo text) '("bar"))))

(defn js-print [& args]
  (if (exists? js/console)
    (.log js/console (apply str args))
    (js/print (apply str args))))

(set! *print-fn* js-print)

(run-tests)
