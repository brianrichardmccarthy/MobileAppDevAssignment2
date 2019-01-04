package io.github.brianrichardmccarthy.hillforts.views.user

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.helpers.sha256
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast

class HillfortSignupActivity: BaseView() {

    lateinit var presenter: HillfortSignupPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

    presenter = initPresenter(HillfortSignupPresenter(this)) as HillfortSignupPresenter

    btnCreateIn.setOnClickListener{
        presenter.doSignup()
    }
  }

}
