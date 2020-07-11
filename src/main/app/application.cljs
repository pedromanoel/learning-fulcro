(ns app.application
  (:require [com.fulcrologic.fulcro.application :as app]
            [app.ui :as ui]))

(defonce app (app/fulcro-app))

(defn init []
  (app/mount! app ui/Root "app")
  (js/console.log "Loaded"))

(defn hot-reload []
  (app/mount! app ui/Root "app")
  (js/console.log "Hot reload"))