package io.github.brianrichardmccarthy.hillforts.views.hillfortMaps

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.github.brianrichardmccarthy.hillforts.R.id.mapView
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.jetbrains.anko.info

class HillfortMapsPresenter(val activity: HillfortMapsActivity) : BasePresenter(activity) {

  lateinit var map: GoogleMap

    fun initMap() {
      map.uiSettings.isZoomControlsEnabled = true
      app.currentUser.hillforts.forEach {
        val loc = LatLng(it.location.lat, it.location.lng)
        val options = MarkerOptions().title(it.title).position(loc)
        map.addMarker(options).tag = it.id
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
      }

      map.setOnMarkerClickListener(activity)
    }

}