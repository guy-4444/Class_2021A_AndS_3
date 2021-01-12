package com.classy.class_2021a_ands_3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.paz.accesstolib.GiveMe;
import com.paz.accesstolib.GrantListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.classy.class_2021a_ands_3.MainActivity.*;

public class MainActivity extends AppCompatActivity implements CallBack_MainActivity {

    // 96a95018cff24b0a828e1e0f90faa488
    // https://currencyfreaks.com/pricing.html


    private DataManager dataManager;
    private MyLocationHelper myLocationHelper;
    private GiveMe giveMe;
    private ViewController viewController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewController = new ViewController(this, inputListener, this);
        giveMe = new GiveMe(this);
        myLocationHelper = new MyLocationHelper(this);
        dataManager = new DataManager(this, Constants.CURRENCY_API);

        viewController.initBackground(Constants.BACKGROUND_NAME);


        ArrayList<Country> others = dataManager.getSelectedCountries(Constants.OTHERS);
        viewController.updateList(others);
    }

    private ViewController.CallBack_InputListener inputListener = new ViewController.CallBack_InputListener() {
        @Override
        public void amountTextChanged(String inputStr) {
            Log.d("pttt", "inputStr= " + inputStr);

            try {
                double inputD = Double.valueOf(inputStr);
                inputD = CurrencyCalculator.getRate(inputD, dataManager.getChosenCountry().getUsdRate());
                viewController.updateList(inputD);
            } catch (Exception ex) {
            }
        }
    };



    @Override
    protected void onStart() {
        super.onStart();
        giveMe.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, grantListener);

        //getAddressFromLocation(32.04885563, 34.80659724);
    }

    private void tryToSampleLocation() {
        myLocationHelper.getCountryCode(new MyLocationHelper.CallBack_CountryCode() {
            @Override
            public void countryCodeReady(String countryCode) {
                dataManager.updateChosenCountry(countryCode);
                viewController.updateUserCountryUi(dataManager.getChosenCountryName());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocationHelper.stopListener();
    }

    GrantListener grantListener = new GrantListener() {
        @Override
        public void onGranted(boolean allGranted) {
            if (allGranted) {
                tryToSampleLocation();
            }
        }

        @Override
        public void onNotGranted(String[] permissions) {
            giveMe.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, grantListener);
        }

        @Override
        public void onNeverAskAgain(String[] permissions) {
            giveMe.askPermissionsFromSetting("Give me my permissions", new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, null);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        giveMe.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        giveMe.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void openPrivacyPolicy() {
        // https://app-privacy-policy-generator.firebaseapp.com/
        // https://sites.google.com/view/super-currency-privacy-policy
        MyUtils.openHtmlTextDialog(this, "privacy_policy.html");

    }

    @Override
    public void openTermsOfUse() {
        MyUtils.openHtmlTextDialog(this,  "terms_of_use.html");
    }

}