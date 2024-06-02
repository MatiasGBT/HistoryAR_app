package com.grupo3.historyar.data.repositories

import com.grupo3.historyar.data.network.api.services.QualificationService
import com.grupo3.historyar.data.network.model.QualificationModel
import com.grupo3.historyar.data.network.model.PointOfInterestModel
import com.grupo3.historyar.data.network.model.TourModel
import com.grupo3.historyar.data.network.model.UserModel
import com.grupo3.historyar.data.network.model.toDomain
import com.grupo3.historyar.models.Qualification
import com.grupo3.historyar.models.User
import javax.inject.Inject

class QualificationRepository @Inject constructor(
    private val qualificationService: QualificationService,
    private val userRepository: UserRepository
) {

    suspend fun getByTourId(idTour: String): List<Qualification>? {
        val commentListModel = qualificationService.getByTourId(idTour)
        val commentList = commentListModel.map {
            val user: User = userRepository.getUserById(it.idUser)
            it.toDomain(user)
        }
        return commentList
    }

    suspend fun saveQualification(qualification: Qualification) {
        qualificationService.save(qualification)
    }

    suspend fun deleteQualification(qualification: Qualification) {
        qualificationService.delete(qualification.id)
    }
}