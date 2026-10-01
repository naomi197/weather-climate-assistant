# دستیار اقلیم شناس و هواشناسی

## Weather & Climate Assistant - Android Application

یک اپلیکیشن اندرویدی جامع برای دریافت اطلاعات روزانه هواشناسی و اقلیم شناسی از منابع رسمی.

### ✨ ویژگی‌ها

- 📍 **موقعیت‌یابی هوشمند**: دریافت خودکار موقعیت جغرافیایی
- 🌡️ **اطلاعات آب‌وهوایی**: درجه حرارت، رطوبت، سرعت باد و فشار هوا
- 📅 **پیش‌بینی 5 روزه**: اطلاعات هواشناسی برای روزهای آینده
- 💨 **شاخص کیفیت هوا**: PM2.5، PM10 و دیگر آلاینده‌های هوا
- 🔗 **منابع رسمی**: تمام اطلاعات از API‌های معتبر
- 🌐 **رابط کاربری پرتو**: طراحی مدرن و واکنش‌پذیر

### 📡 منابع اطلاعات

1. **OpenWeather API**
   - پیش‌بینی هوا و اطلاعات فعلی
   - شاخص کیفیت هوا
   - https://openweathermap.org/

2. **NOAA (National Oceanic and Atmospheric Administration)**
   - داده‌های آب‌وهوایی آمریکا
   - https://www.noaa.gov/

3. **Copernicus Climate Data Store**
   - داده‌های اقلیمی جهانی
   - https://cds.climate.copernicus.eu/

### 🛠️ تکنولوژی‌ها

- **Kotlin**: زبان برنامه‌نویسی
- **Jetpack Compose**: UI toolkit مدرن
- **Retrofit**: HTTP client
- **Room**: پایگاه داده محلی
- **Coroutines**: برنامه‌نویسی غیرهمگام
- **Material Design 3**: طراحی UI

### 📋 نیازمندی‌ها

- Android SDK 26 یا بالاتر
- OpenWeather API Key
- اینترنت فعال
- دسترسی به موقعیت‌یابی (GPS)

### 🚀 شروع کار

1. **Clone Repository**
```bash
git clone https://github.com/naomi197/weather-climate-assistant.git
cd weather-climate-assistant
```

2. **تنظیم API Key**
   - ثبت‌نام در [OpenWeather](https://openweathermap.org/api)
   - API Key خود را در `WeatherRepositoryImpl.kt` وارد کنید

3. **Build & Run**
```bash
./gradlew build
./gradlew installDebug
```

### 📱 ساختار پروژه

```
weather-climate-assistant/
├── src/main/
│   ├── kotlin/
│   │   └── com/weatherclimate/assistant/
│   │       ├── data/network/       # API و مدل‌های شبکه
│   │       ├── domain/repository/  # منطق تجاری
│   │       └── ui/                 # رابط کاربری
│   └── AndroidManifest.xml
└── README.md
```

### 📝 لایسنس

MIT License - برای اطلاعات بیشتر `LICENSE` را ببینید.

### 👨‍💻 نویسندگان

- Naomi197

### 📞 تماس و پشتیبانی

برای سوالات و پیشنهادات:
- Issues: GitHub Issues
- Email: support@example.com

---

**نسخه**: 1.0.0  
**آخرین بروزرسانی**: 2026
