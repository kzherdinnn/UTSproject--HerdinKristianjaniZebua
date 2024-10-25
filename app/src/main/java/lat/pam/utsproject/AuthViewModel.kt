package lat.pam.utsproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    // Hardcoded user for demo
    private val validUser = User("demo", "password123")

    fun login(username: String, password: String): Boolean {
        val isValid = username == validUser.username && password == validUser.password
        _isLoggedIn.value = isValid
        return isValid
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}