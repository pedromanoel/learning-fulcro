(ns app.application
  (:require
   [app.ui.root :as root]
   [com.fulcrologic.fulcro.application :as app]))

(defonce app (app/fulcro-app))

(defn init []
  (app/mount! app root/Root "app")
  (js/console.log "Loaded"))

(defn hot-reload []
  (app/mount! app root/Root "app")
  (js/console.log "Hot reload"))
