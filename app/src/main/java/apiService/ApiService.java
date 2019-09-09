package apiService;

import data.ForecastData;
import data.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
   @GET("forecast")
   Call<ForecastData> getForecastData(@Query("q") String city, @Query("mode") String mode,
                                      @Query("units") String units, @Query("APPID") String appId);

   @GET("weather")
   Call<WeatherData> getWeatherData(@Query("q") String city, @Query("mode") String mode,
                                    @Query("units") String units, @Query("APPID") String appId);
}
