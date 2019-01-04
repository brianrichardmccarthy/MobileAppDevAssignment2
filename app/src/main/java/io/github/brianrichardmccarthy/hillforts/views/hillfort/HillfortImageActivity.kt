package io.github.brianrichardmccarthy.hillforts.views.hillfort

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.R.id.image_delete
import io.github.brianrichardmccarthy.hillforts.helpers.showImagePicker
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import io.github.brianrichardmccarthy.hillforts.views.IMAGE_REQUEST
import kotlinx.android.synthetic.main.activity_hillfort_image.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class HillfortImageActivity: BaseView() {

  lateinit var presenter: HillfortImagePresenter

  lateinit var original: String
  lateinit var image: String
  lateinit var imageView: ImageView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_image)

    presenter = initPresenter(HillfortImagePresenter(this)) as HillfortImagePresenter

    displayImage()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort_image, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId){
      R.id.image_delete -> {
        image=""
        onBackPressed()
      }
      R.id.image_change -> {
        showImagePicker(this, IMAGE_REQUEST)
      }
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode){
      IMAGE_REQUEST -> {
          presenter.doActivityResult(data)
      }
    }
  }

  override fun onBackPressed() {
    presenter.doBackPressed()
    finish()
    super.onBackPressed()
  }

  fun displayImage(){
    Picasso.get()
        .load(image)
        .fit()
        .into(imageView)
  }
}
