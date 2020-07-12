(ns app.ui
  (:require
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))

(defsc Person [this {:person/keys [name age]} {:keys [onDelete]}]
  {:query [:person/name :person/age]
   :initial-state
   (fn [{:keys [name age]}]
     #:person{:name name :age age})}
  (dom/li
    (dom/h5
      (str name " (age: " age ")")
      (dom/button {:onClick #(onDelete name)} "X"))))

;; The keyfn generates a react key for each element based on props. See React documentation on keys.
(def ui-person (comp/factory Person {:keyfn :person/name}))

(defn ui-person-with-on-delete [onDelete]
  (fn [props] (ui-person (comp/computed props {:onDelete onDelete}))))

(defsc PersonList [this {:list/keys [label people]}]
  {:query [:list/label {:list/people (comp/get-query Person)}]
   :initial-state
   (fn [{:keys [label]}]
     #:list{:label  label
            :people (case label
                      "Friends"
                      [(comp/get-initial-state Person {:name "Débora" :age 35})
                       (comp/get-initial-state Person {:name "Cecília" :age 2})
                       (comp/get-initial-state Person {:name "Fabrício" :age 0})]
                      "Enemies"
                      [(comp/get-initial-state Person {:name "Bozo" :age 56})]
                      [])})}
  (let [delete-person (fn [name] (println label "asked to delete" name))]
    (dom/div
      (dom/h4 label)
      (dom/ul
        (map (ui-person-with-on-delete delete-person) people)))))

(def ui-person-list (comp/factory PersonList))

(defsc Root [this {:keys [friends enemies]}]
  {:query [{:friends (comp/get-query PersonList)}
           {:enemies (comp/get-query PersonList)}]
   :initial-state
   (fn [_] {:friends (comp/get-initial-state PersonList {:label "Friends"})
            :enemies (comp/get-initial-state PersonList {:label "Enemies"})})}
  (dom/div
    (ui-person-list friends)
    (ui-person-list enemies)))