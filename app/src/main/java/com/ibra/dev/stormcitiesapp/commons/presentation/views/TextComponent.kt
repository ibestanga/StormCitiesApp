package com.ibra.dev.stormcitiesapp.commons.presentation.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun StandardText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onSurface
    )
}