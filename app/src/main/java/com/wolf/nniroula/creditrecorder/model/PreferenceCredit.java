package com.wolf.nniroula.creditrecorder.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by root on 2/24/17.
 */

public class PreferenceCredit {
    private String currency;
    private String business;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private static String CURRENCY_KEY = "my_currency";
    private static String BUSINESS_KEY = "my_business";


    public PreferenceCredit(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPref.edit();
        currency = sharedPref.getString(CURRENCY_KEY, "NRS");
        business = sharedPref.getString(BUSINESS_KEY, "Credit Note");
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        editor.putString(CURRENCY_KEY, currency);
        editor.apply();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = currency;
        editor.putString(BUSINESS_KEY, business);
        editor.apply();
    }

}
