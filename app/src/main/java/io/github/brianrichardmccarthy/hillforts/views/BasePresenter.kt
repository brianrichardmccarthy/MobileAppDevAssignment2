package io.github.brianrichardmccarthy.hillforts.views

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.github.brianrichardmccarthy.hillforts.main.MainApp

open class BasePresenter(var view: BaseView?) {

  var app: MainApp =  view?.application as MainApp
  var auth: FirebaseAuth = FirebaseAuth.getInstance()

  open fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
  }

  open fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
  }

  open fun onDestroy() {
    view = null
  }
}