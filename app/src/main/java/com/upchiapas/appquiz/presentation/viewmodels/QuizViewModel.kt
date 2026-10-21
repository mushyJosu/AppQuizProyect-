package com.upchiapas.appquiz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta
import com.upchiapas.appquiz.data.repository.PreguntaRepository
import com.upchiapas.appquiz.presentation.screens.Fase
import com.upchiapas.appquiz.presentation.screens.QuizUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.upchiapas.appquiz.presentation.screens.FormularioState


private const val PREGUNTAS_POR_PARTIDA = 10
private const val PAUSA_FEEDBACK_MS = 1200L

class QuizViewModel(
    private val repository: PreguntaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private var todasLasPreguntas: List<Pregunta> = emptyList()
    private var jobAvance: Job? = null
    private  var jobGuardado: Job? = null


    init {
        viewModelScope.launch {
            repository.preguntas.collect { lista -> todasLasPreguntas = lista }
        }
    }

    fun onCategoriaSeleccionada(categoria: Categoria?) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }

    fun onEmpezar() {
        val categoria = _uiState.value.categoriaSeleccionada
        val seleccion = todasLasPreguntas
            .filter { categoria == null || it.categoria == categoria }
            .shuffled()
            .take(PREGUNTAS_POR_PARTIDA)
        if (seleccion.isEmpty()) return

        _uiState.update {
            it.copy(
                fase = Fase.JUGANDO,
                preguntas = seleccion,
                indiceActual = 0,
                opcionesActuales = seleccion.first().opciones.shuffled(),
                opcionElegida = null,
                correctas = 0,
                incorrectas = 0
            )
        }
    }

    fun onOpcionElegida(opcion: String) {
        val estado = _uiState.value
        val pregunta = estado.preguntaActual ?: return
        if (estado.fase != Fase.JUGANDO || estado.opcionElegida != null) return

        val acerto = opcion == pregunta.respuestaCorrecta
        _uiState.update {
            it.copy(
                opcionElegida = opcion,
                correctas = if (acerto) it.correctas + 1 else it.correctas,
                incorrectas = if (acerto) it.incorrectas else it.incorrectas + 1
            )
        }

        jobAvance = viewModelScope.launch {
            delay(PAUSA_FEEDBACK_MS)
            avanzar()
        }
    }

    private fun avanzar() {
        val estado = _uiState.value
        val siguiente = estado.indiceActual + 1

        if (siguiente >= estado.total) {
            _uiState.update { it.copy(fase = Fase.RESULTADOS, opcionElegida = null) }
        } else {
            val opciones = estado.preguntas[siguiente].opciones.shuffled()
            _uiState.update {
                it.copy(
                    indiceActual = siguiente,
                    opcionesActuales = opciones,
                    opcionElegida = null
                )
            }
        }
    }

    fun onJugarDeNuevo() = onEmpezar()

    fun onAgregarPregunta() {
        _uiState.update {
            it.copy(fase = Fase.AGREGAR, formulario = FormularioState(), avisoGuardado = false)
        }
    }

    fun onEnunciadoCambia(texto: String) {
        _uiState.update { it.copy(formulario = it.formulario.copy(enunciado = texto)) }
    }

    fun onOpcionCambia(indice: Int, texto: String) {
        _uiState.update {
            val nuevas = it.formulario.opciones.mapIndexed { i, opcion ->
                if (i == indice) texto else opcion
            }
            it.copy(formulario = it.formulario.copy(opciones = nuevas))
        }
    }

    fun onCorrectaElegida(indice: Int) {
        _uiState.update { it.copy(formulario = it.formulario.copy(indiceCorrecta = indice)) }
    }

    fun onCategoriaFormularioElegida(categoria: Categoria) {
        _uiState.update { it.copy(formulario = it.formulario.copy(categoria = categoria)) }
    }

    fun onGuardar() {
        val form = _uiState.value.formulario
        val categoria = form.categoria ?: return
        val indiceCorrecta = form.indiceCorrecta ?: return
        if (!form.puedeGuardar || jobGuardado?.isActive == true) return

        val opciones = form.opciones.map { it.trim() }
        val nueva = Pregunta(
            id = (todasLasPreguntas.maxOfOrNull { it.id } ?: 0) + 1,
            enunciado = form.enunciado.trim(),
            opciones = opciones,
            respuestaCorrecta = opciones[indiceCorrecta],
            categoria = categoria,
            esDelUsuario = true
        )

        jobGuardado = viewModelScope.launch {
            repository.agregarPregunta(nueva)
            _uiState.update {
                it.copy(fase = Fase.INICIO, formulario = FormularioState(), avisoGuardado = true)
            }
        }
    }

    fun onCancelarFormulario() {
        _uiState.update { it.copy(fase = Fase.INICIO, formulario = FormularioState()) }
    }

    fun onAvisoMostrado() {
        _uiState.update { it.copy(avisoGuardado = false) }
    }
    fun onVolverAlInicio() {
        jobAvance?.cancel()
        _uiState.update { QuizUiState() }
    }
}