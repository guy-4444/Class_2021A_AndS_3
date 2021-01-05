package com.classy.class_2021a_ands_3;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class Adapter_Country extends RecyclerView.Adapter<Adapter_Country.ViewHolder> {

    private ArrayList<Country> countryList;
    private double inputUSD = 0.0;

    public Adapter_Country(ArrayList<Country> countryList) {
        this.countryList = countryList;
    }

    public void update(double inputUSD) {
        this.inputUSD = inputUSD;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countryList == null ? 0 : countryList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_country, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Log.d("ptttL", "onBindViewHolder " + listPosition);
        Country c = countryList.get(listPosition);


        holder.country_LBL_name.setText(c.getCountryName());
        holder.country_LBL_currency.setText(c.getCurrencyName() + " (" + c.getCurrencyCode() + ")");

        double rateCur = c.getUsdRate() * inputUSD;
        String cur = String.format("%.3f", rateCur);
        String symbol = c.getCurrencySymbol();

        String allLine = cur + " " + symbol;
        Spannable s = new SpannableString(allLine);
        s.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), allLine.length() - symbol.length(), allLine.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new RelativeSizeSpan(0.7f), allLine.length() - symbol.length(), allLine.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.country_LBL_rate.setText(s);


        String countryName = c.getCountryName();
        countryName = countryName.toLowerCase();
        countryName = countryName.replaceAll(" ", "-");

        MyImageUtils.getInstance().loadImageFromAssets("flags" + File.separator + countryName + ".png", holder.country_IMG_flag);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView country_IMG_flag;
        public TextView country_LBL_name;
        public TextView country_LBL_rate;
        public TextView country_LBL_currency;

        public ViewHolder(View itemView) {
            super(itemView);
            country_IMG_flag = itemView.findViewById(R.id.country_IMG_flag);
            country_LBL_name = itemView.findViewById(R.id.country_LBL_name);
            country_LBL_rate = itemView.findViewById(R.id.country_LBL_rate);
            country_LBL_currency = itemView.findViewById(R.id.country_LBL_currency);
        }
    }
}
