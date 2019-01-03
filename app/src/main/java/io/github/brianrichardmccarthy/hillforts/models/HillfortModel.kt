package io.github.brianrichardmccarthy.hillforts.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

@Parcelize
data class HillfortModel(var id: Long = Random().nextLong(),
                         var title: String = "",
                         var description: String = "",
                         var images: MutableList<String> = ArrayList(),
                         var location: Location = Location(),
                         var visited: Boolean = false,
                         var notes: String = "",
                         var dayVisited: Int = 1,
                         var monthVisited: Int = 1,
                         var yearVisited: Int = 2018): Parcelable
