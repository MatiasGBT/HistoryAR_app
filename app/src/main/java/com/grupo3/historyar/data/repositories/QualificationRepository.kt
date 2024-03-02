package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.database.entities.UserEntity
import com.grupo3.historyar.data.network.api.services.QualificationService
import com.grupo3.historyar.data.network.model.QualificationModel
import com.grupo3.historyar.data.network.model.PointOfInterestModel
import com.grupo3.historyar.data.network.model.TourModel
import com.grupo3.historyar.data.network.model.UserModel
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.Qualification
import javax.inject.Inject

class QualificationRepository @Inject constructor(
    private val qualificationService: QualificationService
) {

    suspend fun getByTourId(idTour: String): List<Qualification>? {
        //val commentListModel = qualificationService.getByTourId(idTour)
        //return commentListModel.map { it.toDomain() }
        val commentListModel = listOf(
            QualificationModel(
                "1",
                UserModel(fullName = "Leandro Retamar", photo = "https://i.imgur.com/mxC7YmH.jpg"),
                TourModel(id = idTour, pointsOnInterest = listOf(PointOfInterestModel())),
                "Me encanto el tour, se puede aprender mucho de la tecnología.",
                5
            ),
            QualificationModel(
                "2",
                UserModel(fullName = "Nicolás Labasse", photo = "https://i.imgur.com/mxC7YmH.jpg"),
                TourModel(id = idTour, pointsOnInterest = listOf(PointOfInterestModel())),
                "Muy buen recorrido, me la pase genial y aprendí mucho. Gracias.",
                5
            ),
            QualificationModel(
                "3",
                UserModel(fullName = "Matías BT", photo = "https://i.imgur.com/mxC7YmH.jpg"),
                TourModel(id = idTour, pointsOnInterest = listOf(PointOfInterestModel())),
                "Exijo que me devuelvan mi tiempo perdido en esta porquería.",
                1
            )
        )
        return commentListModel.map { it.toDomain() }
    }

    suspend fun saveQualification(qualification: Qualification) {
        qualificationService.save(qualification)
    }

    suspend fun deleteQualification(qualification: Qualification) {
        qualificationService.delete(qualification.id)
    }
}