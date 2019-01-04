package io.github.brianrichardmccarthy.hillforts.views.splashscreen

import android.content.Intent
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import io.github.brianrichardmccarthy.hillforts.views.user.HillfortLoginActivity

class  HillfortSplashPresenter(val activity: HillfortSplashActivity) : BasePresenter(activity) {

  private var delayHandler: Handler
  private val SPLASH_DELAY: Long = 1000

  internal val runnable: Runnable = Runnable {
    if (!activity.isFinishing) {
      val intent = Intent(activity.applicationContext, HillfortLoginActivity::class.java)
      activity.startActivity(intent)
      activity.finish()
    }
  }

  init {
      delayHandler = Handler()
      delayHandler!!.postDelayed(runnable, SPLASH_DELAY)
  }

  fun doDestory() {
      if (delayHandler != null) {
          delayHandler.removeCallbacks(runnable)
      }
  }

}