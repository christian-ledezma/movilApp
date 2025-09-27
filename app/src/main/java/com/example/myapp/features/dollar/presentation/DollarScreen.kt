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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapp.features.dollar.domain.model.DollarModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private val BinanceYellow = Color(0xFFF0B90B)
private val BinanceBackground = Color(0xFF0B0E11)
private val BinanceCard = Color(0xFF181A20)
private val BinanceTextPrimary = Color(0xFFEAECEF)
private val BinanceTextSecondary = Color(0xFF848E9C)

@Composable
fun DollarScreen(viewModelDollar: DollarViewModel = koinViewModel()) {
    val state = viewModelDollar.uiState.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Text(
            text = "Tipo de cambio Dollar a Bolivianos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = BinanceYellow
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val stateValue = state.value) {
            is DollarViewModel.DollarUIState.Error -> Text(
                text = stateValue.message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )

            DollarViewModel.DollarUIState.Loading -> CircularProgressIndicator()
            is DollarViewModel.DollarUIState.Success -> {
                DollarRatesCard(data = stateValue.data)
            }
        }
    }
}

@Composable
fun DollarRatesCard(data: DollarModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = BinanceCard
        ),
        modifier = Modifier.fillMaxSize(0.9f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            data.tipoCambioOficialCompra?.let {
                RateText(label = "Dolar oficial Compra", value = it)
            }
            data.tipoCambioOficialVenta?.let {
                RateText(label = "Dolar oficial Venta", value = it)
            }
            data.tipoCambioParaleloCompra?.let {
                RateText(label = "Dolar paralelo compra", value = it)
            }
            data.tipoCambioPareleloVenta?.let {
                RateText(label = "Dolar paralelo venta", value = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timestamp
            data.lastUpdated?.let { timestamp ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(timestamp))

                Text(
                    text = "Última actualización:",
                    style = MaterialTheme.typography.labelMedium,
                    color = BinanceTextSecondary
                )
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = BinanceTextPrimary,
                )
            } ?: Text(
                text = "Sin timestamp disponible",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
        }
    }
}

@Composable
fun RateText(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        color = BinanceTextPrimary
    )
}
