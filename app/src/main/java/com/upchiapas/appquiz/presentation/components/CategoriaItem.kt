package com.upchiapas.appquiz.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.upchiapas.appquiz.R
import com.upchiapas.appquiz.data.model.Categoria
import com.upchiapas.appquiz.ui.theme.CategoriaCpp
import com.upchiapas.appquiz.ui.theme.CategoriaJava
import com.upchiapas.appquiz.ui.theme.CategoriaKotlin
import com.upchiapas.appquiz.ui.theme.CategoriaSql

@Composable
fun CategoriaItem(
    nombre: String,
    colorPunto: Color,
    seleccionada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionada) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.size(12.dp).background(colorPunto, CircleShape))
            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            if (seleccionada) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        }
    }
}

@StringRes
fun Categoria.nombreRes(): Int = when (this) {
    Categoria.JAVA -> R.string.categoria_java
    Categoria.SQL -> R.string.categoria_sql
    Categoria.CPP -> R.string.categoria_cpp
    Categoria.KOTLIN -> R.string.categoria_kotlin
}

fun Categoria.color(): Color = when (this) {
    Categoria.JAVA -> CategoriaJava
    Categoria.SQL -> CategoriaSql
    Categoria.CPP -> CategoriaCpp
    Categoria.KOTLIN -> CategoriaKotlin
}