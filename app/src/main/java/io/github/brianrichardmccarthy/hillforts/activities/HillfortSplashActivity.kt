package io.github.brianrichardmccarthy.hillforts.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import io.github.brianrichardmccarthy.hillforts.R
import org.jetbrains.anko.intentFor

class HillfortSplashActivity: AppCompatActivity() {

  private lateinit var delayHandler: Handler
  private val SPLASH_DELAY: Long = 5000

  internal val runnable: Runnable = Runnable {
    if (!isFinishing) {
      val intent = Intent(applicationContext, HillfortLoginActivity::class.java)
      startActivity(intent)
      finish()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    delayHandler = Handler()

    delayHandler!!.postDelayed(runnable, SPLASH_DELAY)

  }

  public override fun onDestroy() {

    if (delayHandler != null) {
      delayHandler.removeCallbacks(runnable)
    }

    super.onDestroy()
  }

}
