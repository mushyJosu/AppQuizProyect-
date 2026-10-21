package com.upchiapas.appquiz.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.presentation.screens.FormularioState
import com.upchiapas.appquiz.ui.theme.AppQuizTheme

private val letrasFormulario = listOf("A", "B", "C", "D")

@Composable
fun FormContent(
    formulario: FormularioState,
    onEnunciadoCambia: (String) -> Unit,
    onOpcionCambia: (Int, String) -> Unit,
    onCorrectaElegida: (Int) -> Unit,
    onCategoriaElegida: (Categoria) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.agregar_pregunta),
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = formulario.enunciado,
            onValueChange = onEnunciadoCambia,
            label = { Text(text = stringResource(R.string.form_pregunta)) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.form_marca_correcta),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(R.string.form_ayuda_correcta),
            style = MaterialTheme.typography.bodySmall
        )

        formulario.opciones.forEachIndexed { indice, texto ->
            OpcionFormulario(
                letra = letrasFormulario[indice],
                texto = texto,
                esCorrecta = formulario.indiceCorrecta == indice,
                onTextoCambia = { nuevo -> onOpcionCambia(indice, nuevo) },
                onMarcarCorrecta = { onCorrectaElegida(indice) }
            )
        }

        Text(
            text = stringResource(R.string.form_categoria),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Categoria.entries.forEach { categoria ->
                FilterChip(
                    selected = formulario.categoria == categoria,
                    onClick = { onCategoriaElegida(categoria) },
                    label = { Text(text = stringResource(categoria.nombreRes())) }
                )
            }
        }

        if (!formulario.puedeGuardar) {
            Text(
                text = stringResource(R.string.form_ayuda_guardar),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Button(
            onClick = onGuardar,
            enabled = formulario.puedeGuardar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.guardar))
        }
        OutlinedButton(onClick = onCancelar, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.cancelar_descartar))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormContentVacioPreview() {
    AppQuizTheme {
        Surface {
            FormContent(
                formulario = FormularioState(),
                onEnunciadoCambia = {}, onOpcionCambia = { _, _ -> },
                onCorrectaElegida = {}, onCategoriaElegida = {},
                onGuardar = {}, onCancelar = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormContentLlenoPreview() {
    AppQuizTheme {
        Surface {
            FormContent(
                formulario = FormularioState(
                    enunciado = "¿Qué palabra clave se usa para heredar de una clase en Java?",
                    opciones = listOf("extends", "implements", "inherits", "super"),
                    indiceCorrecta = 0,
                    categoria = Categoria.JAVA
                ),
                onEnunciadoCambia = {}, onOpcionCambia = { _, _ -> },
                onCorrectaElegida = {}, onCategoriaElegida = {},
                onGuardar = {}, onCancelar = {}
            )
        }
    }
}