(ns app.ui.person-list
  (:require [app.mutations :as api]
            [com.fulcrologic.fulcro.components :as comp :refer [defsc]]
            [com.fulcrologic.fulcro.dom :as dom]
            [app.ui.person :as person]))

(defsc PersonList [this {:list/keys [id label people]}]
  {:query [:list/id :list/label {:list/people (comp/get-query person/Person)}]
   :ident (fn [] [:list/id id])
   :initial-state
   (fn [{:keys [id label]}]
     #:list{:id     id
            :label  label
            :people (case label
                      "Friends"
                      [(comp/get-initial-state person/Person {:id 1 :name "Débora" :age 35})
                       (comp/get-initial-state person/Person {:id 2 :name "Cecília" :age 2})
                       (comp/get-initial-state person/Person {:id 3 :name "Fabrício" :age 0})]
                      "Enemies"
                      [(comp/get-initial-state person/Person {:id 4 :name "Bozo" :age 56})]
                      [])})}
  (let [delete-person
        (fn [person-id] (comp/transact! this [(api/delete-person {:list/id id :person/id person-id})]))]
    (dom/div
     (dom/h4 label)
     (dom/ul
      (map #(person/ui-person % {:onDelete delete-person}) people)))))

(def ui-person-list (comp/factory PersonList {:keyfn :list/id}))
