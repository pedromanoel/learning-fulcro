(ns app.ui
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))

(defsc Person [this {:person/keys [name age]}]
  (dom/li
    (dom/h5 (str name " (age: " age ")"))))

;; The keyfn generates a react key for each element based on props. See React documentation on keys.
(def ui-person (comp/factory Person {:keyfn :person/name}))

(defsc PersonList [this {:list/keys [label people]}]
  (dom/div
    (dom/h4 label)
    (dom/ul
      (map ui-person people))))

(def ui-person-list (comp/factory PersonList))

(defsc Root [this props]
  (let [ui-data {:friends #:list{:label "Friends"
                                 :people [#:person{:name "Débora" :age 35}
                                          #:person{:name "Cecília" :age 2}
                                          #:person{:name "Fabrício" :age 0}]}
                 :enemies #:list{:label "Enemies"
                                 :people [#:person{:name "Bozo" :age 56}]}}]
    (dom/div
      (ui-person-list (:friends ui-data))
      (ui-person-list (:enemies ui-data)))))