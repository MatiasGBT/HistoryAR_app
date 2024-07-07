package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.UserDao
import com.grupo3.historyar.data.database.entities.toDomain
import com.grupo3.historyar.data.network.api.services.SubscriptionService
import com.grupo3.historyar.data.network.api.services.UserService
import com.grupo3.historyar.data.network.model.toDatabase
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.Subscription
import com.grupo3.historyar.models.User
import com.grupo3.historyar.models.toModel
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val subscriptionService: SubscriptionService
) {

    suspend fun getUserSubscription(currentUser: User): Subscription {
        return subscriptionService.getUserSubscription(currentUser.id).toDomain()
    }
}