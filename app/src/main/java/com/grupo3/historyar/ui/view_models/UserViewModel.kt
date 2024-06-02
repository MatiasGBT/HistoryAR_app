package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.repositories.UserRepository
import com.grupo3.historyar.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val userModel = MutableLiveData<User>()

    fun getUserLoggedIn() {
        viewModelScope.launch {
            val currentUser = userRepository.getCurrentUser()
            userModel.postValue(currentUser)
        }
    }

    fun getUserById(idUser: String) {
        viewModelScope.launch {
            val user = userRepository.getUserById(idUser)
            userModel.postValue(user)
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            userRepository.saveUser(user)
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }
}