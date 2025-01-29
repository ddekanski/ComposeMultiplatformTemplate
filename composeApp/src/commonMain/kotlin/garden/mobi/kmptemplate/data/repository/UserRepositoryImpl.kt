package garden.mobi.kmptemplate.data.repository

import garden.mobi.kmptemplate.data.DefaultData
import garden.mobi.kmptemplate.domain.model.User
import garden.mobi.kmptemplate.domain.repository.UserRepository

/**
 * Repository to provide a "Hello" data
 */

class UserRepositoryImpl : UserRepository {

    private val _users = arrayListOf<User>()
    init {
        _users.add(DefaultData.DEFAULT_USER)
    }

    override fun findUser(name: String): User? {
        return _users.firstOrNull { it.name == name }
    }

    override fun addUsers(users : List<User>) {
        _users.addAll(users)
    }
}