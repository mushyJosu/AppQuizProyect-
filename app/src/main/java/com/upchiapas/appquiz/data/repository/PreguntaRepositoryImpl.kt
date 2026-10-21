package com.upchiapas.appquiz.data.repository

import com.upchiapas.appquiz.data.datasource.SeedDataSource
import com.upchiapas.appquiz.data.datasource.UserQuestionsDataSource
import com.upchiapas.appquiz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreguntaRepositoryImpl(
    private val seed: SeedDataSource,
    private val usuario: UserQuestionsDataSource
) : PreguntaRepository {
                           //la lista se actualiza constamente
    override val preguntas: Flow<List<Pregunta>> =
        usuario.preguntas.map { delUsuario -> seed.obtenerPreguntas() + delUsuario }

    override suspend fun agregarPregunta(pregunta: Pregunta) {
        usuario.guardar(pregunta)
    }
}