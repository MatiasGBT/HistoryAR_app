package com.grupo3.historyar.ui.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo3.historyar.data.repositories.SubscriptionRepository
import com.grupo3.historyar.data.repositories.UserRepository
import com.grupo3.historyar.models.Subscription
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val subModel = MutableLiveData<Subscription>()

    fun getUserSubscription() {
        viewModelScope.launch {
            val currentUser = userRepository.getCurrentUser()
            val subscription = subscriptionRepository.getUserSubscription(currentUser)
            if (subscription.id != "") {
                subscription.isSubValid = isSubValid(subscription.creationDate)
                subscription.daysUntilMonth = daysUntilMonth(subscription.creationDate)
            }
            subModel.postValue(subscription)
        }
    }

    private fun isSubValid(subCreationDate: Date): Boolean {
        val currentDate = LocalDate.now()
        val subCreationLocalDate = toLocalDate(subCreationDate)
        val subCreationDatePlusMonth = subCreationLocalDate.plusMonths(1)
        return currentDate.isAfter(subCreationDatePlusMonth) || subCreationDatePlusMonth.isEqual(subCreationDatePlusMonth)
    }

    private fun daysUntilMonth(subCreationDate: Date): Long {
        val currentDate = LocalDate.now()
        val subCreationLocalDate = toLocalDate(subCreationDate)
        val subCreationDatePlusMonth = subCreationLocalDate.plusMonths(1)
        return ChronoUnit.DAYS.between(currentDate, subCreationDatePlusMonth)
    }

    private fun toLocalDate(date: Date): LocalDate {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}