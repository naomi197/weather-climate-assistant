# Climate Assistant

## Android Weather & Climate Application

Climate Assistant is an English-language Android application for checking daily weather, air-quality, and climate-related figures with transparent data-source attribution.

Developer: Alireza Sani · [alirezafazeli@live.com](mailto:alirezafazeli@live.com)

### Features

- Current temperature, humidity, wind speed, and atmospheric pressure
- Five-day weather forecast
- Air-quality data including PM2.5, PM10, and AQI
- Place search, then a refresh of the latest observation
- English user interface
- Jetpack Compose and Material 3 design

### Data source

Open-Meteo provides the current observation, the five-day forecast, and PM2.5, PM10, and US AQI. No API key is required.

https://open-meteo.com/

### Technology

- Kotlin
- Jetpack Compose
- Material Design 3
- Retrofit and OkHttp
- Kotlin Coroutines

### Setup

1. Clone the repository:

```bash
git clone https://github.com/naomi197/weather-climate-assistant.git
cd weather-climate-assistant
```

2. Build the debug package:

```bash
./gradlew assembleDebug
```

3. Open the project in Android Studio and run it on an Android 8.0 (API 26) or newer device. Refresh loads live Open-Meteo data for the place in the text field. Tehran is the starting place.

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
