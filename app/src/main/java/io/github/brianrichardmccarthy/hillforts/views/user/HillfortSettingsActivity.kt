package io.github.brianrichardmccarthy.hillforts.views.user

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import kotlinx.android.synthetic.main.activity_hillfort_settings.*
import org.jetbrains.anko.AnkoLogger

class HillfortSettingsActivity: AppCompatActivity(), AnkoLogger {


  lateinit var presenter: HillfortSettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_settings)

    toolbarSettings.title = title
    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)

      presenter = HillfortSettingsPresenter(this)

    supportFragmentManager.beginTransaction()
        .add(R.id.settings_fragment_container, HillfortSettingsFragment())
        .commit()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId){
      android.R.id.home -> presenter.doBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

}
