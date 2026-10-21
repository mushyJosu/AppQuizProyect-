package com.upchiapas.appquiz.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upchiapas.appquiz.QuizApplication
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.presentation.components.FormContent
import com.upchiapas.appquiz.presentation.components.InicioContent
import com.upchiapas.appquiz.presentation.components.PreguntaContent
import com.upchiapas.appquiz.presentation.components.ResultadosContent
import com.upchiapas.appquiz.presentation.viewmodels.QuizViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.upchiapas.appquiz.data.repository.FakePreguntaRepository
import com.upchiapas.appquiz.ui.theme.AppQuizTheme

@Composable
fun QuizPage(
    viewModel: QuizViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as QuizApplication).quizModule.factory
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuizContent(
        uiState = uiState,
        onCategoriaSeleccionada = viewModel::onCategoriaSeleccionada,
        onEmpezar = viewModel::onEmpezar,
        onAgregarPregunta = viewModel::onAgregarPregunta,
        onOpcionElegida = viewModel::onOpcionElegida,
        onJugarDeNuevo = viewModel::onJugarDeNuevo,
        onVolverAlInicio = viewModel::onVolverAlInicio,
        onEnunciadoCambia = viewModel::onEnunciadoCambia,
        onOpcionCambia = viewModel::onOpcionCambia,
        onCorrectaElegida = viewModel::onCorrectaElegida,
        onCategoriaFormularioElegida = viewModel::onCategoriaFormularioElegida,
        onGuardar = viewModel::onGuardar,
        onCancelarFormulario = viewModel::onCancelarFormulario,
        onAvisoMostrado = viewModel::onAvisoMostrado
    )
}

@Composable
fun QuizContent(
    uiState: QuizUiState,
    onCategoriaSeleccionada: (Categoria?) -> Unit,
    onEmpezar: () -> Unit,
    onAgregarPregunta: () -> Unit,
    onOpcionElegida: (String) -> Unit,
    onJugarDeNuevo: () -> Unit,
    onVolverAlInicio: () -> Unit,
    onEnunciadoCambia: (String) -> Unit,
    onOpcionCambia: (Int, String) -> Unit,
    onCorrectaElegida: (Int) -> Unit,
    onCategoriaFormularioElegida: (Categoria) -> Unit,
    onGuardar: () -> Unit,
    onCancelarFormulario: () -> Unit,
    onAvisoMostrado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val mensajeGuardado = stringResource(R.string.pregunta_guardada)

    LaunchedEffect(uiState.avisoGuardado) {
        if (uiState.avisoGuardado) {
            snackbarHostState.showSnackbar(mensajeGuardado)
            onAvisoMostrado()
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
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
                    onEmpezar = onEmpezar,
                    onAgregarPregunta = onAgregarPregunta
                )
                Fase.JUGANDO -> PreguntaContent(
                    uiState = uiState,
                    onOpcionElegida = onOpcionElegida,
                    onAbandonar = onVolverAlInicio
                )
                Fase.RESULTADOS -> ResultadosContent(
                    uiState = uiState,
                    onJugarDeNuevo = onJugarDeNuevo,
                    onVolverAlInicio = onVolverAlInicio
                )
                Fase.AGREGAR -> FormContent(
                    formulario = uiState.formulario,
                    onEnunciadoCambia = onEnunciadoCambia,
                    onOpcionCambia = onOpcionCambia,
                    onCorrectaElegida = onCorrectaElegida,
                    onCategoriaElegida = onCategoriaFormularioElegida,
                    onGuardar = onGuardar,
                    onCancelar = onCancelarFormulario
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizPageInteractivaPreview() {
    val viewModel = remember { QuizViewModel(FakePreguntaRepository()) }
    AppQuizTheme {
        QuizPage(viewModel = viewModel)
    }
}