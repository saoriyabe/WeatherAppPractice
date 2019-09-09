package data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import model.ForecastFragment;

public class ForecastFragmentAdapter extends FragmentPagerAdapter {
    private java.util.List<ForecastFragment> forecastFragments;

    public ForecastFragmentAdapter(FragmentManager fm, List<ForecastFragment> fragments) {
        super(fm);
        forecastFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return forecastFragments.get(i);
    }

    @Override
    public int getCount() {
        return forecastFragments.size();
    }
}
