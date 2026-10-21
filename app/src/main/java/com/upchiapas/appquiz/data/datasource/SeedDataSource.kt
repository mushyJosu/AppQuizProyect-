package com.upchiapas.appquiz.data.datasource

import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta

class SeedDataSource {

    fun obtenerPreguntas(): List<Pregunta> = preguntasSemilla

    private fun pregunta(
        id: Int,
        enunciado: String,
        correcta: String,
        incorrectas: List<String>,
        categoria: Categoria
    ) = Pregunta(
        id = id,
        enunciado = enunciado,
        opciones = listOf(correcta) + incorrectas,
        respuestaCorrecta = correcta,
        categoria = categoria
    )

    private val preguntasSemilla = listOf(
        //Java
        pregunta(1, "¿Qué es un constructor?", "Método que se ejecuta al crear un objeto",
            listOf("Una variable global", "Un tipo de bucle", "Una librería externa"), Categoria.JAVA),
        pregunta(2, "¿Qué palabra clave se usa para heredar de una clase en Java?", "extends",
            listOf("implements", "inherits", "super"), Categoria.JAVA),
        pregunta(3, "¿Qué palabra clave impide que una variable cambie su valor?", "final",
            listOf("static", "const", "abstract"), Categoria.JAVA),
        pregunta(4, "¿Cómo se llama el método donde empieza a ejecutarse un programa Java?", "main",
            listOf("start", "run", "init"), Categoria.JAVA),
        pregunta(5, "¿Qué tipo de dato guarda únicamente true o false?", "boolean",
            listOf("int", "String", "char"), Categoria.JAVA),

        //SQL
        pregunta(6, "¿Qué cláusula filtra filas en una consulta?", "WHERE",
            listOf("ORDER BY", "JOIN", "DISTINCT"), Categoria.SQL),
        pregunta(7, "¿Qué sentencia agrega filas nuevas a una tabla?", "INSERT",
            listOf("UPDATE", "SELECT", "DELETE"), Categoria.SQL),
        pregunta(8, "¿Qué cláusula ordena los resultados de una consulta?", "ORDER BY",
            listOf("GROUP BY", "WHERE", "HAVING"), Categoria.SQL),
        pregunta(9, "¿Qué función cuenta el número de filas?", "COUNT",
            listOf("SUM", "AVG", "MAX"), Categoria.SQL),
        pregunta(10, "¿Qué cláusula combina filas de dos tablas relacionadas?", "JOIN",
            listOf("LIMIT", "DISTINCT", "GROUP BY"), Categoria.SQL),

        //C++
        pregunta(11, "¿Qué operador obtiene la dirección de una variable?", "&",
            listOf("*", "->", "::"), Categoria.CPP),
        pregunta(12, "¿Qué operador accede a un miembro a través de un puntero a un objeto?", "->",
            listOf("::", "&&", "%"), Categoria.CPP),
        pregunta(13, "¿Qué biblioteca se incluye para usar cout y cin?", "iostream",
            listOf("cmath", "string", "vector"), Categoria.CPP),
        pregunta(14, "¿Qué palabra clave libera la memoria reservada con new?", "delete",
            listOf("free", "remove", "drop"), Categoria.CPP),
        pregunta(15, "¿Cuál es el operador de resolución de ámbito?", "::",
            listOf("->", ".", "#"), Categoria.CPP),

        //Kotlin
        pregunta(16, "¿Qué palabra clave declara una variable inmutable?", "val",
            listOf("var", "let", "const"), Categoria.KOTLIN),
        pregunta(17, "¿Qué palabra clave declara una variable que puede cambiar su valor?", "var",
            listOf("val", "let", "mut"), Categoria.KOTLIN),
        pregunta(18, "¿Qué símbolo marca un tipo como nulable?", "?",
            listOf("!", "*", "@"), Categoria.KOTLIN),
        pregunta(19, "¿Qué palabra clave declara una función?", "fun",
            listOf("func", "function", "def"), Categoria.KOTLIN),
        pregunta(20, "¿Qué tipo de clase genera automáticamente equals, hashCode y copy?", "data class",
            listOf("enum class", "sealed class", "object"), Categoria.KOTLIN)
    )
}