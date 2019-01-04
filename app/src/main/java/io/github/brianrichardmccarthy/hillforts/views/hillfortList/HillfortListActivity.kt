package io.github.brianrichardmccarthy.hillforts.views.hillfortList

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.TextView
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortAdapter
import io.github.brianrichardmccarthy.hillforts.adapters.HillfortListener
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.views.hillfortMaps.HillfortMapsActivity
import io.github.brianrichardmccarthy.hillforts.views.user.HillfortSettingsActivity
import io.github.brianrichardmccarthy.hillforts.views.hillfort.HillfortActivity
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast


class HillfortListActivity: AppCompatActivity(), HillfortListener {


  private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
  private lateinit var navView: NavigationView
  private var showAllHillforts: Boolean = true

  lateinit var presenter: HillforListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    drawerLayout = findViewById(R.id.drawer_layout)

    presenter = HillforListPresenter(this)

    toolbarMain.title = title
    setSupportActionBar(toolbarMain)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    navView = findViewById<NavigationView>(R.id.hillfort_nav_view)

    navView.setNavigationItemSelectedListener { menuItem ->
      onOptionsItemSelected(menuItem)
      drawerLayout.closeDrawers()
      true
    }

    navView.getHeaderView(0).findViewById<TextView>(R.id.drawer_user_name).text = presenter.app.currentUser.name
    navView.getHeaderView(0).findViewById<TextView>(R.id.drawer_user_email).text = presenter.app.currentUser.email

    val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    loadHillforts()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<HillfortActivity>(0)
      R.id.item_logout -> {
        toast(R.string.logout_success)
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
      R.id.view_favs -> {
        showAllHillforts = !showAllHillforts
        loadHillforts(showAllHillforts)
      }
      R.id.item_view_all_hillforts -> {
        startActivityForResult<HillfortMapsActivity>(0)
      }
      R.id.item_settings -> {
        startActivityForResult<HillfortSettingsActivity>(0)
      }
      android.R.id.home -> {
        drawerLayout.openDrawer(GravityCompat.START)
        true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onHillfortClick(hillfort: HillfortModel) {
      presenter.doHillfortClick(hillfort)
  }

  override fun onHillfortMenuDeleteClick(hillfort: HillfortModel) {
      presenter.doHillfortMenuDeleteClick(hillfort)
  }

  override fun onHillfortMenuVisitClick(hillfort: HillfortModel) {
    presenter.onHillfortMenuVisitClick(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadHillforts()
    navView.getHeaderView(0).findViewById<TextView>(R.id.drawer_user_email).text = presenter.app.currentUser.email
    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onBackPressed() {
    super.onBackPressed()
    presenter.doBackPressed()
  }

  fun loadHillforts(showAll: Boolean = true) {
    showHillforts(if (showAll) presenter.app.currentUser.hillforts else presenter.app.currentUser.favourites)
  }

  fun showHillforts (hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }
}
