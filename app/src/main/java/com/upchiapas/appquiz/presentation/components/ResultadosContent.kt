package com.upchiapas.appquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.presentation.screens.MensajeFinal
import com.upchiapas.appquiz.presentation.screens.QuizUiState

@Composable
fun ResultadosContent(
    uiState: QuizUiState,
    onJugarDeNuevo: () -> Unit,
    onVolverAlInicio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mensaje = when (uiState.mensajeFinal) {
        MensajeFinal.EXCELENTE -> R.string.mensaje_excelente
        MensajeFinal.REGULAR -> R.string.mensaje_regular
        MensajeFinal.REPASAR -> R.string.mensaje_repasar
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(R.string.resultados), style = MaterialTheme.typography.headlineMedium)
        Text(
            text = stringResource(R.string.porcentaje, uiState.porcentaje),
            style = MaterialTheme.typography.displayMedium
        )
        Text(text = stringResource(mensaje), style = MaterialTheme.typography.titleMedium)
        Text(
            text = stringResource(R.string.aciertos_errores, uiState.correctas, uiState.incorrectas),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = onJugarDeNuevo, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.jugar_de_nuevo))
        }
        OutlinedButton(onClick = onVolverAlInicio, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.volver_al_inicio))
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ResultadoContentPreview(){
    ResultadoContentPreview(

    )
}
