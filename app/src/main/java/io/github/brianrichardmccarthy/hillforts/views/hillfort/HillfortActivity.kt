package io.github.brianrichardmccarthy.hillforts.views.hillfort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.R.id.*
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageGalleryAdapter
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageListener
import io.github.brianrichardmccarthy.hillforts.helpers.readImageFromPath
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.models.Location
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import io.github.brianrichardmccarthy.hillforts.views.IMAGE_GALLERY_REQUEST
import io.github.brianrichardmccarthy.hillforts.views.IMAGE_REQUEST
import io.github.brianrichardmccarthy.hillforts.views.LOCATION_REQUEST
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger

class HillfortActivity : BaseView(), HillfortImageListener {

  lateinit var presenter: HillfortPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    init(toolbarAdd)
    presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      presenter.map = it
      presenter.doConfigureMap()
      it.setOnMapClickListener { presenter.doSetLocation() }
    }

    val layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
    hillfortImageGallery.layoutManager = layoutManager

    chooseImage.setOnClickListener {
      presenter.doChooseImage()
    }

  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
    presenter.doResartLocationUpdates()
  }

  override fun onImageClick(image: String) {
    presenter.doImageClick()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
      R.id.item_save -> presenter.doBtnAddClick()
      R.id.item_delete -> {
        presenter.doDelete()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun showHillfort(hillfort: HillfortModel) {
    hillfortTitle.setText(presenter.hillfort.title)
    description.setText(presenter.hillfort.description)
    lat.setText("%.6f".format(presenter.hillfort.location.lat))
    lng.setText("%.6f".format(presenter.hillfort.location.lng))
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode) {
      IMAGE_REQUEST -> {
        presenter.doImageRequest(data)
      }
      LOCATION_REQUEST -> {
        presenter.doLocationRequest(data)
      }
      IMAGE_GALLERY_REQUEST -> {
        presenter.doImageGalleryRequest(data)
      }
    }
  }
}
