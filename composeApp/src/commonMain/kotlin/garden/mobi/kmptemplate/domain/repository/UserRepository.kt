package garden.mobi.kmptemplate.domain.repository

import garden.mobi.kmptemplate.domain.model.User

interface UserRepository {
    fun findUser(name : String): User?
    fun addUsers(users : List<User>)
}

