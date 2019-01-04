package io.github.brianrichardmccarthy.hillforts.models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference

  override fun findAll(): MutableList<HillfortModel> {
    return hillforts
  }

  override fun findById(id: Long): HillfortModel? {
    return hillforts.find { p -> p.id == id }
  }

  override fun create(hillfort: HillfortModel) {
    val key = db.child("users").child(userId).child("hillforts").push().key
    hillfort.fbId = key!!
    hillforts.add(hillfort)
    db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
  }

  override fun update(hillfort: HillfortModel) {
    var foundPlacemark: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
    if (foundPlacemark != null) {
      foundPlacemark.title = hillfort.title
      foundPlacemark.description = hillfort.description
      foundPlacemark.images = hillfort.images
      foundPlacemark.location = hillfort.location
    }

    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
  }

  override fun delete(hillfort: HillfortModel) {
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
    hillforts.remove(hillfort)
  }

  override fun clear() {
    hillforts.clear()
  }

  fun fetchPlacemarks(hillfortReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
        hillfortReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    hillforts.clear()
    db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
  }
}