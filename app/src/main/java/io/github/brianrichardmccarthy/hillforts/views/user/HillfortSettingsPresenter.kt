package io.github.brianrichardmccarthy.hillforts.views.user

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter

class HillfortSettingsPresenter(val activity: HillfortSettingsActivity) : BasePresenter(activity) {

  private var sharedPrefs: SharedPreferences

  init {
    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity)
    sharedPrefs.edit().clear().apply()

    val visited = app.currentUser.hillforts.filter { it.visited }.size
    val total = app.currentUser.hillforts.size

    sharedPrefs.edit().putInt("pref_stats_visited", visited).putInt("pref_stats_total", total).apply()
  }

  fun doBackPressed() {
    val newEmail = sharedPrefs.getString("pref_user_email", "")
    val newPasswordHash = sharedPrefs.getString("pref_user_password", "")

    if (newEmail.isNotEmpty()) app.currentUser.email = newEmail
    if (newPasswordHash.isNotEmpty()) app.currentUser.passwordHash = newPasswordHash
    app.users.update(app.currentUser.copy())

    activity.onBackPressed()
  }

}