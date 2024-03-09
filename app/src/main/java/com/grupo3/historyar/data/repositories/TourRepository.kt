package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.TourDao
import com.grupo3.historyar.data.database.entities.TourEntity
import com.grupo3.historyar.data.network.api.services.TourService
import com.grupo3.historyar.models.Tour
import com.grupo3.historyar.models.toDomain
import javax.inject.Inject

class TourRepository @Inject constructor(
    private val tourDao: TourDao,
    private val tourService: TourService
) {

    suspend fun getTourById(id: String): Tour? {
        val tourEntity = tourDao.getTourById(id)
        if (tourEntity == null) {
            //val tourModel = tourService.getTourById(id)
            val tourModel = TourEntity(
                "1",
                "Revolución de Mayo",
                "Reviví el momento en el que Argentina se volvió un país soberano.",
                25,
                "https://i.imgur.com/IGB1rxN.jpg",
                "-34.611293",
                "-58.398129",
                true
            )
            saveTour(tourModel)
            return tourModel.toDomain()
        } else {
            return tourEntity.toDomain()
        }
    }

    suspend fun getCloseExperiences(): List<Tour>? {
        //val tourListModel = tourService.getCloseExperiences()
        //tourDao.insertTourList(tourListModel.toDatabase())
        //return tourListModel.map { it.toDomain() }
        val tourListModel = listOf(
            TourEntity(
                "1",
                "Revolución de Mayo",
                "Reviví el momento en el que Argentina se volvió un país soberano.",
                25,
                "https://i.imgur.com/IGB1rxN.jpg",
                "-34.611293",
                "-58.398129",
                true
            ),
            TourEntity(
                "2",
                "Obelisco Inmortal",
                "Explora la construcción del obelisco, considerado ícono de Buenos Aires.",
                10,
                "https://i.imgur.com/Pe3CPEB.jpg",
                "-34.603759",
                "-58.381549",
                false
            ),
            TourEntity(
                "3",
                "Creación del Teatro Colón",
                "Embárcate en un tour exclusivo para presenciar la creación en vivo del Teatro Colón.",
                15,
                "https://i.imgur.com/XaZRFFd.jpg",
                "-34.6010855",
                "-58.3831868989758",
                false
            )
        )
        tourDao.insertTourList(tourListModel)
        return tourListModel.map { it.toDomain() }
    }

    suspend fun getPreviousExperiences(): List<Tour>? {
        //val tourListModel = tourService.getPreviousExperiences()
        //tourDao.insertTourList(tourListModel)
        //return tourListModel.map { it.toDomain() }
        val tourListModel = listOf(
            TourEntity(
                "1",
                "Revolución de Mayo",
                "Reviví el momento en el que Argentina se volvió un país soberano.",
                25,
                "https://i.imgur.com/IGB1rxN.jpg",
                "-34.611293",
                "-58.398129",
                true
            ),
            TourEntity(
                "2",
                "Obelisco Inmortal",
                "Explora la construcción del obelisco, considerado ícono de Buenos Aires.",
                10,
                "https://i.imgur.com/Pe3CPEB.jpg",
                "-34.603759",
                "-58.381549",
                false
            ),
            TourEntity(
                "3",
                "Creación del Teatro Colón",
                "Embárcate en un tour exclusivo para presenciar la creación en vivo del Teatro Colón.",
                15,
                "https://i.imgur.com/XaZRFFd.jpg",
                "-34.6010855",
                "-58.3831868989758",
                false
            )
        )
        tourDao.insertTourList(tourListModel)
        return tourListModel.map { it.toDomain() }
    }

    suspend fun getAll(): List<Tour>? {
        //val tourListModel = tourService.getAll()
        //tourDao.insertTourList(tourListModel.toDatabase())
        //return tourListModel.map { it.toDomain() }
        val tourListModel = listOf(
            TourEntity(
                "1",
                "Revolución de Mayo",
                "Reviví el momento en el que Argentina se volvió un país soberano.",
                25,
                "https://i.imgur.com/IGB1rxN.jpg",
                "-34.611293",
                "-58.398129",
                true
            ),
            TourEntity(
                "2",
                "Obelisco Inmortal",
                "Explora la construcción del obelisco, considerado ícono de Buenos Aires.",
                10,
                "https://i.imgur.com/Pe3CPEB.jpg",
                "-34.603759",
                "-58.381549",
                false
            ),
            TourEntity(
                "3",
                "Creación del Teatro Colón",
                "Embárcate en un tour exclusivo para presenciar la creación en vivo del Teatro Colón.",
                15,
                "https://i.imgur.com/XaZRFFd.jpg",
                "-34.6010855",
                "-58.3831868989758",
                false
            ),
            TourEntity(
                "4",
                "Obelisco Inmortal",
                "Explora la construcción del obelisco, considerado ícono de Buenos Aires.",
                10,
                "https://i.imgur.com/Pe3CPEB.jpg",
                "-34.603759",
                "-58.381549",
                false
            ),
            TourEntity(
                "5",
                "Creación del Teatro Colón",
                "Embárcate en un tour exclusivo para presenciar la creación en vivo del Teatro Colón.",
                15,
                "https://i.imgur.com/XaZRFFd.jpg",
                "-34.6010855",
                "-58.3831868989758",
                false
            )
        )
        tourDao.insertTourList(tourListModel)
        return tourListModel.map { it.toDomain() }
    }

    suspend fun saveTour(tour: TourEntity) {
        tourDao.insertTour(tour)
    }
}