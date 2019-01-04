package io.github.brianrichardmccarthy.hillforts.views.user

import androidx.core.app.ActivityCompat.startActivityForResult
import io.github.brianrichardmccarthy.hillforts.R
//import io.github.brianrichardmccarthy.hillforts.R.id.login_useremail
//import io.github.brianrichardmccarthy.hillforts.R.id.login_userpassword
import io.github.brianrichardmccarthy.hillforts.helpers.sha256
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import io.github.brianrichardmccarthy.hillforts.views.hillfortList.HillfortListActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class HillfortLoginPresenter {}

/*
class HillfortLoginPresenter(val activity: HillfortLoginActivity) : BasePresenter(activity) {

  fun doLogin() {
      val email = activity.login_useremail.text.toString()
      val passwordHash = sha256(activity.login_userpassword.text.toString())
      var userFound: UserModel? = app.users.findAll().find{ u ->
        u.email == email && u.passwordHash == passwordHash
      }

      if (userFound == null) {
        activity.toast(R.string.login_error)
        return
      }

      activity.toast(R.string.login_success)
      clearFields()
      app.currentUser = userFound
      activity.startActivityForResult(activity.intentFor<HillfortListActivity>(), 0)
  }

  fun doSignup() {

      activity.startActivityForResult(activity.intentFor<HillfortSignupActivity>(),0)

  }

  fun clearFields() {
    activity.login_useremail.text.clear()
    activity.login_userpassword.text.clear()
  }

}

    */