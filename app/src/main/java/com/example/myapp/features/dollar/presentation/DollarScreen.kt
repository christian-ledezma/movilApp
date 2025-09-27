package com.example.myapp.features.dollar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.features.dollar.domain.model.DollarModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state = viewModelDollar.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (val stateValue = state.value) {
            is DollarViewModel.DollarUIState.Error -> {
                Text(
                    text = stateValue.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }

            DollarViewModel.DollarUIState.Loading -> {
                CircularProgressIndicator()
            }

            is DollarViewModel.DollarUIState.Success -> {
                DollarRatesCard(data = stateValue.data)
            }

            DollarViewModel.DollarUIState.Empty -> {
                Text(
                    text = "Sin datos disponibles",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            DollarViewModel.DollarUIState.Refreshing -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Actualizando...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DollarRatesCard(data: DollarModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxSize(0.9f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            data.tipoCambioOficial?.let {
                RateText(label = "Dólar Oficial", value = it)
            }
            data.tipoCambioParalelo?.let {
                RateText(label = "Dólar Paralelo", value = it)
            }
            data.tipoCambioUSDT?.let {
                RateText(label = "USDT", value = it)
            }
            data.tipoCambioUSDC?.let {
                RateText(label = "USDC", value = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timestamp
            data.lastUpdated?.let { timestamp ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(timestamp))

                Text(
                    text = "Última actualización:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            } ?: Text(
                text = "Sin timestamp disponible",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun RateText(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )
}
