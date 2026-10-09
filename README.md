# Climate Assistant

## Android Weather & Climate Application

Climate Assistant is an English-language Android application for checking daily weather, air-quality, and climate-related figures with transparent data-source attribution.

Developer: [alirezafazeli@live.com](mailto:alirezafazeli@live.com)

### Features

- Current temperature, humidity, wind speed, and atmospheric pressure
- Five-day weather forecast
- Air-quality data including PM2.5, PM10, and AQI
- Location-aware weather retrieval (planned in the next implementation step)
- Official data-source attribution
- English user interface
- Jetpack Compose and Material 3 design

### Data Sources

1. **OpenWeather API** — current weather, forecasts, and air pollution data: https://openweathermap.org/
2. **NOAA** — official US weather and climate data: https://www.noaa.gov/
3. **Copernicus Climate Data Store** — global climate datasets: https://cds.climate.copernicus.eu/

> The current network implementation uses OpenWeather endpoints. NOAA and Copernicus are listed as planned source integrations and should only be displayed as active sources after their APIs are connected.

### Technology

- Kotlin
- Jetpack Compose
- Material Design 3
- Retrofit and OkHttp
- Kotlin Coroutines
- Room and DataStore dependencies

### Setup

1. Clone the repository:

```bash
git clone https://github.com/naomi197/weather-climate-assistant.git
cd weather-climate-assistant
```

2. Create an OpenWeather API key at https://openweathermap.org/api.
3. Configure the key securely through a local Gradle property or environment variable. Do not commit API keys to source control.
4. Open the project in Android Studio and run it on an Android 8.0 (API 26) or newer device.

### Project Structure

```text
src/main/kotlin/com/weatherclimate/assistant/
├── data/network/       # API interfaces and response models
├── domain/repository/  # Repository contracts and implementations
└── ui/                 # Compose activity, screens, and theme
```

## Related work

- [ClimaScope](https://github.com/naomi197/climascope) — live climate observatory for Android and the browser
- [TreeGrow](https://github.com/naomi197/treegrow-android) — Android app for virtual tree planting
- [CleanFlow Ghana](https://github.com/naomi197/cleanflow-ghana) — water-pollution reporting and priority ranking

## License

MIT License.
