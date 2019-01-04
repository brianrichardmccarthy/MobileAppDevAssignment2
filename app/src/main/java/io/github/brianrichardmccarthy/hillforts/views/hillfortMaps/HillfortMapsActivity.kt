package io.github.brianrichardmccarthy.hillforts.views.hillfortMaps

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity;
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

  lateinit var presenter: HillfortMapsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_maps)
    setSupportActionBar(toolbarMaps)

    presenter = HillfortMapsPresenter(this)

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      presenter.initMap()
      presenter.map = it
    }
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

  override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
    super.onSaveInstanceState(outState!!)
    mapView.onSaveInstanceState(outState)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    currentTitle.text = marker.title
    return false
  }

}
