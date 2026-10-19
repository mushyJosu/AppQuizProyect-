package com.upchiapas.appquiz.presentation.components

import android.view.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import com.upchiapas.appquiz.presentation.screens.QuizPage
import com.upchiapas.appquiz.ui.theme.AppQuizTheme
import com.upchiapas.appquiz.ui.theme.CategoriaTodas

@Composable
fun InicioContent(
    CategoriaSeleccionada: Categoria?,
    onCategoriaSeleccionada: (Categoria?) -> Unit,
    onEmpezar: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.titulo_inicio),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.elige_categoria),
            style = MaterialTheme.typography.bodyMedium
        )
        CategoriaItem(
            nombre = stringResource(R.string.categoria_todas),
            colorPunto = CategoriaTodas,
            seleccionada = CategoriaSeleccionada == null,
            onClick = { onCategoriaSeleccionada(null)}
        )
        Categoria.entries.forEach { categoria ->
            CategoriaItem(
                nombre = stringResource(categoria.nombreRes()),
                colorPunto = categoria.color(),
                seleccionada = CategoriaSeleccionada == categoria,
                onClick = {onCategoriaSeleccionada(categoria)}
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onEmpezar, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.empezar))
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun InicioContentPreview(){
    AppQuizTheme {
        Surface{
            InicioContent(
               CategoriaSeleccionada = Categoria.SQL,
                onCategoriaSeleccionada = {},
                onEmpezar = {}
            )
        }
    }
}