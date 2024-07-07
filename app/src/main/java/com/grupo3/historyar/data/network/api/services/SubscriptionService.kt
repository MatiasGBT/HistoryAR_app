package com.grupo3.historyar.data.network.api.services

import android.util.Log
import com.grupo3.historyar.data.network.api.clients.SubscriptionApiClient
import com.grupo3.historyar.data.network.api.clients.UserApiClient
import com.grupo3.historyar.data.network.model.SubscriptionModel
import com.grupo3.historyar.data.network.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubscriptionService @Inject constructor(private val api: SubscriptionApiClient) {

    suspend fun getUserSubscription(currentUserId: String): SubscriptionModel {
        return withContext(Dispatchers.IO) {
            val response = api.getAll()
            val subs = response.body() ?: emptyList()
            var currentSub = SubscriptionModel()
            subs.forEach {
                if (it.idUser == currentUserId){
                    currentSub = it
                }
            }
            currentSub
        }
    }
}