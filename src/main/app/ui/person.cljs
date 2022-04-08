(ns app.ui.person
  (:require [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom]))

(defsc Person [_this {:person/keys [id name age]} {:keys [onDelete]}]
  {:query [:person/id :person/name :person/age]
   :ident (fn [] [:person/id id])
   :initial-state
   (fn [{:keys [id name age]}]
     #:person{:id id :name name :age age})}
  (dom/li
   (dom/h5
    (str name " (age: " age ")")
    (dom/button {:onClick #(onDelete id)} "X"))))

;; The keyfn generates a react key for each element based on props. See React documentation on keys.
(def ui-person (comp/computed-factory Person {:keyfn :person/name}))
