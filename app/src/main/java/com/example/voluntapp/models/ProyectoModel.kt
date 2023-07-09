package com.example.voluntapp.models

import java.io.Serializable

data class ProyectoModel(
    val id: String,
    val title: String,
    val description:String,
    val fecha_inicio:String,
    val fecha_fin:String,
    val cantidad:String,
    val cantidad_inscritos:String,
) : Serializable
