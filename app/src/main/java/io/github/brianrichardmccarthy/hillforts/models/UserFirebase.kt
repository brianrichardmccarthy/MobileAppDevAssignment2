package io.github.brianrichardmccarthy.hillforts.models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.brianrichardmccarthy.hillforts.helpers.exists
import org.jetbrains.anko.AnkoLogger
import java.util.ArrayList

class UserFirebase(val context: Context) : UserStore, AnkoLogger {
  override fun findById(id: Long): UserModel? {
    return users.find { it.id == id }
  }


  lateinit var userId: String
  lateinit var db: DatabaseReference

  var users = mutableListOf<UserModel>()

  override fun findAll(): MutableList<UserModel> {
    return users
  }

  override fun create(user: UserModel) {
    val key = db.child("users").child(userId).child("hillforts").push().key
    user.fbId = key!!
    users.add(user)
    db.child("users").child(userId).child("hillforts").child(key).setValue(user)
  }

  override fun update(user: UserModel) {
    var foundUser: UserModel? = users.find { u -> u.id == user.id }
    if (foundUser != null){
      foundUser.name = user.name
      foundUser.email = user.email
      foundUser.passwordHash = user.passwordHash
      foundUser.hillforts = ArrayList(user.hillforts)
      foundUser.favourites = ArrayList(user.favourites)
    }

    db.child("users").child(userId).child("hillforts").child(user.fbId).setValue(user)

  }

  override fun delete(user: UserModel) {
    db.child("users").child(userId).child("hillforts").child(user.fbId).removeValue()
    users.remove(user)
  }

  override fun clear() {
    users.clear()
  }

  fun fetchPlacemarks(userReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(users) { it.getValue<UserModel>(UserModel::class.java) }
        userReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    users.clear()
    db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
  }

}