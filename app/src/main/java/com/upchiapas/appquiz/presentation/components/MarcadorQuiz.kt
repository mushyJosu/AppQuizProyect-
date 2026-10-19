package com.upchiapas.appquiz.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.ui.theme.Incorrecto

@Composable
fun MarcadorQuiz(
    total: Int,
    correctas: Int,
    incorrectas: Int,
    restantes: Int,
    errorResaltado: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DatoMarcador(
            etiqueta = stringResource(R.string.marcador_total),
            valor = total,
            resaltado = false,
            modifier = Modifier.weight(1f)
        )
        DatoMarcador(
            etiqueta = stringResource(R.string.marcador_correctas),
            valor = correctas,
            resaltado = false,
            modifier = Modifier.weight(1f)
        )
        DatoMarcador(
            etiqueta = stringResource(R.string.marcador_incorrectas),
            valor = incorrectas,
            resaltado = errorResaltado,
            modifier = Modifier.weight(1f)
        )
        DatoMarcador(
            etiqueta = stringResource(R.string.marcador_restantes),
            valor = restantes,
            resaltado = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DatoMarcador(
    etiqueta: String,
    valor: Int,
    resaltado: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (resaltado) Incorrecto else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = etiqueta, style = MaterialTheme.typography.labelSmall)
            Text(text = valor.toString(), style = MaterialTheme.typography.titleLarge)
        }
    }
}