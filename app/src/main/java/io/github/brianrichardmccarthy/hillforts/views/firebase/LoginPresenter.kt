package io.github.brianrichardmccarthy.hillforts.views.firebase

import com.google.firebase.auth.FirebaseAuth
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import io.github.brianrichardmccarthy.hillforts.views.BasePresenter
import io.github.brianrichardmccarthy.hillforts.views.BaseView
import io.github.brianrichardmccarthy.hillforts.views.VIEW
import org.jetbrains.anko.toast

class LoginPresenter(view: BaseView) : BasePresenter(view) {

  var auth: FirebaseAuth = FirebaseAuth.getInstance()

  fun doLogin(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        app.currentUser = UserModel(email = email, passwordHash = password)
        view?.navigateTo(VIEW.LIST)
      } else {
        view?.toast("Sign Up Failed: ${task.exception?.message}")
      }
      view?.hideProgress()
    }
  }

  fun doSignUp(email: String, password: String) {
    view?.showProgress()
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        app.currentUser = UserModel(email = email, passwordHash = password)
        view?.navigateTo(VIEW.LIST)
      } else {
        view?.toast("Sign Up Failed: ${task.exception?.message}")
      }
      view?.hideProgress()
    }
  }
}