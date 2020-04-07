package com.example.covid_19tracker.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.covid_19tracker.R;

public class CovidCountryDetail extends AppCompatActivity {

    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths,
             tvDetailTodayDeaths, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);

        //call view
        tvDetailCountryName =  findViewById(R.id.tavDetailCountryName);
        tvDetailTotalCases =   findViewById(R.id.tavDetailTotalCases);
        tvDetailTodayCases =   findViewById(R.id.tavDetailTodayCases);
        tvDetailTotalDeaths =  findViewById(R.id.tavDetailTotalDeath);
        tvDetailTodayDeaths =  findViewById(R.id.tavDetailTodayDeaths);
        tvDetailTotalRecovered = findViewById(R.id.tavDetailTotalRecovered);
        tvDetailTotalActive =  findViewById(R.id.tavDetailTotalActive);
        tvDetailTotalCritical =  findViewById(R.id.tavDetailTotalCritical);


        //call covid country
        CovidCountry covidCountry = getIntent().getParcelableExtra("EXTRA COVID");

        //set TextView
        tvDetailCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTotalCases.setText(covidCountry.getmCases());
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(covidCountry.getmDeaths());
        tvDetailTodayDeaths.setText(covidCountry.getmTotalDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActive());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());



    }
}
