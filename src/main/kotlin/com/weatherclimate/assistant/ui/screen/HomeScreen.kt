package com.weatherclimate.assistant.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()

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
            text = "Daily weather and climate information",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        CurrentWeatherCard()
        Spacer(modifier = Modifier.height(16.dp))
        ForecastCard()
        Spacer(modifier = Modifier.height(16.dp))
        AirQualityCard()
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: refresh data */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Refresh weather data")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Refresh")
        }

        Spacer(modifier = Modifier.height(16.dp))
        DataSourcesCard()
    }
}

@Composable
fun CurrentWeatherCard() {
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
            Text("Temperature: -- °C", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
            Text("Humidity: --%", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("Wind speed: -- m/s", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("Pressure: -- hPa", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ForecastCard() {
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
            repeat(5) { day ->
                Text(
                    "Day ${day + 1}: Waiting for data...",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun AirQualityCard() {
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
            Text("PM2.5: -- µg/m³", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("PM10: -- µg/m³", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            Text("Air Quality Index: --", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
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
                Text("Official Data Sources", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "• OpenWeather API\n• NOAA Weather Data\n• Copernicus Climate Data Store",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
