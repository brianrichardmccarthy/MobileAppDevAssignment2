package io.github.brianrichardmccarthy.hillforts.views.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.helpers.sha256
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import io.github.brianrichardmccarthy.hillforts.views.hillfortList.HillfortListActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class HillfortLoginActivity: BaseView() {

  lateinit var presenter: HillfortLoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    presenter = initPresenter(HillfortLoginPresenter(this)) as HillfortLoginPresenter

    btn_login.setOnClickListener{
      presenter.doLogin()
    }

    link_signup.setOnClickListener{
      presenter.doSignup()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    login_useremail.requestFocus()
    super.onActivityResult(requestCode, resultCode, data)
  }

}
