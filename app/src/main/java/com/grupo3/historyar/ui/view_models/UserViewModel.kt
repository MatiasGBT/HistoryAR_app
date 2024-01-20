package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.database.entities.UserEntity
import com.grupo3.historyar.data.repositories.UserRepository
import com.grupo3.historyar.models.User
import com.grupo3.historyar.models.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val userModel = MutableLiveData<User>()

    fun getUserLoggedIn() {
        viewModelScope.launch {
            val currentUser = userRepository.getCurrentUser()
            if (currentUser != null)
                userModel.postValue(currentUser.toDomain())
        }
    }

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.deleteUser()
            userRepository.saveUser(user)
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            userRepository.deleteUser()
        }
    }
}