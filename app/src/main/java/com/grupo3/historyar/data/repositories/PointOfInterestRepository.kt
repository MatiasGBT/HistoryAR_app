package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.dao.PointOfInterestDao
import com.grupo3.historyar.data.database.entities.PointOfInterestEntity
import com.grupo3.historyar.data.network.api.services.PointOfInterestService
import com.grupo3.historyar.models.PointOfInterest
import com.grupo3.historyar.models.toDomain
import javax.inject.Inject

class PointOfInterestRepository @Inject constructor(
    private val pointDao: PointOfInterestDao,
    private val pointService: PointOfInterestService
) {

    suspend fun getPointById(id: String): PointOfInterest? {
        val pointEntity = pointDao.getPointById(id)
        if (pointEntity == null) {
            //val pointModel = pointService.getPointById(id)
            val pointModel = PointOfInterestEntity(
                "1",
                "Plaza de Mayo",
                "https://i.imgur.com/IGB1rxN.jpg",
                "",
                "",
                ""
            )
            savePoint(pointModel)
            return pointModel.toDomain()
        } else {
            return pointEntity.toDomain()
        }
    }

    suspend fun getPointsByTourId(idTour: String): List<PointOfInterest>? {
        //val pointListModel = pointService.getPointsByTourId()
        //pointDao.insertPointList(pointListModel.toDatabase())
        //return pointListModel.map { it.toDomain() }
        val pointListModel = listOf(
            PointOfInterestEntity(
                "1",
                "La Recova",
                "https://i.imgur.com/k8PLEYd.jpeg",
                "-34.603759",
                "-58.381549",
                "https://tesis-web.onrender.com/media/modelos/halloween_q7MaqI4.glb"
            ),
            PointOfInterestEntity(
                "2",
                "Colegio San Carlos",
                "https://i.imgur.com/WCscTqh.jpeg",
                "-34.6010855",
                "-58.3831868989758",
                "https://tesis-web.onrender.com/media/modelos/halloween_q7MaqI4.glb"
            ),
            PointOfInterestEntity(
                "3",
                "Plaza de Mayo",
                "https://i.imgur.com/IGB1rxN.jpg",
                "-34.611293",
                "-58.398129",
                "https://tesis-web.onrender.com/media/modelos/halloween_q7MaqI4.glb"
            )
        )
        pointDao.insertPointList(pointListModel)
        return pointListModel.map { it.toDomain() }
    }

    suspend fun savePoint(point: PointOfInterestEntity) {
        pointDao.insertPoint(point)
    }
}