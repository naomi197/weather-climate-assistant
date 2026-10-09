package com.weatherclimate.assistant.data.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

data class DayOutlook(
    val date: String,
    val minimumC: Double,
    val maximumC: Double
)

data class ClimateSnapshot(
    val place: String,
    val temperatureC: Double,
    val humidityPercent: Int,
    val windSpeedMs: Double,
    val pressureHpa: Double,
    val days: List<DayOutlook>,
    val pm25: Double,
    val pm10: Double,
    val usAqi: Int
)

object OpenMeteoClient {
    private val http = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()

    suspend fun load(placeQuery: String): ClimateSnapshot = withContext(Dispatchers.IO) {
        val query = placeQuery.trim()
        if (query.isEmpty()) error("Enter a place name")
        val geoUrl =
            "https://geocoding-api.open-meteo.com/v1/search?name=${URLEncoder.encode(query, "UTF-8")}&count=1&language=en&format=json"
        val geo = gson.fromJson(get(geoUrl), GeoResponse::class.java)
        val hit = geo.results?.firstOrNull() ?: error("No place found for \"$query\"")
        val latitude = hit.latitude ?: error("Place is missing a latitude")
        val longitude = hit.longitude ?: error("Place is missing a longitude")
        val place = listOfNotNull(hit.name, hit.country).joinToString(", ").ifBlank { query }
        loadCoordinates(latitude, longitude, place)
    }

    suspend fun loadCoordinates(latitude: Double, longitude: Double, place: String): ClimateSnapshot =
        withContext(Dispatchers.IO) {
            val forecastUrl =
                "https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude" +
                    "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,surface_pressure" +
                    "&daily=temperature_2m_max,temperature_2m_min&forecast_days=5&timezone=auto&wind_speed_unit=ms"
            val forecast = gson.fromJson(get(forecastUrl), ForecastBody::class.java)
            val airUrl =
                "https://air-quality-api.open-meteo.com/v1/air-quality?latitude=$latitude&longitude=$longitude" +
                    "&current=pm2_5,pm10,us_aqi"
            val air = gson.fromJson(get(airUrl), AirBody::class.java)
            val current = forecast.current ?: error("Forecast did not include current conditions")
            val daily = forecast.daily ?: error("Forecast did not include daily values")
            val times = daily.time.orEmpty()
            val mins = daily.temperatureMin.orEmpty()
            val maxs = daily.temperatureMax.orEmpty()
            val days = times.mapIndexed { index, date ->
                DayOutlook(
                    date = date,
                    minimumC = mins.getOrElse(index) { 0.0 },
                    maximumC = maxs.getOrElse(index) { 0.0 }
                )
            }
            val airNow = air.current ?: error("Air quality did not include current values")
            ClimateSnapshot(
                place = place,
                temperatureC = current.temperature ?: error("Temperature is missing"),
                humidityPercent = (current.humidity ?: error("Humidity is missing")).toInt(),
                windSpeedMs = current.wind ?: error("Wind speed is missing"),
                pressureHpa = current.pressure ?: error("Pressure is missing"),
                days = days,
                pm25 = airNow.pm25 ?: error("PM2.5 is missing"),
                pm10 = airNow.pm10 ?: error("PM10 is missing"),
                usAqi = (airNow.aqi ?: error("Air quality index is missing")).toInt()
            )
        }

    private fun get(url: String): String {
        val response = http.newCall(Request.Builder().url(url).build()).execute()
        response.use {
            val body = it.body?.string().orEmpty()
            if (!it.isSuccessful) error("Request failed (${it.code})")
            return body
        }
    }
}

private data class GeoResponse(val results: List<GeoHit>?)

private data class GeoHit(
    val name: String?,
    val country: String?,
    val latitude: Double?,
    val longitude: Double?
)

private data class ForecastBody(
    val current: CurrentBlock?,
    val daily: DailyBlock?
)

private data class CurrentBlock(
    @SerializedName("temperature_2m") val temperature: Double?,
    @SerializedName("relative_humidity_2m") val humidity: Double?,
    @SerializedName("wind_speed_10m") val wind: Double?,
    @SerializedName("surface_pressure") val pressure: Double?
)

private data class DailyBlock(
    val time: List<String>?,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>?,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>?
)

private data class AirBody(val current: AirCurrent?)

private data class AirCurrent(
    @SerializedName("pm2_5") val pm25: Double?,
    val pm10: Double?,
    @SerializedName("us_aqi") val aqi: Double?
)
