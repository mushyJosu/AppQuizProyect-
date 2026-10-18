package com.upchiapas.appquiz.data.repository

import com.upchiapas.appquiz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow

interface PreguntaRepository {
    //nos dice que se puede hacer con las pregunta
    //pero no como
    val preguntas: Flow<List<Pregunta>> //todas las preguntas
    suspend fun agregarPregunta(pregunta: Pregunta)
}