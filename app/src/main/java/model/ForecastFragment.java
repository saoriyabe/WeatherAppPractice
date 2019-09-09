package model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.List;
import practice.saori.weatherapppractice.R;

public class ForecastFragment extends Fragment {

    private TextView dateTextView;
    private TextView highTempTextView;
    private TextView lowTempTextView;
    private TextView humidityTextView;

    public ForecastFragment() {
        //default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        dateTextView = view.findViewById(R.id.dateTextView);
        highTempTextView = view.findViewById(R.id.highTempTextView);
        lowTempTextView = view.findViewById(R.id.lowTempTextView);
        humidityTextView = view.findViewById(R.id.humidityTextView);

        Bundle bundle = getArguments();
        List list = (List)bundle.getSerializable("listData");
        dateTextView.setText(list.getDtTxt());
        highTempTextView.setText(list.getMain().getTempMax().toString());
        lowTempTextView.setText(list.getMain().getTempMin().toString());
        humidityTextView.setText("Humidity : " + list.getMain().getHumidity().toString() + "%");
        return view;
    }

    public static final ForecastFragment getInstance(List list) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listData", list);
        fragment.setArguments(bundle);
        return fragment;
    }
}
