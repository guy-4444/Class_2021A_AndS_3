package com.classy.class_2021a_ands_3;

public class Country {

    private String countryCode = "";
    private String countryName = "";
    private String currencyCode = "";
    private String currencyName = "";
    private String currencySymbol = "";
    private int population = 0;
    private String capital = "";
    private String continentName = "";
    private double usdRate = 0.0;

    public Country() { }

    public String getCountryCode() {
        return countryCode;
    }

    public Country setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Country setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public int getPopulation() {
        return population;
    }

    public Country setPopulation(int population) {
        this.population = population;
        return this;
    }

    public String getCapital() {
        return capital;
    }

    public Country setCapital(String capital) {
        this.capital = capital;
        return this;
    }

    public String getContinentName() {
        return continentName;
    }

    public Country setContinentName(String continentName) {
        this.continentName = continentName;
        return this;
    }

    public double getUsdRate() {
        return usdRate;
    }

    public Country setUsdRate(double usdRate) {
        this.usdRate = usdRate;
        return this;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public Country setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
        return this;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public Country setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
        return this;
    }
}
