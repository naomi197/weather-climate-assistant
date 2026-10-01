package com.weatherclimate.assistant.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse

    @GET("air_pollution")
    suspend fun getAirPollution(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): AirPollutionResponse
}

// Data classes for API responses
data class WeatherResponse(
    val coord: Coordinates,
    val weather: List<Weather>,
    val main: MainWeatherData,
    val wind: Wind,
    val clouds: Clouds,
    val visibility: Int,
    val dt: Long,
    val name: String,
    val sys: SystemData
)

data class Coordinates(val lon: Double, val lat: Double)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainWeatherData(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null
)

data class Clouds(val all: Int)

data class SystemData(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    val main: MainWeatherData,
    val weather: List<Weather>,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain? = null
)

data class Rain(val `3h`: Double? = null)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates
)

data class AirPollutionResponse(
    val list: List<AirPollutionData>
)

data class AirPollutionData(
    val dt: Long,
    val main: AirQualityIndex,
    val components: AirComponents
)

data class AirQualityIndex(
    val aqi: Int // 1: Good, 2: Fair, 3: Moderate, 4: Poor, 5: Very Poor
)

data class AirComponents(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val nh3: Double
)
