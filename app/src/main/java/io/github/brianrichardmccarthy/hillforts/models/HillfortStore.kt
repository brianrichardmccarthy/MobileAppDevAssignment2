package io.github.brianrichardmccarthy.hillforts.models


interface HillfortStore {
  fun findAll(): MutableList<HillfortModel>
  fun findById(id: Long): HillfortModel?
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
  fun delete(hillfort: HillfortModel)
  fun clear()
}
