(ns app.ui
  (:require
    [app.mutations :as api]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
    [com.fulcrologic.fulcro.dom :as dom]))

(defsc Person [this {:person/keys [id name age]} {:keys [onDelete]}]
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

(defsc PersonList [this {:list/keys [id label people]}]
  {:query [:list/id :list/label {:list/people (comp/get-query Person)}]
   :ident (fn [] [:list/id id])
   :initial-state
          (fn [{:keys [id label]}]
            #:list{:id     id
                   :label  label
                   :people (case label
                             "Friends"
                             [(comp/get-initial-state Person {:id 1 :name "Débora" :age 35})
                              (comp/get-initial-state Person {:id 2 :name "Cecília" :age 2})
                              (comp/get-initial-state Person {:id 3 :name "Fabrício" :age 0})]
                             "Enemies"
                             [(comp/get-initial-state Person {:id 4 :name "Bozo" :age 56})]
                             [])})}
  (let [delete-person
        (fn [person-id] (comp/transact! this [(api/delete-person {:list/id id :person/id person-id})]))]
    (dom/div
      (dom/h4 label)
      (dom/ul
        (map #(ui-person % {:onDelete delete-person}) people)))))

(def ui-person-list (comp/factory PersonList))

(defsc Root [this {:keys [friends enemies]}]
  {:query [{:friends (comp/get-query PersonList)}
           {:enemies (comp/get-query PersonList)}]
   :initial-state
          (fn [_] {:friends (comp/get-initial-state PersonList {:id :friends :label "Friends"})
                   :enemies (comp/get-initial-state PersonList {:id :enemies :label "Enemies"})})}
  (dom/div
    (ui-person-list friends)
    (ui-person-list enemies)))