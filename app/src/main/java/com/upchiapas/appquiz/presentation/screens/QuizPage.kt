package com.upchiapas.appquiz.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.appquiz.QuizApplication
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.data.model.Pregunta
import com.upchiapas.appquiz.presentation.components.InicioContent
import com.upchiapas.appquiz.presentation.components.PreguntaContent
import com.upchiapas.appquiz.presentation.components.ResultadosContent
import com.upchiapas.appquiz.presentation.viewmodels.QuizViewModel
import com.upchiapas.appquiz.ui.theme.AppQuizTheme   // el nombre que veas en Theme.kt

@JvmOverloads
@Composable
fun QuizPage(
    viewModel: QuizViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as QuizApplication).quizModule.factory

    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuizContent(
        uiState = uiState,
        onEmpezar = { viewModel.onEmpezar() },
        onOpcionElegida = { opcion -> viewModel.onOpcionElegida(opcion) },
        onJugarDeNuevo = { viewModel.onJugarDeNuevo() },
        onVolverAlInicio = { viewModel.onVolverAlInicio() },
        onCategoriaSeleccionada = { categoria -> viewModel.onCategoriaSeleccionada(categoria) },
    )
}
@Composable
fun QuizContent(
    uiState: QuizUiState,
    onEmpezar: () -> Unit,
    onOpcionElegida: (String) -> Unit,
    onJugarDeNuevo: () -> Unit,
    onVolverAlInicio: () -> Unit,
    modifier: Modifier = Modifier,
    onCategoriaSeleccionada:(Categoria?) -> Unit,
) {
    Scaffold(modifier = modifier) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.fase) {
                Fase.INICIO -> InicioContent(
                    categoriaSeleccionada = uiState.categoriaSeleccionada,
                    onCategoriaSeleccionada = onCategoriaSeleccionada,
                    onEmpezar = onEmpezar
                )
                Fase.JUGANDO -> PreguntaContent(uiState = uiState, onOpcionElegida = onOpcionElegida)
                Fase.RESULTADOS -> ResultadosContent(
                    uiState = uiState,
                    onJugarDeNuevo = onJugarDeNuevo,
                    onVolverAlInicio = onVolverAlInicio
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizContentInicioPreview() {
    AppQuizTheme {
        QuizContent(
            uiState = QuizUiState(),
            onEmpezar = {}, onOpcionElegida = {}, onJugarDeNuevo = {}, onVolverAlInicio = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizContentResultadosPreview() {
    val diez = List(10) { i -> Pregunta(i, "p$i", listOf("a", "b", "c", "d"), "a", Categoria.JAVA) }
    AppQuizTheme {
        QuizContent(
            uiState = QuizUiState(fase = Fase.RESULTADOS, preguntas = diez, correctas = 8, incorrectas = 2),
            onEmpezar = {}, onOpcionElegida = {}, onJugarDeNuevo = {}, onVolverAlInicio = {},
        )
    }
}