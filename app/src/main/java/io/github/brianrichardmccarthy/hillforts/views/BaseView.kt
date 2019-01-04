package io.github.brianrichardmccarthy.hillforts.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.views.hillfort.HillfortActivity
import io.github.brianrichardmccarthy.hillforts.views.hillfortList.HillfortListActivity
import io.github.brianrichardmccarthy.hillforts.views.hillfortMaps.HillfortMapsActivity
import io.github.brianrichardmccarthy.hillforts.views.hillfortMaps.MapsActivity
import org.jetbrains.anko.AnkoLogger

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2
val IMAGE_GALLERY_REQUEST = 3


enum class VIEW {
  LOCATION, PLACEMARK, MAPS, LIST
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, HillfortListActivity::class.java)
    when (view) {
      VIEW.LOCATION -> intent = Intent(this, MapsActivity::class.java)
      VIEW.PLACEMARK -> intent = Intent(this, HillfortActivity::class.java)
      VIEW.MAPS -> intent = Intent(this, HillfortMapsActivity ::class.java)
      VIEW.LIST -> intent = Intent(this, HillfortListActivity::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar) {
    toolbar.title = title
    setSupportActionBar(toolbar)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showHillfort(hillfort: HillfortModel) {}
  open fun showHillforts(hillforts: List<HillfortModel>) {}
  open fun showProgress() {}
  open fun hideProgress() {}
}