package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.TourDao
import com.grupo3.historyar.data.database.entities.TourEntity
import com.grupo3.historyar.data.database.entities.toDomain
import com.grupo3.historyar.data.network.api.services.TourService
import com.grupo3.historyar.data.network.model.toDatabase
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.models.toDomain
import javax.inject.Inject

class TourRepository @Inject constructor(
    private val tourDao: TourDao,
    private val tourService: TourService
) {

    suspend fun getTourById(id: String): Tour {
        val tourEntity = tourDao.getTourById(id)
        return if (tourEntity == null) {
            val tourModel = tourService.getTourById(id)
            saveTour(tourModel.toDatabase(false))
            tourModel.toDomain(false)
        } else {
            tourEntity.toDomain()
        }
    }

    suspend fun getCloseExperiences(latitude: Double, longitude: Double): List<Tour> {
        val tourListModel = tourService.getCloseExperiences(latitude, longitude)
        if (tourListModel.isNotEmpty()) {
            tourDao.insertTourList(tourListModel.map { it.toDatabase(false) })
        }
        return tourListModel.map {  it.toDomain(false) }
    }

    suspend fun getPreviousExperiences(lastTourIds: List<String>): List<Tour> {
        val tourListModel = tourService.getPreviousExperiences(lastTourIds)
        if (tourListModel.isNotEmpty()) {
            tourDao.insertTourList(tourListModel.map { it.toDatabase(false) })
        }
        return tourListModel.map { it.toDomain(false) }
    }

    suspend fun getAll(): List<Tour> {
        val tourListModel = tourService.getAll()
        if (tourListModel.isNotEmpty()) {
            tourDao.insertTourList(tourListModel.map { it.toDatabase(false) })
        }
        return tourListModel.map { it.toDomain(false) }
    }

    suspend fun saveTour(tour: TourEntity) {
        tourDao.insertTour(tour)
    }
}