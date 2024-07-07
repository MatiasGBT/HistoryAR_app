package com.grupo3.historyar.data.network.api.services

import com.grupo3.historyar.data.network.api.clients.TourApiClient
import com.grupo3.historyar.data.network.model.FavoriteTourModel
import com.grupo3.historyar.data.network.model.TourModel
import com.grupo3.historyar.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TourService @Inject constructor(private val api: TourApiClient, private val userRepository: UserRepository) {

    suspend fun getTourById(id: String): TourModel {
        return withContext(Dispatchers.IO) {
            val responseTour = api.getById(id)
            val tour = responseTour.body() ?: TourModel()
            val responseFavorite = api.getFavoriteUserTour(userRepository.getCurrentUser().id)
            tour.isFavorite = tour.id == (responseFavorite.body()?.favoriteTourId ?: "")
            tour
        }
    }

    suspend fun getCloseExperiences(latitude: Double, longitude: Double): List<TourModel> {
        return withContext(Dispatchers.IO) {
            val responseTour = api.getCloseExperiences(latitude, longitude)
            val tours = responseTour.body() ?: emptyList()
            val responseFavorite = api.getFavoriteUserTour(userRepository.getCurrentUser().id)
            tours.forEach {
                it.isFavorite = it.id == (responseFavorite.body()?.favoriteTourId ?: "")
            }
            tours
        }
    }

    suspend fun getPreviousExperiences(lastTourIds: List<String>): List<TourModel> {
        return withContext(Dispatchers.IO) {
            val previousExperiences: List<TourModel> = lastTourIds.map {
                api.getById(it).body()!!
            }
            val responseFavorite = api.getFavoriteUserTour(userRepository.getCurrentUser().id)
            previousExperiences.forEach {
                it.isFavorite = it.id == (responseFavorite.body()?.favoriteTourId ?: "")
            }
            previousExperiences
        }
    }

    suspend fun getAll(): List<TourModel> {
        return withContext(Dispatchers.IO) {
            val responseTour = api.getAll()
            val tours = responseTour.body() ?: emptyList()
            val responseFavorite = api.getFavoriteUserTour(userRepository.getCurrentUser().id)
            tours.forEach {
                it.isFavorite = it.id == (responseFavorite.body()?.favoriteTourId ?: "")
            }
            tours
        }
    }

    suspend fun setFavoriteTour(idUser: String, favoriteTour: FavoriteTourModel): TourModel {
        return withContext(Dispatchers.IO) {
            val response = api.updateFavoriteUserTour(idUser, favoriteTour)
            response.body() ?: TourModel()
        }
    }
}