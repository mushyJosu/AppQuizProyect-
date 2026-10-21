package com.upchiapas.appquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta
import com.upchiapas.appquiz.presentation.screens.Fase
import com.upchiapas.appquiz.presentation.screens.QuizUiState
import com.upchiapas.appquiz.ui.theme.AppQuizTheme
import com.upchiapas.appquiz.ui.theme.OpcionAzul
import com.upchiapas.appquiz.ui.theme.OpcionMorado
import com.upchiapas.appquiz.ui.theme.OpcionNaranja
import com.upchiapas.appquiz.ui.theme.OpcionTurquesa

private val letras = listOf("A", "B", "C", "D")
private val coloresLetra = listOf(OpcionAzul, OpcionMorado, OpcionNaranja, OpcionTurquesa)

@Composable
fun PreguntaContent(
    uiState: QuizUiState,
    onOpcionElegida: (String) -> Unit,
    onAbandonar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pregunta = uiState.preguntaActual ?: return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onAbandonar) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.abandonar_partida)
                )
            }
            Text(
                text = stringResource(R.string.sesion_de_quiz),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = stringResource(R.string.pregunta_n_de_total, uiState.indiceActual + 1, uiState.total),
                style = MaterialTheme.typography.labelLarge
            )
        }
        LinearProgressIndicator(
            progress = { (uiState.indiceActual + 1) / uiState.total.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        MarcadorQuiz(
            total = uiState.total,
            correctas = uiState.correctas,
            incorrectas = uiState.incorrectas,
            restantes = uiState.restantes,
            errorResaltado = uiState.opcionElegida != null &&
                    uiState.opcionElegida != pregunta.respuestaCorrecta
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = pregunta.enunciado, style = MaterialTheme.typography.headlineSmall)
                Text(text = stringResource(R.string.selecciona_respuesta), style = MaterialTheme.typography.bodyMedium)
            }
        }
        uiState.opcionesActuales.forEachIndexed { indice, opcion ->
            OpcionRespuesta(
                letra = letras[indice],
                texto = opcion,
                colorLetra = coloresLetra[indice],
                estado = estadoDe(opcion, uiState),
                onClick = { onOpcionElegida(opcion) }
            )
        }
    }
}

private fun estadoDe(opcion: String, uiState: QuizUiState): EstadoOpcion = when {
    uiState.opcionElegida == null -> EstadoOpcion.NORMAL
    opcion == uiState.preguntaActual?.respuestaCorrecta -> EstadoOpcion.CORRECTA
    opcion == uiState.opcionElegida -> EstadoOpcion.INCORRECTA
    else -> EstadoOpcion.APAGADA
}

private val opcionesEjemplo = listOf(
    "Método que se ejecuta al crear un objeto", "Una variable global",
    "Un tipo de bucle", "Una librería externa"
)

private val estadoDeEjemplo = QuizUiState(
    fase = Fase.JUGANDO,
    preguntas = listOf(
        Pregunta(1, "¿Qué es un constructor?", opcionesEjemplo, opcionesEjemplo[0], Categoria.JAVA)
    ),
    opcionesActuales = opcionesEjemplo
)

@Preview(showBackground = true)
@Composable
private fun PreguntaSinResponderPreview() {
    AppQuizTheme {
        Surface { PreguntaContent(estadoDeEjemplo, onOpcionElegida = {}, onAbandonar = {}) }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreguntaRespondidaMalPreview() {
    AppQuizTheme {
        Surface {
            PreguntaContent(
                uiState = estadoDeEjemplo.copy(opcionElegida = opcionesEjemplo[1], incorrectas = 1),
                onOpcionElegida = {},
                onAbandonar = {}
            )
        }
    }
}