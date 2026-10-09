package com.weatherclimate.assistant.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherclimate.assistant.data.network.ClimateSnapshot
import com.weatherclimate.assistant.data.network.OpenMeteoClient
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var placeQuery by remember { mutableStateOf("Tehran") }
    var snapshot by remember { mutableStateOf<ClimateSnapshot?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun reload() {
        scope.launch {
            loading = true
            error = null
            try {
                snapshot = OpenMeteoClient.load(placeQuery)
            } catch (exception: Exception) {
                error = exception.message ?: "Could not load weather"
            } finally {
                loading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        reload()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Climate Assistant",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Daily weather and air quality from Open-Meteo",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = placeQuery,
            onValueChange = { placeQuery = it },
            label = { Text("Place") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))

        CurrentWeatherCard(snapshot)
        Spacer(modifier = Modifier.height(16.dp))
        ForecastCard(snapshot)
        Spacer(modifier = Modifier.height(16.dp))
        AirQualityCard(snapshot)
        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Button(
            onClick = { reload() },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.height(20.dp).width(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh weather data")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Refresh")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        DataSourcesCard()
    }
}

@Composable
fun CurrentWeatherCard(snapshot: ClimateSnapshot?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Cloud,
                    contentDescription = "Current weather",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Current Weather", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(snapshot?.place ?: "Waiting for a place", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text("Temperature: ${snapshot.temperature()}", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
            Text("Humidity: ${snapshot?.humidityPercent?.let { "$it%" } ?: "--"}", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("Wind speed: ${snapshot.wind()}", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("Pressure: ${snapshot.pressure()}", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ForecastCard(snapshot: ClimateSnapshot?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                "5-Day Forecast",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            val days = snapshot?.days.orEmpty()
            if (days.isEmpty()) {
                Text("Waiting for data...", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            } else {
                days.forEach { day ->
                    Text(
                        "${day.date}: ${formatOne(day.minimumC)} to ${formatOne(day.maximumC)} °C",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AirQualityCard(snapshot: ClimateSnapshot?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                "Air Quality",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text("PM2.5: ${snapshot.amount(snapshot?.pm25)} µg/m³", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("PM10: ${snapshot.amount(snapshot?.pm10)} µg/m³", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("US AQI: ${snapshot?.usAqi?.toString() ?: "--"}", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun DataSourcesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "Data sources",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Data source", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Open-Meteo forecast and air-quality APIs. No API key is required.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun ClimateSnapshot?.temperature(): String =
    this?.let { "${formatOne(temperatureC)} °C" } ?: "-- °C"

private fun ClimateSnapshot?.wind(): String =
    this?.let { "${formatOne(windSpeedMs)} m/s" } ?: "-- m/s"

private fun ClimateSnapshot?.pressure(): String =
    this?.let { "${pressureHpa.roundToInt()} hPa" } ?: "-- hPa"

private fun ClimateSnapshot?.amount(value: Double?): String =
    value?.let { formatOne(it) } ?: "--"

private fun formatOne(value: Double): String = String.format(Locale.US, "%.1f", value)
