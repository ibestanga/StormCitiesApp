package com.ibra.dev.stormcitiesapp.commons.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_16dp
import com.ibra.dev.stormcitiesapp.commons.presentation.theme.padding_8dp
import com.ibra.dev.stormcitiesapp.home.domain.models.CityDto


@Composable
fun DetailDialog(
    city: CityDto,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(padding_16dp),
            elevation = CardDefaults.cardElevation(padding_8dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(padding_16dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = city.name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(padding_8dp))
                Text(
                    "País: ${city.country}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(padding_8dp))
                Text(
                    text = "Lat: ${city.latitude}, Long${city.longitude}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(padding_16dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}