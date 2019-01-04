package io.github.brianrichardmccarthy.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var name: String="",
                     var email: String="",
                     var passwordHash: String="",
                     var hillforts: MutableList<HillfortModel> = ArrayList(),
                     var favourites: MutableList<HillfortModel> = ArrayList(),
                     var fbId : String = ""): Parcelable
