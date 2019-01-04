package io.github.brianrichardmccarthy.hillforts.views.hillfort

import android.app.Activity
import android.content.Intent
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import io.github.brianrichardmccarthy.hillforts.views.BaseView

class HillfortImagePresenter(val hillfortImageActivity: BaseView) : BasePresenter(hillfortImageActivity) {

  fun doBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("image", (hillfortImageActivity as HillfortImageActivity).image)
    resultIntent.putExtra("original", hillfortImageActivity.original)
    hillfortImageActivity.setResult(Activity.RESULT_OK, resultIntent)
  }

  fun doActivityResult(data: Intent?) {
    if (data != null){
      (hillfortImageActivity as HillfortImageActivity).image = data.data!!.toString()
      hillfortImageActivity.displayImage()
    }
  }

}