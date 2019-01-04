package io.github.brianrichardmccarthy.hillforts.views.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import io.github.brianrichardmccarthy.hillforts.views.user.HillfortLoginActivity

class HillfortSplashActivity: BaseView() {


  lateinit var presenter: HillfortSplashPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    presenter = initPresenter(HillfortSplashPresenter(this)) as HillfortSplashPresenter

  }

  public override fun onDestroy() {
      presenter.doDestory()
      super.onDestroy()
  }

}
