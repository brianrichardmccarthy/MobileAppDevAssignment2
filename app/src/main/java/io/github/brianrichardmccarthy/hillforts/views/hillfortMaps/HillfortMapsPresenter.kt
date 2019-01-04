package io.github.brianrichardmccarthy.hillforts.views.hillfortMaps

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.brianrichardmccarthy.hillforts.R.id.*
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.jetbrains.anko.info

class HillfortMapsPresenter(val activity: HillfortMapsActivity) : BasePresenter(activity) {

  lateinit var map: GoogleMap

  fun doMarkerSelected(marker: Marker) {
    val hillfort = marker.tag as HillfortModel
    if (hillfort != null) {
      view?.showHillfort(hillfort)
    }
    //async(UI) {
    //  val hillfort = marker.tag as HillfortModel
    //  if (hillfort != null) view?.showHillfort(hillfort)
    //}
  }

  fun loadPlacemarks() {
    view?.showHillforts(app.currentUser.hillforts)
    //async(UI) {
    //  view?.showHillforts(app.currentUser.hillforts)
    //}
  }

  fun doPopulateMap(placemarks: List<HillfortModel>) {
    map.uiSettings.setZoomControlsEnabled(true)
    placemarks.forEach {
      val loc = LatLng(it.location.lat, it.location.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      //map.addMarker(options).tag = it.id
      map.addMarker(options).tag = it
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
    }
    map.setOnMarkerClickListener(activity)
  }

}