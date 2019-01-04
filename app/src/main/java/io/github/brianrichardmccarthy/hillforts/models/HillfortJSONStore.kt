package io.github.brianrichardmccarthy.hillforts.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.brianrichardmccarthy.hillforts.helpers.exists
import io.github.brianrichardmccarthy.hillforts.helpers.read
import io.github.brianrichardmccarthy.hillforts.helpers.write
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

fun generateRandomId(): Long {
  return Random().nextLong()
}

class HillfortJSONStore : HillfortStore, AnkoLogger {

  val JSON_HILLFORT_FILE = "hillforts.json"
  val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
  val hillfortListType = object : TypeToken<ArrayList<HillfortModel>>() {}.type

  val context: Context
  var hillforts = mutableListOf<HillfortModel>()

  constructor (context: Context, initialHillforts: List<HillfortModel>) {
    this.context = context
    if (exists(context, JSON_HILLFORT_FILE)) {
      deserialize()
    } else {
      initialHillforts.forEach { create(it.copy()) }
    }
  }

  override fun findAll(): MutableList<HillfortModel> {
    return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = generateRandomId()
    hillforts.add(hillfort)
    serialize()
  }

  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
    serialize()
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
    if (foundHillfort != null) {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.images = ArrayList(hillfort.images)
      foundHillfort.location.lat = hillfort.location.lat
      foundHillfort.location.lng = hillfort.location.lng
      foundHillfort.location.zoom = hillfort.location.zoom
      foundHillfort.visited = hillfort.visited
      foundHillfort.notes = hillfort.notes
      foundHillfort.dayVisited = hillfort.dayVisited
      foundHillfort.monthVisited = hillfort.monthVisited
      foundHillfort.yearVisited = hillfort.yearVisited
      foundHillfort.rating = hillfort.rating
      serialize()
      logAll()
    }
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(hillforts, hillfortListType)
    write(context, JSON_HILLFORT_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_HILLFORT_FILE)
    hillforts = Gson().fromJson(jsonString, hillfortListType)
  }

  fun logAll(){
    hillforts.forEach { info("${it}") }
  }
}
