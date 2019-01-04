package io.github.brianrichardmccarthy.hillforts.models

interface UserStore {
  fun findAll(): MutableList<UserModel>
  fun findById(id: Long): UserModel?
  fun create(user: UserModel)
  fun update(user: UserModel)
  fun delete(user: UserModel)
  fun clear()
}
