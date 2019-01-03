package io.github.brianrichardmccarthy.hillforts.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.main.MainApp

import kotlinx.android.synthetic.main.activity_hillfort_maps.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class HillfortMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener, AnkoLogger {

  lateinit var map: GoogleMap
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_maps)
    setSupportActionBar(toolbarMaps)
    app = application as MainApp
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      map = it
      configureMap()
    }
  }

  fun configureMap() {
    map.uiSettings.setZoomControlsEnabled(true)
    app.currentUser.hillforts.forEach {
      val loc = LatLng(it.location.lat, it.location.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it.id
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
      info("\n\tDebug Maps\nLatitude=${loc.latitude}, Longitude=${loc.longitude}\n\n")
    }

    //info("Number of shitforts = ${app.currentUser.hillforts}")

    map.setOnMarkerClickListener(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    currentTitle.text = marker.title
    return false
  }

}
