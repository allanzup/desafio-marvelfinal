package br.com.zup.marvel.ui.Login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.EMAIL_ERROR_MESSAGE
import br.com.zup.marvel.LOGIN_ERROR_MESSAGE
import br.com.zup.marvel.PASSWORD_ERROR_MESSAGE
import br.com.zup.marvel.data.model.User
import br.com.zup.marvel.data.repository.AuthenticationRepository

class LoginViewModel:ViewModel() {
    private val authRepository = AuthenticationRepository()

    private var _userLoginTo = MutableLiveData<User>()
    val userLoginTo: LiveData<User> = _userLoginTo

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState
    fun validateUserData(user: User) {
        when {
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                loginUser(user)
            }
        }
    }
    private fun loginUser(user: User) {
        try {
            authRepository.loginUser(user.email, user.password)
                .addOnSuccessListener {
                    _userLoginTo.value = user
                }.addOnFailureListener {
                    _errorState.value = LOGIN_ERROR_MESSAGE + it.message
                }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}