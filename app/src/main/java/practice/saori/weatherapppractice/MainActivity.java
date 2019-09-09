package practice.saori.weatherapppractice;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import apiService.ApiService;
import data.AsyncResponse;
import data.Forecast;
import data.ForecastData;
import data.ForecastFragmentAdapter;
import data.List;
import data.WeatherData;
import model.ForecastFragment;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ForecastFragmentAdapter adapter;
    private TextView currentLocationTextView;
    private TextView currentTempTextView;
    private TextView currentDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get instance of textView
        currentLocationTextView = findViewById(R.id.locationTextView);
        currentTempTextView = findViewById(R.id.currentTempTextView);
        currentDateTextView = findViewById(R.id.dateTextView);

        adapter = new ForecastFragmentAdapter(getSupportFragmentManager(), getFragments());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        new Forecast().getCurrentWeatherData(new AsyncResponse<WeatherData>() {
            @Override
            public void processFinished(WeatherData weatherData) {
                Log.d("getCurrentWeatherData processFinished", weatherData.getName());
                currentTempTextView.setText("Temp:" + weatherData.getMain().getTemp().toString() + "℃");
                currentLocationTextView.setText(weatherData.getName());
            }
        });
        Date currentDate = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        currentDateTextView.setText(df.format(currentDate));
    }

    private java.util.List<ForecastFragment> getFragments() {
        java.util.List<ForecastFragment> fragments = new ArrayList<>();
        new Forecast().getForecastData(new AsyncResponse<java.util.List<List>>() {
            @Override
            public void processFinished(java.util.List<List> lists) {
                for(List list : lists) {
                    fragments.add(ForecastFragment.getInstance(list));
                }
                adapter.notifyDataSetChanged();
            }
        });
        return fragments;
    }
}
