package com.upchiapas.appquiz.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preguntas_usuario")

class UserQuestionsDataSource(private val context: Context) {

    private val clavePreguntas = stringPreferencesKey("preguntas_json")

    val preguntas: Flow<List<Pregunta>> = context.dataStore.data.map { preferencias ->
        preferencias[clavePreguntas]?.let(::leerJson) ?: emptyList()
    }

    suspend fun guardar(pregunta: Pregunta) {
        context.dataStore.edit { preferencias ->
            val actuales = preferencias[clavePreguntas]?.let(::leerJson) ?: emptyList()
            preferencias[clavePreguntas] = escribirJson(actuales + pregunta)
        }
    }

    private fun escribirJson(lista: List<Pregunta>): String {
        val arreglo = JSONArray()
        lista.forEach { pregunta ->
            arreglo.put(
                JSONObject()
                    .put("id", pregunta.id)
                    .put("enunciado", pregunta.enunciado)
                    .put("opciones", JSONArray(pregunta.opciones))
                    .put("respuestaCorrecta", pregunta.respuestaCorrecta)
                    .put("categoria", pregunta.categoria.name)
            )
        }
        return arreglo.toString()
    }

    private fun leerJson(texto: String): List<Pregunta> = runCatching {
        val arreglo = JSONArray(texto)
        List(arreglo.length()) { i ->
            val objeto = arreglo.getJSONObject(i)
            val opciones = objeto.getJSONArray("opciones")
            Pregunta(
                id = objeto.getInt("id"),
                enunciado = objeto.getString("enunciado"),
                opciones = List(opciones.length()) { j -> opciones.getString(j) },
                respuestaCorrecta = objeto.getString("respuestaCorrecta"),
                categoria = Categoria.valueOf(objeto.getString("categoria")),
                esDelUsuario = true
            )
        }
    }.getOrDefault(emptyList())
}