package io.github.brianrichardmccarthy.hillforts.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.github.brianrichardmccarthy.hillforts.helpers.showImagePicker
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.models.Location
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.intentFor
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.R.id.*
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortImageGalleryAdapter
import io.github.brianrichardmccarthy.hillforts.helpers.checkLocationPermissions
import io.github.brianrichardmccarthy.hillforts.views.*
import io.github.brianrichardmccarthy.hillforts.views.hillfortMaps.MapsActivity
import io.github.brianrichardmccarthy.hillforts.helpers.createDefaultLocationRequest
import org.jetbrains.anko.toast

class HillfortPresenter(val activity: BaseView) : BasePresenter(activity) {
  var hillfort = HillfortModel()
  var edit = false
  lateinit var map: GoogleMap
  var defaultLocation = Location(52.245696, -7.139102, 15f)
  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
  val locationRequest = createDefaultLocationRequest()
  init {

    if (activity.intent.hasExtra("hillfort_edit")){
      edit = true
      hillfort = activity.intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      activity.hillfortTitle.setText(hillfort.title)
      activity.description.setText(hillfort.description)
      activity.notes.setText(hillfort.notes)
      activity.visit.isChecked = hillfort.visited
      activity.rating.text = hillfort.rating.toString()
      activity.date_visited.updateDate(hillfort.yearVisited, hillfort.monthVisited, hillfort.dayVisited)
      view!!.showHillfort(hillfort)
      for ( h: HillfortModel in app.currentUser.favourites) {
        if (h.title == hillfort.title) {
          activity.fav.isChecked = true
          break
        }
      }

    } else {
      if (checkLocationPermissions(activity)) {
        doSetLocation()
      } else {
        locationUpdate(defaultLocation.lat, defaultLocation.lng)
      }
    }

    loadHillfortImages()

  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    locationService.lastLocation.addOnSuccessListener {
      locationUpdate(it.latitude, it.longitude)
    }
  }

  @SuppressLint("MissingPermission")
  fun doResartLocationUpdates() {

    var locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null && locationResult.locations != null) {
          val l = locationResult.locations.last()
          locationUpdate(l.latitude, l.longitude)
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }

  fun doSetLocation() {
    view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
  }

  fun doHillfortLocation() {
    val location = Location(52.245696, -7.139102, 15f)
    if (hillfort.location.zoom != 0f){
      location.lat = hillfort.location.lat
      location.lng = hillfort.location.lng
      location.zoom = hillfort.location.zoom
    }
    activity.startActivityForResult(activity.intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
  }

  fun doChooseImage() {
    showImagePicker(activity, IMAGE_REQUEST)
  }

  fun doactivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
  }

  fun doImageClick() {
    activity.startActivityForResult(activity.intentFor<HillfortImageActivity>().putExtra("image", image), IMAGE_GALLERY_REQUEST)
  }

  fun doDelete() {
    val original : HillfortModel? = app.currentUser.hillforts.find { h -> h.id == hillfort.id }
    val index = app.currentUser.hillforts.indexOf(original)
    app.currentUser.hillforts[index] = hillfort.copy()
    app.currentUser.hillforts.removeAt(index)
    app.users.update(app.currentUser.copy())
    activity.finish()
  }

  fun doBtnAddClick() {
    hillfort.title = activity.hillfortTitle.text.toString()
    hillfort.description = activity.description.text.toString()
    hillfort.visited = activity.visit.isChecked
    hillfort.notes = activity.notes.text.toString()
    hillfort.dayVisited = activity.date_visited.dayOfMonth
    hillfort.monthVisited = activity.date_visited.month
    hillfort.yearVisited = activity.date_visited.year
    hillfort.rating = activity.rating.text.toString().toInt()
    if (activity.fav.isChecked) {
      app.currentUser.favourites.add(hillfort.copy())
    } else {
      app.currentUser.favourites.removeAll { h -> h.title.equals(hillfort.title) }
    }

    if (hillfort.title.isEmpty()) activity.toast(R.string.enter_hillfort_title)
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
      activity.setResult(AppCompatActivity.RESULT_OK)
      activity.finish()
    }
  }

  fun doImageRequest(data: Intent?) {
    if (data != null){
      hillfort.images.add(data.data!!.toString())
      loadHillfortImages()
    }
  }

  fun doLocationRequest(data: Intent?) {
    if (data != null) {
      val location = data.extras.getParcelable<Location>("location")
      hillfort.location.lat = location.lat
      hillfort.location.lng = location.lng
      hillfort.location.zoom = location.zoom
      locationUpdate(hillfort.location.lat, hillfort.location.lng)
    }
  }

  fun doImageGalleryRequest(data: Intent?) {
    if (data != null){
      val image = data.extras.getString("image")
      val original = data.extras.getString("original")
      if (image.isEmpty()) hillfort.images.remove(original)
      else hillfort.images.set(hillfort.images.indexOf(original), image)
      loadHillfortImages()
    }
  }

  fun loadHillfortImages(){
    showHillfortImages(hillfort.images)
  }

  fun showHillfortImages(images: List<String>){
    activity.hillfortImageGallery.adapter = HillfortImageGalleryAdapter(images, activity as HillfortActivity)
    activity.hillfortImageGallery.adapter?.notifyDataSetChanged()
  }

  fun locationUpdate(lat: Double, lng: Double) {
    hillfort.location.lat = lat
    hillfort.location.lng = lng
    hillfort.location.zoom = 15f
    map?.clear()
    map?.uiSettings?.isZoomControlsEnabled = true
    val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
    map?.addMarker(options)
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
    view?.showHillfort(hillfort)
  }

  fun doConfigureMap() {
    locationUpdate(hillfort.location.lat, hillfort.location.lng)
  }

}