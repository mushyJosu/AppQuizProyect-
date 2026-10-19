package com.upchiapas.appquiz.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.ui.theme.AppQuizTheme
import com.upchiapas.appquiz.ui.theme.Correcto
import com.upchiapas.appquiz.ui.theme.Incorrecto
import com.upchiapas.appquiz.ui.theme.OpcionAzul

enum class EstadoOpcion { NORMAL, CORRECTA, INCORRECTA, APAGADA }

@Composable
fun OpcionRespuesta(
    letra: String,
    texto: String,
    colorLetra: Color,
    estado: EstadoOpcion,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorFondo = when (estado) {
        EstadoOpcion.CORRECTA -> Correcto
        EstadoOpcion.INCORRECTA -> Incorrecto
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (estado == EstadoOpcion.APAGADA) 0.4f else 1f)
            .clickable(enabled = estado == EstadoOpcion.NORMAL, onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorFondo,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(colorLetra, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = letra, style = MaterialTheme.typography.labelLarge, color = Color.Black)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = texto, style = MaterialTheme.typography.bodyLarge)
                if (estado == EstadoOpcion.INCORRECTA) {
                    Text(
                        text = stringResource(R.string.tu_seleccion),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            when (estado) {
                EstadoOpcion.CORRECTA -> Icon(Icons.Default.Check, contentDescription = null)
                EstadoOpcion.INCORRECTA -> Icon(Icons.Default.Close, contentDescription = null)
                else -> Unit
            }
        }
    }
}

// La Preview debe ir FUERA de la función OpcionRespuesta
@Preview(showBackground = true)
@Composable
private fun OpcionRespuestaEstadosPreview() {
    AppQuizTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EstadoOpcion.entries.forEach { estado ->
                    OpcionRespuesta("A", "Texto de la opción", OpcionAzul, estado, onClick = {})
                }
            }
        }
    }
}