package io.github.brianrichardmccarthy.hillforts.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.helpers.sha256
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

class HillfortSignupActivity: AppCompatActivity() {

  private var user = UserModel()
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

    app = application as MainApp
    clearFields()
    username.requestFocus()

    btnCreateIn.setOnClickListener{
      signup()
    }
  }

  fun signup(){

    if (!valid()) return

    user.email = useremail.text.toString()
    user.name = username.text.toString()
    user.passwordHash = sha256(userpassword.text.toString())
    app.users.create(user.copy())

    toast(R.string.signup_success)
    clearFields()
    setResult(Activity.RESULT_OK, null)
    finish()
  }

  private fun clearFields(){
    username.text?.clear()
    userpassword.text?.clear()
    useremail.text?.clear()
  }

  private fun valid(): Boolean {
    return !useremail.text.toString().isNullOrEmpty() &&
        !username.text.toString().isNullOrEmpty() &&
        !userpassword.text.toString().isNullOrEmpty() &&
        userpassword.text.toString().length >= 5

  }



}
