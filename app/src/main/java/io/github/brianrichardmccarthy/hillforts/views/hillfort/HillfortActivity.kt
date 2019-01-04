package io.github.brianrichardmccarthy.hillforts.views.hillfort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageGalleryAdapter
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageListener
import io.github.brianrichardmccarthy.hillforts.models.Location
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger, HillfortImageListener {

  lateinit var presenter: HillfortPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)

    presenter = HillfortPresenter(this)

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
    hillfortImageGallery.layoutManager = layoutManager

    loadHillfortImages()

    chooseImage.setOnClickListener {
      presenter.doChooseImage()
    }

    hillfortLocation.setOnClickListener {
      presenter.doHillfortLocation()
    }

    // btnAdd.setOnClickListener { presenter.doBtnAddClick() }
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
      R.id.item_delete -> {
          presenter.doDelete()
      }
      R.id.item_save -> {
          presenter.doBtnAddClick()
      }

    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode) {
      presenter.IMAGE_REQUEST -> {
          presenter.doImageRequest(data)
      }
      presenter.LOCATION_REQUEST -> {
          presenter.doLocationRequest(data)
      }
      presenter.IMAGE_GALLERY_REQUEST -> {
          presenter.doImageGalleryRequest(data)
      }
    }
  }

  fun loadHillfortImages(){
    showHillfortImages(presenter.hillfort.images)
  }

  fun showHillfortImages(images: List<String>){
    hillfortImageGallery.adapter = HillfortImageGalleryAdapter(images, this)
    hillfortImageGallery.adapter?.notifyDataSetChanged()
  }
}
