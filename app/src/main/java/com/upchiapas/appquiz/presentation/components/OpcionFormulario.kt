package com.upchiapas.appquiz.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.upchiapas.appquiz.R

@Composable
fun OpcionFormulario(
    letra: String,
    texto: String,
    esCorrecta: Boolean,
    onTextoCambia: (String) -> Unit,
    onMarcarCorrecta: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = esCorrecta, onClick = onMarcarCorrecta)
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoCambia,
            label = { Text(text = stringResource(R.string.form_opcion, letra)) },
            modifier = Modifier.weight(1f)
        )
    }
}