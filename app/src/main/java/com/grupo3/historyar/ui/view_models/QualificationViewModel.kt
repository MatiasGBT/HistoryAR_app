package com.grupo3.historyar.ui.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.database.entities.UserEntity
import com.grupo3.historyar.data.repositories.QualificationRepository
import com.grupo3.historyar.models.Qualification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QualificationViewModel @Inject constructor(private val qualificationRepository: QualificationRepository): ViewModel() {
    val commentsModel = MutableLiveData<List<Qualification>>()
    val commentsAreLoading = MutableLiveData<Boolean>()

    fun getQualificationsByTourId(idTour: String) {
        viewModelScope.launch {
            commentsAreLoading.postValue(true)
            val commentList = qualificationRepository.getByTourId(idTour)
            if (commentList != null) {
                commentsModel.postValue(commentList!!)
            } else {
                commentsModel.postValue(emptyList())
            }
            commentsAreLoading.postValue(false)
        }
    }

    fun saveQualification(qualification: Qualification) {
        viewModelScope.launch {
            qualificationRepository.saveQualification(qualification)
        }
    }
}