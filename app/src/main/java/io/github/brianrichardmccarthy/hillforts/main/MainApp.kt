package io.github.brianrichardmccarthy.hillforts.main

import android.app.Application
import io.github.brianrichardmccarthy.hillforts.models.*
import io.github.brianrichardmccarthy.hillforts.models.UserJSONStore
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.models.UserStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

  lateinit var users: UserStore
  lateinit var currentUser: UserModel

  override fun onCreate() {
    super.onCreate()

    var initialHillforts = ArrayList<HillfortModel>()

    initialHillforts.add(HillfortModel(title = "IR0905 Ballynamona Lower, Waterford",
        description = "A small sub-rectangular area barely projecting E from the mainland at an altitude of 28m OD.",
        location = Location(51.996723, -7.583626, 15f)))

    var initialUsers = ArrayList<UserModel>()
    initialUsers.add(UserModel(name="OK",
        email="ok@ok.com",
        passwordHash = "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3")) //password: 123

    users = UserJSONStore(applicationContext, initialUsers, initialHillforts)
    //hillforts = HillfortJSONStore(applicationContext, initialHillforts)
    info("Hillfort started")
  }
}
