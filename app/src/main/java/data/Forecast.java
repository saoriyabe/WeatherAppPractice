package data;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import apiService.ApiService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Forecast {
    static final String baseUrl = "http://api.openweathermap.org/data/2.5/";
    static final String APP_ID = "451387e7f5ca35e4bf31d5f9a1007c91";
    static final String mode = "json";
    static final String units = "metric";

    private ApiService initialize() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original = chain.request();

                //header setting
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);
                return response;
            }
        });

        // setting for logs
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient.addInterceptor(logging);

        // create client
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService API = retrofit.create(ApiService.class);
        return API;
    }

    public void getForecastData(AsyncResponse<java.util.List<List>> callback) {
        initialize();
        ApiService API = initialize();
        API.getForecastData("Tokyo", mode, units, APP_ID).enqueue(new Callback<ForecastData>() {
            @Override
            public void onResponse(Call<ForecastData> call, retrofit2.Response<ForecastData> response) {
                if(response.isSuccessful()) {
                    ForecastData data = response.body();
                    Log.d("onResponse", data.getCity().getName());
                    callback.processFinished(data.getList());
                }
            }

            @Override
            public void onFailure(Call<ForecastData> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void getCurrentWeatherData(AsyncResponse<WeatherData> callback) {
        ApiService API = initialize();
        API.getWeatherData("Tokyo",mode, units, APP_ID).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, retrofit2.Response<WeatherData> response) {
                if(response.isSuccessful()) {
                    WeatherData data = response.body();
                    Log.d("onResponse", data.getName());
                    callback.processFinished(data);
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
