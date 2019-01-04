package io.github.brianrichardmccarthy.hillforts.views.hillfort

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.helpers.showImagePicker

class HillfortImagePresenter(val hillfortImageActivity: HillfortImageActivity) {

  val IMAGE_REQUEST = 1

  fun doBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("image", hillfortImageActivity.image)
    resultIntent.putExtra("original", hillfortImageActivity.original)
    hillfortImageActivity.setResult(Activity.RESULT_OK, resultIntent)
  }

  fun doActivityResult(data: Intent?) {
    if (data != null){
      hillfortImageActivity.image = data.data!!.toString()
      hillfortImageActivity.displayImage()
    }
  }

}