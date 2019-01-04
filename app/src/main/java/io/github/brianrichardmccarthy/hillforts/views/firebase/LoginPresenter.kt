package io.github.brianrichardmccarthy.hillforts.views.firebase

import com.google.firebase.auth.FirebaseAuth
import io.github.brianrichardmccarthy.hillforts.models.HillfortFireStore
import io.github.brianrichardmccarthy.hillforts.models.UserFirebase
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.models.UserStore
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import io.github.brianrichardmccarthy.hillforts.views.VIEW
import io.github.brianrichardmccarthy.hillforts.views.user.HillfortSettingsFragment
import org.jetbrains.anko.db.NULL
import org.jetbrains.anko.toast

class LoginPresenter(view: BaseView) : BasePresenter(view) {

  var auth: FirebaseAuth = FirebaseAuth.getInstance()
  var fireStore: UserFirebase? = null

  init {
    if (app.users is UserStore) {
      fireStore = app.users as UserFirebase
    }
  }

  fun doLogin(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        if (fireStore != null) {
          fireStore!!.fetchPlacemarks {
            initCurrentUser(email, password)
            view?.hideProgress()
            view?.navigateTo(VIEW.LIST)
          }
        } else {
          app.currentUser = UserModel(email = email, passwordHash = password)
          view?.hideProgress()
          view?.navigateTo(VIEW.LIST)
        }
      } else {
        view?.hideProgress()
        view?.toast("Sign Up Failed: ${task.exception?.message}")
      }
    }
  }

  fun doSignUp(email: String, password: String) {
    view?.showProgress()
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        initCurrentUser(email, password)
        view?.hideProgress()
        view?.navigateTo(VIEW.LIST)
      } else {
        view?.hideProgress()
        view?.toast("Sign Up Failed: ${task.exception?.message}")
      }
    }
  }

  private fun initCurrentUser(email: String, password: String) {
    if (fireStore!!.findAll().size > 0) {
      fireStore!!.findAll().forEach {
        if (it.email == email) {
          app.currentUser = it
        }
      }
    } else {
        fireStore!!.create(UserModel(email = email, passwordHash = password))
        app.currentUser = fireStore!!.findAll().last()
    }

  }

}