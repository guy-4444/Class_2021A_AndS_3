package com.classy.class_2021a_ands_3;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataManager {

    private String currencyApi = "";
    private HashMap<String, Country> countries = new HashMap<>();
    private Country chosenCountry;


    public DataManager(Context context, String currencyApi) {
        this.currencyApi = currencyApi;
        buildData(context);
    }

    public ArrayList<Country> getSelectedCountries(String[] SELECTED_CODES) {
        ArrayList<Country> others = new ArrayList<>();
        for (String code : SELECTED_CODES) {
            others.add(countries.get(code));
        }

        return others;
    }

    public String getChosenCountryName() {
        String countryName = chosenCountry.getCountryName();
        countryName = countryName.toLowerCase();
        countryName = countryName.replaceAll(" ", "-");

        return countryName;
    }

    public void updateChosenCountry(String countryCode) {
        chosenCountry = countries.get(countryCode);
    }

    private void buildData(Context context) {
        ArrayList<Country> countriesArr = loadJsonData(context);
        for (Country c : countriesArr) {
            countries.put(c.getCountryCode(), c);
        }

        HashMap<String, CurrencyManager.MyCurrency> map = new HashMap<>();
        for (CurrencyManager.MyCurrency currency : CurrencyManager.CURRENCIES) {
            map.put(currency.code, currency);
        }

        for (Country c : countriesArr) {
            try {
                c.setCurrencyName(map.get(c.getCurrencyCode()).name);
                c.setCurrencySymbol(map.get(c.getCurrencyCode()).symbol);
            } catch (Exception ex) { }

        }

        downloadCurrencyData();
    }

    public HashMap<String, Country> getCountries() {
        return countries;
    }

    public Country getChosenCountry() {
        return chosenCountry;
    }


    /////////

    private ArrayList<Country> loadJsonData(Context context) {
        ArrayList<Country> _countries;
        String json = null;
        try {
            InputStream is = context.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            TypeToken typeToken = new TypeToken<ArrayList<Country>>() {
            };
            _countries = new Gson().fromJson(json, typeToken.getType());
        } catch (IOException ex) {
            ex.printStackTrace();
            _countries = new ArrayList<>();
        }

        if (_countries == null) {
            _countries = new ArrayList<>();
        }

        return _countries;
    }

    private void downloadCurrencyData() {
        MyServerCall.downloadData(currencyApi, new MyServerCall.CallBack_Data() {
            @Override
            public void dataReady(String data) {
                currencyDataReady(data);
            }
        });
    }

    private void currencyDataReady(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject map = obj.getJSONObject("rates");
            Iterator<String> keys = map.keys();

            HashMap<String, Double> currencies = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                double val = map.getDouble(key);
                Log.d("pttt", key + ": " + val);

                currencies.put(key, val);
            }

            for (Map.Entry<String, Country> entry : countries.entrySet()) {
                Country c = entry.getValue();
                try {
                    c.setUsdRate(currencies.get(c.getCurrencyCode()));
                } catch (Exception ex) {
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
