package com.upchiapas.appquiz.data.repository
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
                              //cumplir con el contrato de la interface
class FakePreguntaRepository : PreguntaRepository {
    //implementacion falsa para probar

    private val preguntasDeEjemplo = listOf(
        Pregunta(
            id = 1,
            enunciado = "¿Qué es un constructor?",
            opciones = listOf(
                "Método que se ejecuta al crear un objeto",
                "Una variable global",
                "Un tipo de bucle",
                "Una librería externa"
            ),
            respuestaCorrecta = "Método que se ejecuta al crear un objeto",
            categoria = Categoria.JAVA
        ),
        Pregunta(
            id = 2,
            enunciado = "¿Qué cláusula filtra filas en una consulta?",
            opciones = listOf("WHERE", "ORDER BY", "JOIN", "DISTINCT"),
            respuestaCorrecta = "WHERE",
            categoria = Categoria.SQL
        )
        // sigue con C++ y Kotlin
    )

    private val _preguntas = MutableStateFlow(preguntasDeEjemplo)
    override val preguntas: Flow<List<Pregunta>> = _preguntas.asStateFlow()

    override suspend fun agregarPregunta(pregunta: Pregunta) {
        _preguntas.update { it + pregunta }
    }
}