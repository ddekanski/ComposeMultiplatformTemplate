package garden.mobi.kmptemplate.view.greeting

import androidx.lifecycle.ViewModel
import garden.mobi.kmptemplate.domain.repository.UserRepository
import garden.mobi.kmptemplate.getPlatform

class GreetingViewModel(private val repository: UserRepository) : ViewModel() {

    fun sayHello(name : String) : String{
        val foundUser = repository.findUser(name)
        val platform = getPlatform()
        return foundUser?.let { "Hello '$it' from ${platform.name}" } ?: "User '$name' not found!"
    }
}