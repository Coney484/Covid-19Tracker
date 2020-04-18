package com.example.covid_19tracker.ui.country;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountryFragment extends Fragment {

    RecyclerView rvCovidCountry;
    ProgressBar progressBar;
//    TextView tvTotalCountry;
    CovidCountryAdapter covidCountryAdapter;



    private static final String TAG = CountryFragment.class.getSimpleName();
    List<CovidCountry> covidCountries;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country, container, false);

        //set has option menu as true because we have menu
        setHasOptionsMenu(true);

        //call view
        rvCovidCountry = root.findViewById(R.id.rvCovidCountry);
        progressBar = root.findViewById(R.id.progress_circular_country);
//        tvTotalCountry = root.findViewById(R.id.tvTotalCountries);
        rvCovidCountry.setLayoutManager(new LinearLayoutManager(getActivity()));

        //call list
        covidCountries = new ArrayList<>();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCovidCountry.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.line_divider)));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);

        //call Volley method

        getDataFromServet();
        return root;
    }

    private void showRecyclerView() {
        covidCountryAdapter = new CovidCountryAdapter(covidCountries,getActivity());
        rvCovidCountry.setAdapter(covidCountryAdapter);
        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCovidCountries(covidCountries.get(position));
            }
        });
    }

    private void showSelectedCovidCountries(CovidCountry covidCountry){
        Intent covidCovidCountriesDetails = new Intent(getActivity(), CovidCountryDetail.class);
        covidCovidCountriesDetails.putExtra("EXTRA COVID", covidCountry);
        startActivity(covidCovidCountriesDetails);

    }

    private void getDataFromServet() {
        String url = "https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            //Extract JSONObject to JSONObject
                            JSONObject countryInfo = data.getJSONObject("countryInfo");
                            covidCountries.add(new CovidCountry(data.getString("country"), data.getString("cases"),
                                    data.getString("todayCases"),
                                    data.getString("deaths"),
                                    data.getString("todayDeaths"),
                                    data.getString("recovered"),
                                    data.getString("active"),
                                    data.getString("critical"),
                                    countryInfo.getString("flag")
                                    ));
                        }
//                        tvTotalCountry.setText( "Countries");

                        //Action Bar Title
                        getActivity().setTitle("Countries");
                        showRecyclerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onResponse: " + error);
            }
        });
       Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (covidCountryAdapter!=null)
                {
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

