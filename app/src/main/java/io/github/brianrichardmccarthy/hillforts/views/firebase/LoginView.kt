package io.github.brianrichardmccarthy.hillforts.views.firebase

import android.os.Bundle
import android.view.View
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.R.id.*
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginView : BaseView() {

  lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    init(toolbar, false)

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter
    progressBar.visibility = View.GONE
    signUp.setOnClickListener {
      val email = email.text.toString()
      val password = password.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doSignUp(email,password)
      }
    }

    logIn.setOnClickListener {
      val email = email.text.toString()
      val password = password.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doLogin(email,password)
      }
    }
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
  }
}