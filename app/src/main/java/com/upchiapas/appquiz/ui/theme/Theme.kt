package com.upchiapas.appquiz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EsquemaOscuro = darkColorScheme(
    //colores predefinidos para la app sin usar por default matherial design
    primary = AzulPrimario,
    onPrimary = TextoSobreAzul,
    primaryContainer = AzulContenedor,
    onPrimaryContainer = TextoClaro,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = FondoOscuro,
    onSurface = TextoClaro,
    surfaceVariant = SuperficieOscura,
    onSurfaceVariant = TextoSecundario,
    outline = BordeOscuro,
    secondaryContainer = AzulPrimario, //color para los chips seleccionables de la categoria en FormContent
    onSecondaryContainer = TextoSobreAzul,
)

@Composable
fun AppQuizTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaOscuro,
        typography = Typography,
        content = content
    )
}