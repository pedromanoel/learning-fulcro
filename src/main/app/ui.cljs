(ns app.ui
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))

(defsc Person [this {:person/keys [name age]}]
  (dom/div
    (dom/p "Name: " name)
    (dom/p "Age: " age)))

(def ui-person (comp/factory Person))

(defsc Root [this props]
  (dom/div
    (dom/h1 "People")
    (ui-person #:person{:name "Pedro Manoel" :age 35})
    (ui-person #:person{:name "Débora Miglino Evangelista" :age 34})))