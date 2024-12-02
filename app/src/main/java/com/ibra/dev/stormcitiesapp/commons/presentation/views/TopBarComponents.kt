package com.ibra.dev.stormcitiesapp.commons.presentation.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    title: String,
    needBackNavigation: Boolean,
    actionIcon: ImageVector? = null,
    onBackPressClick:() -> Unit,
    actionClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            StandardText(text = title)
        },
        navigationIcon = {
            if (needBackNavigation) {
                IconButton(onClick = onBackPressClick) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "icon",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            actionIcon?.let {
                IconButton(onClick = actionClick) {
                    Icon(
                        imageVector = it,
                        contentDescription = "icon",
                        tint = Color.White
                    )
                }
            }
        }
    )
}