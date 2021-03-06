package com.classy.class_2021a_ands_3;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;

public class ViewController {

    public interface CallBack_InputListener {
        void amountTextChanged(String input);
    }

    private AppCompatActivity appCompatActivity;

    private ImageView panel_IMG_back;
    private ImageView panel_IMG_flag;
    private TextInputLayout panel_EDT_amount;
    private RecyclerView panel_LST_others;
    private MaterialButton panel_BTN_privacyPolicy;
    private MaterialButton panel_BTN_termsOfUse;

    private Adapter_Country adapter_country;
    private CallBack_InputListener callBack_inputListener;
    private CallBack_MainActivity callBack_mainActivity;

    public ViewController(AppCompatActivity appCompatActivity, CallBack_InputListener callBack_inputListener, CallBack_MainActivity callBack_mainActivity) {
        this.appCompatActivity = appCompatActivity;
        this.callBack_inputListener = callBack_inputListener;
        this.callBack_mainActivity = callBack_mainActivity;

        findViews();
        initViews();
    }

    private void findViews() {
        panel_IMG_back = appCompatActivity.findViewById(R.id.panel_IMG_back);
        panel_IMG_flag = appCompatActivity.findViewById(R.id.panel_IMG_flag);
        panel_EDT_amount = appCompatActivity.findViewById(R.id.panel_EDT_amount);
        panel_LST_others = appCompatActivity.findViewById(R.id.panel_LST_others);
        panel_BTN_privacyPolicy = appCompatActivity.findViewById(R.id.panel_BTN_privacyPolicy);
        panel_BTN_termsOfUse = appCompatActivity.findViewById(R.id.panel_BTN_termsOfUse);
    }

    private void initViews() {
        panel_EDT_amount.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (callBack_inputListener != null) {
                    callBack_inputListener.amountTextChanged(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        panel_BTN_privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack_mainActivity != null) {
                    callBack_mainActivity.openPrivacyPolicy();
                }
            }
        });

        panel_BTN_termsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack_mainActivity != null) {
                    callBack_mainActivity.openTermsOfUse();
                }
            }
        });
    }

    public void initBackground(String drawableName) {
        MyImageUtils.getInstance().loadImageByDrawableName(drawableName, panel_IMG_back);
    }

    public void updateUserCountryUi(String countryName) {
        MyImageUtils.getInstance().loadImageFromAssets("flags" + File.separator + countryName + ".png", panel_IMG_flag);
    }

    public void updateList(double inputD) {
        adapter_country.update(inputD);
    }

    public void updateList(ArrayList<Country> others) {
        adapter_country = new Adapter_Country(others);
        panel_LST_others.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        //panel_LST_others.setLayoutManager(new GridLayoutManager(this, 2));
        panel_LST_others.setItemAnimator(new DefaultItemAnimator());
        panel_LST_others.setAdapter(adapter_country);
    }
}
