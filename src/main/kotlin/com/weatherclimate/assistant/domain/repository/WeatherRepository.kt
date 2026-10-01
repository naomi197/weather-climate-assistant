package com.weatherclimate.assistant.domain.repository

import com.weatherclimate.assistant.data.network.AirPollutionResponse
import com.weatherclimate.assistant.data.network.ForecastResponse
import com.weatherclimate.assistant.data.network.WeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherResponse>
    suspend fun getForecast(latitude: Double, longitude: Double): Result<ForecastResponse>
    suspend fun getAirPollution(latitude: Double, longitude: Double): Result<AirPollutionResponse>
}
