package com.weatherclimate.assistant.domain.repository

import com.weatherclimate.assistant.data.network.AirPollutionResponse
import com.weatherclimate.assistant.data.network.ForecastResponse
import com.weatherclimate.assistant.data.network.WeatherApi
import com.weatherclimate.assistant.data.network.WeatherResponse

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {
    private val apiKey = "your_openweather_api_key"

    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherResponse> =
        try {
            val response = weatherApi.getCurrentWeather(latitude, longitude, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getForecast(latitude: Double, longitude: Double): Result<ForecastResponse> =
        try {
            val response = weatherApi.getForecast(latitude, longitude, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getAirPollution(latitude: Double, longitude: Double): Result<AirPollutionResponse> =
        try {
            val response = weatherApi.getAirPollution(latitude, longitude, apiKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
}
