package io.github.brianrichardmccarthy.hillforts.views.hillfortList

import android.view.MenuItem
import androidx.core.app.ActivityCompat.startActivityForResult
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.views.hillfort.HillfortActivity
import org.jetbrains.anko.intentFor
import kotlin.system.exitProcess

class HillforListPresenter(val activity: HillfortListActivity) {

    var app: MainApp

    init {
      app = activity.application as MainApp
    }

  fun doHillfortClick(hillfort: HillfortModel) {
    activity.startActivityForResult(activity.intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
  }

  fun doHillfortMenuDeleteClick(hillfort: HillfortModel) {

    val original : HillfortModel? = app.currentUser.hillforts.find { h -> h.id == hillfort.id }
    val index = app.currentUser.hillforts.indexOf(original)
    app.currentUser.hillforts.removeAt(index)
    app.users.update(app.currentUser.copy())
    activity.loadHillforts()
  }

  fun doBackPressed(){
    activity.moveTaskToBack(true)
    exitProcess(0)
  }

  fun onHillfortMenuVisitClick(hillfort: HillfortModel) {

    hillfort.visited = !hillfort.visited
    val original : HillfortModel? = app.currentUser.hillforts.find { h -> h.id == hillfort.id }
    val index = app.currentUser.hillforts.indexOf(original)
    app.currentUser.hillforts[index] = hillfort.copy()
    app.users.update(app.currentUser.copy())
    activity.loadHillforts()

  }

}