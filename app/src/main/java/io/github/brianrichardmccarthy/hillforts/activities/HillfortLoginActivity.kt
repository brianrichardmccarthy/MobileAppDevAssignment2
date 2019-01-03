package io.github.brianrichardmccarthy.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.helpers.sha256
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class HillfortLoginActivity: AppCompatActivity(), AnkoLogger {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    app = application as MainApp

    btn_login.setOnClickListener{
      login()
    }

    link_signup.setOnClickListener{
      startActivityForResult(intentFor<HillfortSignupActivity>(),0)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    login_useremail.requestFocus()
    super.onActivityResult(requestCode, resultCode, data)
  }

  fun login(){

    val email = login_useremail.text.toString()
    val passwordHash = sha256(login_userpassword.text.toString())
    var userFound: UserModel? = app.users.findAll().find{ u ->
      u.email == email && u.passwordHash == passwordHash
    }

    if (userFound == null) {
      toast(R.string.login_error)
      return
    }

    toast(R.string.login_success)
    clearFields()
    app.currentUser = userFound
    startActivityForResult(intentFor<HillfortListActivity>(), 0)
  }

  private fun clearFields(){
    login_useremail.text.clear()
    login_userpassword.text.clear()
  }
}
