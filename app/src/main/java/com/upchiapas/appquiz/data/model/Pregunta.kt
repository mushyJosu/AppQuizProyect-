package com.upchiapas.appquiz.data.model

data class Pregunta(
    //representacion del contenido de la pregunta
    val id: Int,
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrecta: String,
    val categoria: Categoria, //tema de categoria enum
    val esDelUsuario: Boolean = false
)
