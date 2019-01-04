package io.github.brianrichardmccarthy.hillforts.views.user

import android.app.Activity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.R.id.*
import io.github.brianrichardmccarthy.hillforts.helpers.sha256
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

class HillfortSignupPresenter(val activity: HillfortSignupActivity) : BasePresenter(activity) {

  private var user = UserModel()
  init {
      clearFields()
      activity.username.requestFocus()
  }


  fun doSignup() {

    if (!valid()) return

    user.email = activity.useremail.text.toString()
    user.name = activity.username.text.toString()
    user.passwordHash = sha256(activity.userpassword.text.toString())
    app.users.create(user.copy())

    activity.toast(R.string.signup_success)
    clearFields()
    activity.setResult(Activity.RESULT_OK, null)
    activity.finish()

  }


  private fun clearFields(){
    activity.username.text?.clear()
    activity.userpassword.text?.clear()
    activity.useremail.text?.clear()
  }

  private fun valid(): Boolean {
    return !activity.useremail.text.toString().isNullOrEmpty() &&
        !activity.username.text.toString().isNullOrEmpty() &&
        !activity.userpassword.text.toString().isNullOrEmpty() &&
        activity.userpassword.text.toString().length >= 5

  }
}