//package com.classy.class_2021a_ands_3;
//
//public class OldMainWithPermissions {
//}
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.drawable.Drawable;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Looper;
//import android.provider.Settings;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.classy.class_2021a_ands_3.R;
//import com.google.android.material.textfield.TextInputLayout;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//public class MainActivity extends AppCompatActivity {
//
//    /*
//    Views
//    Constants
//    Countries DB (Local json + Currency Class + Server API)
//
//
//
//     */
//// 96a95018cff24b0a828e1e0f90faa488
//// https://currencyfreaks.com/pricing.html
//private ImageView panel_IMG_flag;
//    private TextInputLayout panel_EDT_amount;
//    private RecyclerView panel_LST_others;
//
//
//
//    private static final String[] OTHERS = new String[]{
//            "US",
//            "EU",
//            "GB",
//            "JP",
//            "AE"
//    };
//
//    private Adapter_Country adapter_country;
//
//    private DataManager dataManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        panel_IMG_flag = findViewById(R.id.panel_IMG_flag);
//        panel_EDT_amount = findViewById(R.id.panel_EDT_amount);
//        panel_LST_others = findViewById(R.id.panel_LST_others);
//
//        initViews();
//
//        dataManager = new DataManager(this);
//
//
//
//
//
//
//
//        ArrayList<Country> others = new ArrayList<>();
//        for (String code : OTHERS) {
//            others.add(dataManager.getCountries().get(code));
//        }
//
//
//        adapter_country = new Adapter_Country(others);
//        panel_LST_others.setLayoutManager(new LinearLayoutManager(this));
//        //panel_LST_others.setLayoutManager(new GridLayoutManager(this, 2));
//        panel_LST_others.setItemAnimator(new DefaultItemAnimator());
//        panel_LST_others.setAdapter(adapter_country);
//    }
//
//    private void updateUserCountryUi(Country country) {
//        String countryName = country.getCountryName();
//        countryName = countryName.toLowerCase();
//        countryName = countryName.replaceAll(" ", "-");
//
//        MyImageUtils.getInstance().loadImageFromAssets("flags" + File.separator + countryName + ".png", panel_IMG_flag);
//    }
//
//    private void initViews() {
//        panel_EDT_amount.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                amountTextChanged(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//    }
//
//    private void amountTextChanged(String inputStr) {
//        Log.d("pttt", "inputStr= " + inputStr);
//
//        try {
//            double inputD = Double.valueOf(inputStr);
//            inputD = inputD / dataManager.getChosenCountry().getUsdRate();
//
//            adapter_country.update(inputD);
//        } catch (Exception ex) {
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        checkContactsPermission();
//
//        getAddressFromLocation(32.04885563, 34.80659724);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        mLocationManager.removeUpdates(mLocationListener);
//    }
//
//
//
//    private void tryToSampleLocation() {
//        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        //mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.1f, mLocationListener);
//        mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, Looper.myLooper());
//    }
//
//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(final Location location) {
//            Log.d("pttt", "Location: " + location.getLatitude() + "," + location.getLongitude());
//            getAddressFromLocation(location.getLatitude(), location.getLongitude());
//        }
//
//        @Override
//        public void onProviderEnabled(@NonNull String provider) {
//        }
//
//        @Override
//        public void onProviderDisabled(@NonNull String provider) {
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//    };
//
//    private void getAddressFromLocation(double latitude, double longitude) {
//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        try {
//            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
//            Log.d("pttt", "country: " + country);
//            Log.d("pttt", "country: " + addresses.get(0).getCountryCode());
//
//            chosenCountry = countries.get(addresses.get(0).getCountryCode());
//            updateUserCountryUi(dataManager.getChosenCountry());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void checkContactsPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d("pttt", "No Permission granted");
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                Log.d("pttt", "shouldShowRequestPermissionRationale");
//                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
//            } else {
//                openSettingsToManuallyPermission();
//                Log.d("pttt", "No shouldShowRequestPermissionRationale");
//            }
//        } else {
//            tryToSampleLocation();
//        }
//    }
//
//    private void openPermissionInfoDialog() {
//        String message = getString(R.string.permission_rationale);
//        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, 0)
//                .setMessage(message)
//                .setPositiveButton(getString(R.string.ok),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                checkContactsPermission();
//                            }
//                        })
//                .show();
//        alertDialog.setCanceledOnTouchOutside(true);
//    }
//
//    private void openSettingsToManuallyPermission() {
//        String message = getString(R.string.message_permission_disabled);
//        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, 0)
//                .setMessage(message)
//                .setPositiveButton(getString(R.string.ok),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent();
//                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivityForResult(intent, 126);
//                                dialog.cancel();
//                            }
//                        })
//                .setNegativeButton(getString(R.string.cancel), null)
//                .show();
//        alertDialog.setCanceledOnTouchOutside(true);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("pttt", "onActivityResult");
//
//        if (requestCode == 126) {
//            checkContactsPermission();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        Log.d("pttt", "onRequestPermissionsResult");
//        switch (requestCode) {
//            case 123: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("pttt", "PERMISSION_GRANTED");
//                    tryToSampleLocation();
//                } else {
//                    Log.d("pttt", "PERMISSION_DENIED");
//                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
//
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                        openPermissionInfoDialog();
//                    } else {
//                        openSettingsToManuallyPermission();
//                    }
//                }
//                return;
//            }
//        }
//    }
//
//}
//
