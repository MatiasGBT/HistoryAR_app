package com.grupo3.historyar.models

import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.grupo3.historyar.data.database.entities.PointOfInterestEntity

data class PointOfInterest(
    val id: String,
    val name: String,
    val image: String,
    val latitude: String,
    val longitude: String,
    val model: String,
    var modelRenderable: Renderable? = null,
    var modelView: ViewRenderable? = null
)