package com.classy.class_2021a_ands_3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationHelper {

    public interface CallBack_CountryCode {
        void countryCodeReady(String countryCode);
    }

    private Context context;
    private CallBack_CountryCode callBack_countryCode;

    public MyLocationHelper(Context context) {
        this.context = context;
    }


    public void getCountryCode(CallBack_CountryCode callBack_countryCode) {
        this.callBack_countryCode = callBack_countryCode;
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.1f, mLocationListener);
        mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, Looper.myLooper());
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.d("pttt", "Location: " + location.getLatitude() + "," + location.getLongitude());


            String countryCode = getCountryCodeFromLocation(location.getLatitude(), location.getLongitude());
            if (callBack_countryCode != null) {
                callBack_countryCode.countryCodeReady(countryCode);
            }
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private String getCountryCodeFromLocation(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String countryCode = addresses.get(0).getCountryCode();
            return countryCode;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void stopListener() {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.removeUpdates(mLocationListener);
    }
}
