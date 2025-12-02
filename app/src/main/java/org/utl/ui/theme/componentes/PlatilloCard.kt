package org.utl.ui.theme.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.model.Platillo

@Composable
fun PlatiloCard(platillo: Platillo, onAddClick : () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                //Texto para el nombre del platillo
                Text(
                    text = platillo.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                //Texto para el precio y si esta disponible
                Text(
                    "$${platillo.precio}",
                    style =  MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                //Texto para mostrar stock
                Text(
                    text = "Stock: $${platillo.stock}",
                    style =  MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = onAddClick,
                enabled = platillo.disponible && platillo.stock > 0
            ) {
                Text(text = "+")
            } //quedó.
        }
    }
}