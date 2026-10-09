package com.weatherclimate.assistant.domain.repository

import com.weatherclimate.assistant.data.network.AirPollutionResponse
import com.weatherclimate.assistant.data.network.AirComponents
import com.weatherclimate.assistant.data.network.AirPollutionData
import com.weatherclimate.assistant.data.network.AirQualityIndex
import com.weatherclimate.assistant.data.network.City
import com.weatherclimate.assistant.data.network.Clouds
import com.weatherclimate.assistant.data.network.Coordinates
import com.weatherclimate.assistant.data.network.ForecastItem
import com.weatherclimate.assistant.data.network.ForecastResponse
import com.weatherclimate.assistant.data.network.MainWeatherData
import com.weatherclimate.assistant.data.network.OpenMeteoClient
import com.weatherclimate.assistant.data.network.SystemData
import com.weatherclimate.assistant.data.network.Weather
import com.weatherclimate.assistant.data.network.WeatherResponse
import com.weatherclimate.assistant.data.network.Wind

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): Result<WeatherResponse> =
        runCatching {
            val snapshot = OpenMeteoClient.loadCoordinates(latitude, longitude, "Selected point")
            WeatherResponse(
                coord = Coordinates(longitude, latitude),
                weather = listOf(Weather(0, "Observed", snapshot.place, "")),
                main = MainWeatherData(
                    temp = snapshot.temperatureC,
                    feels_like = snapshot.temperatureC,
                    temp_min = snapshot.days.firstOrNull()?.minimumC ?: snapshot.temperatureC,
                    temp_max = snapshot.days.firstOrNull()?.maximumC ?: snapshot.temperatureC,
                    pressure = snapshot.pressureHpa.toInt(),
                    humidity = snapshot.humidityPercent
                ),
                wind = Wind(snapshot.windSpeedMs, 0),
                clouds = Clouds(0),
                visibility = 0,
                dt = 0,
                name = snapshot.place,
                sys = SystemData("", 0, 0)
            )
        }

    override suspend fun getForecast(latitude: Double, longitude: Double): Result<ForecastResponse> =
        runCatching {
            val snapshot = OpenMeteoClient.loadCoordinates(latitude, longitude, "Selected point")
            ForecastResponse(
                list = snapshot.days.map { day ->
                    ForecastItem(
                        dt = 0,
                        main = MainWeatherData(day.maximumC, day.maximumC, day.minimumC, day.maximumC, 0, 0),
                        weather = listOf(Weather(0, day.date, day.date, "")),
                        wind = Wind(snapshot.windSpeedMs, 0),
                        visibility = 0,
                        pop = 0.0
                    )
                },
                city = City(0, snapshot.place, "", Coordinates(longitude, latitude))
            )
        }

    override suspend fun getAirPollution(latitude: Double, longitude: Double): Result<AirPollutionResponse> =
        runCatching {
            val snapshot = OpenMeteoClient.loadCoordinates(latitude, longitude, "Selected point")
            AirPollutionResponse(
                list = listOf(
                    AirPollutionData(
                        dt = 0,
                        main = AirQualityIndex(snapshot.usAqi),
                        components = AirComponents(
                            co = 0.0,
                            no = 0.0,
                            no2 = 0.0,
                            o3 = 0.0,
                            so2 = 0.0,
                            pm2_5 = snapshot.pm25,
                            pm10 = snapshot.pm10,
                            nh3 = 0.0
                        )
                    )
                )
            )
        }
}
