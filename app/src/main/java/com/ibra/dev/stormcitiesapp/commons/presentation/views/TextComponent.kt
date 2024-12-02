package com.ibra.dev.stormcitiesapp.commons.presentation.views

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun StandardText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Text(
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onSurface
    )
}