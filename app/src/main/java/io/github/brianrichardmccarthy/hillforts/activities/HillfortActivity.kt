package io.github.brianrichardmccarthy.hillforts.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageGalleryAdapter
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageListener
import io.github.brianrichardmccarthy.hillforts.helpers.showImagePicker
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.models.Location
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger, HillfortImageListener {

  val IMAGE_REQUEST = 1
  val IMAGE_GALLERY_REQUEST = 2
  val LOCATION_REQUEST = 3

  // rating

  var hillfort = HillfortModel()
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    val layoutManager = GridLayoutManager(this,2)
    hillfortImageGallery.layoutManager = layoutManager

    var edit = false
    if (intent.hasExtra("hillfort_edit")){
      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      notes.setText(hillfort.notes)
      visit.isChecked = hillfort.visited
      rating.setText(hillfort.rating.toString())
      date_visited.updateDate(hillfort.yearVisited, hillfort.monthVisited, hillfort.dayVisited)
      btnAdd.setText(R.string.button_saveHillfort)
    }

    loadHillfortImages()

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    hillfortLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.location.zoom != 0f){
        location.lat = hillfort.location.lat
        location.lng = hillfort.location.lng
        location.zoom = hillfort.location.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    btnAdd.setOnClickListener {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      hillfort.visited = visit.isChecked
      hillfort.notes = notes.text.toString()
      hillfort.dayVisited = date_visited.dayOfMonth
      hillfort.monthVisited = date_visited.month
      hillfort.yearVisited = date_visited.year
      hillfort.rating = rating.text.toString().toInt()

      if (hillfort.title.isEmpty()) toast(R.string.enter_hillfort_title)
      else {
        if (edit){
          val original : HillfortModel? = app.currentUser.hillforts.find { h -> h.id == hillfort.id }
          val index = app.currentUser.hillforts.indexOf(original)
          app.currentUser.hillforts[index]= hillfort.copy()
          app.users.update(app.currentUser.copy())
        }
        else{
          app.currentUser.hillforts.add(hillfort.copy())
          app.users.update(app.currentUser.copy())
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
    }
  }

  override fun onImageClick(image: String) {
    startActivityForResult(intentFor<HillfortImageActivity>().putExtra("image", image), IMAGE_GALLERY_REQUEST)
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
        val original : HillfortModel? = app.currentUser.hillforts.find { h -> h.id == hillfort.id }
        val index = app.currentUser.hillforts.indexOf(original)
        app.currentUser.hillforts[index] = hillfort.copy()
        app.currentUser.hillforts.removeAt(index)
        app.users.update(app.currentUser.copy())
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode){
      IMAGE_REQUEST -> {
        if (data != null){
          hillfort.images.add(data.data!!.toString())
          loadHillfortImages()
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          hillfort.location.lat = location.lat
          hillfort.location.lng = location.lng
          hillfort.location.zoom = location.zoom
        }
      }
      IMAGE_GALLERY_REQUEST -> {
        if (data != null){
          val image = data.extras.getString("image")
          val original = data.extras.getString("original")
          if (image.isEmpty()) hillfort.images.remove(original)
          else hillfort.images.set(hillfort.images.indexOf(original), image)
          loadHillfortImages()
        }
      }
    }
  }

  private fun loadHillfortImages(){
    showHillfortImages(hillfort.images)
  }

  fun showHillfortImages(images: List<String>){
    hillfortImageGallery.adapter = HillfortImageGalleryAdapter(images, this)
    hillfortImageGallery.adapter?.notifyDataSetChanged()
  }
}
