package br.com.zup.marvel.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.marvel.data.model.Marvel
import br.com.zup.marvel.data.repository.AuthenticationRepository

class HomeViewModel: ViewModel() {
    private val authRepository = AuthenticationRepository()

    private var _marvelListState = MutableLiveData<List<Marvel>>()
    val marvelListState: LiveData<List<Marvel>> = _marvelListState

    fun getUserName() = authRepository.getNameUser()

    fun getUserEmail() = authRepository.getEmailUser()

    fun logout() = authRepository.logoutOut()
    fun getListMarvel() {
       val listMarvel =  authRepository.getMarvelList()
        _marvelListState.value = listMarvel
    }

}