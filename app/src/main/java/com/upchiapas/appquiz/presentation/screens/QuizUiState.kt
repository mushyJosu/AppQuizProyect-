package com.upchiapas.appquiz.presentation.screens
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta

enum class Fase { INICIO, JUGANDO, RESULTADOS, AGREGAR}

enum class MensajeFinal { EXCELENTE, REGULAR, REPASAR}

data class FormularioState(
    val enunciado: String= "",
    val opciones: List<String> = List(4){""},
    val indiceCorrecta: Int? = null,
    val categoria: Categoria? = null
){
    val puedeGuardar: Boolean
        get() = enunciado.isNotBlank() &&
                opciones.all { it.isNotBlank() } &&
                opciones.map { it.trim() }.toSet().size == opciones.size &&
                indiceCorrecta != null &&
                categoria != null
}
data class QuizUiState(
    val fase: Fase = Fase.INICIO,
    val categoriaSeleccionada: Categoria? = null,
    val preguntas: List<Pregunta> = emptyList(),
    val indiceActual: Int = 0,
    val opcionesActuales: List<String> = emptyList(),
    val opcionElegida: String? = null,
    val correctas: Int = 0,
    val incorrectas: Int = 0,
    val formulario: FormularioState = FormularioState(),
    val avisoGuardado: Boolean= false
)
 {
    val total: Int
        get() = preguntas.size

    val restantes: Int
        get() = total - correctas - incorrectas

    val preguntaActual: Pregunta?
        get() = preguntas.getOrNull(indiceActual)

    val porcentaje: Int
        get() = if (total == 0) 0 else correctas * 100 / total

    val mensajeFinal: MensajeFinal
        get() = when {
            porcentaje >= 80 -> MensajeFinal.EXCELENTE
            porcentaje >= 50 -> MensajeFinal.REGULAR
            else -> MensajeFinal.REPASAR
        }
}