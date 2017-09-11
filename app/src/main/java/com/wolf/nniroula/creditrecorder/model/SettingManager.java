package com.wolf.nniroula.creditrecorder.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wolf.nniroula.creditrecorder.activity.CreditApplication;

public class SettingManager {

// store value//////////////////////////////////////////////////////////////////////////////////////

    // whether it is CoCoin's first time
    private Boolean FIRST_TIME;
    // color reminder
    private Boolean IS_COLOR_REMIND;
    // the color of the reminder
    private Integer REMIND_COLOR;

    // account bool name
    private String ACCOUNT_BOOK_NAME;
    // whether remind update
    private Boolean REMIND_UPDATE;
    // whether this version can be updated
    private Boolean CAN_BE_UPDATED;
    private Boolean AD_WATCHED;


    private String CURRENCY;

    private String SORT;

    private int DUE_LIMIT;


    // tell the main view to change the title
    private Boolean MAIN_VIEW_TITLE_SHOULD_CHANGE = false;

    private Boolean MAIN_VIEW_REMIND_COLOR_SHOULD_CHANGE = false;


// default value////////////////////////////////////////////////////////////////////////////////////

    // default first time
    private final Boolean DEFAULT_FIRST_TIME = true;
    // color reminder by default
    private final Boolean DEFAULT_IS_COLOR_REMIND = false;
    // the color of the reminder defaulty
    private final Integer DEFAULT_REMIND_COLOR = (int) Long.parseLong("FFE91E63", 16);
    // account bool name by default
    private final String DEFAULT_ACCOUNT_BOOK_NAME = "Credit Note";
    // whether remind update
    private final Boolean DEFAULT_REMIND_UPDATE = true;
    // whether this version can be updated

    private final Boolean DEFAULT_CAN_BE_UPDATED = false;

    private final Boolean DEFAULT_AD_WATCHED = false;


    private final int DEFAULT_DUE_LIMIT = 1000;

    private final String DEFAULT_CURRENCY = "NRS";

    private final String DEFAULT_SORT = "Date";


    private boolean SHOW_MAIN_ACTIVITY_GUIDE = true;

    private boolean SHOW_LIST_VIEW_GUIDE = true;

    private static SettingManager settingInstance = new SettingManager();

    public static SettingManager getInstance() {
        return settingInstance;
    }

    private SettingManager() {
    }

    public Boolean getFirstTime() {
        FIRST_TIME = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("FIRST_TIME", DEFAULT_FIRST_TIME);
        return FIRST_TIME;
    }

    public void setVideoWatched(Boolean AD_WATCHED) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("AD_WATCHED", AD_WATCHED);
        editor.commit();
        this.AD_WATCHED = AD_WATCHED;
    }

    public Boolean isVideoWatched() {
        AD_WATCHED = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("AD_WATCHED", DEFAULT_AD_WATCHED);
        return AD_WATCHED;
    }

    public void setFirstTime(Boolean FIRST_TIME) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("FIRST_TIME", FIRST_TIME);
        editor.commit();
        this.FIRST_TIME = FIRST_TIME;
    }


    public Boolean getIsColorRemind() {
        IS_COLOR_REMIND = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("IS_COLOR_REMIND", DEFAULT_IS_COLOR_REMIND);
        return IS_COLOR_REMIND;
    }

    public void setIsColorRemind(Boolean IS_COLOR_REMIND) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("IS_COLOR_REMIND", IS_COLOR_REMIND);
        editor.commit();
        this.IS_COLOR_REMIND = IS_COLOR_REMIND;
    }

    public int getRemindColor() {
        REMIND_COLOR = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getInt("REMIND_COLOR", DEFAULT_REMIND_COLOR);
        return REMIND_COLOR;
    }

    public void setRemindColor(int REMIND_COLOR) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putInt("REMIND_COLOR", REMIND_COLOR);
        editor.commit();
        this.REMIND_COLOR = REMIND_COLOR;
    }

    public int getDueLimit() {
        DUE_LIMIT = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getInt("DUE_LIMIT", DEFAULT_DUE_LIMIT);
        return DUE_LIMIT;
    }

    public void setDueLimit(int DUE_LIMIT) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putInt("DUE_LIMIT", DUE_LIMIT);
        editor.commit();
        this.DUE_LIMIT = DUE_LIMIT;
    }

    public String getNotesName() {
        ACCOUNT_BOOK_NAME = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getString("ACCOUNT_BOOK_NAME", DEFAULT_ACCOUNT_BOOK_NAME);
        return ACCOUNT_BOOK_NAME;
    }

    public void setNotesName(String ACCOUNT_BOOK_NAME) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putString("ACCOUNT_BOOK_NAME", ACCOUNT_BOOK_NAME);
        editor.commit();
        this.ACCOUNT_BOOK_NAME = ACCOUNT_BOOK_NAME;
    }

    public String getCurrency() {
        CURRENCY = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getString("CURRENCY", DEFAULT_CURRENCY);
        return CURRENCY;
    }

    public void setCurrency(String CURRENCY) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putString("CURRENCY", CURRENCY);
        editor.commit();
        this.CURRENCY = CURRENCY;
    }

    public String getSort() {
        SORT = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getString("SORT", DEFAULT_SORT);
        return SORT;
    }

    public void setSort(String SORT) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putString("SORT", SORT);
        editor.commit();
        this.SORT = SORT;
    }

    public Boolean getRemindUpdate() {
        REMIND_UPDATE = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("REMIND_UPDATE", DEFAULT_REMIND_UPDATE);
        return REMIND_UPDATE;
    }

    public void setRemindUpdate(Boolean REMIND_UPDATE) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("REMIND_UPDATE", REMIND_UPDATE);
        editor.commit();
        this.REMIND_UPDATE = REMIND_UPDATE;
    }

    public Boolean getCanBeUpdated() {
        CAN_BE_UPDATED = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("CAN_BE_UPDATED", DEFAULT_CAN_BE_UPDATED);
        return CAN_BE_UPDATED;
    }

    public void setCanBeUpdated(Boolean CAN_BE_UPDATED) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("CAN_BE_UPDATED", CAN_BE_UPDATED);
        editor.commit();
        this.CAN_BE_UPDATED = CAN_BE_UPDATED;
    }


    public boolean getShowMainActivityGuide() {
        SHOW_MAIN_ACTIVITY_GUIDE = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("SHOW_MAIN_ACTIVITY_GUIDE", false);
        return SHOW_MAIN_ACTIVITY_GUIDE;
    }

    public void setShowMainActivityGuide(boolean SHOW_MAIN_ACTIVITY_GUIDE) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("SHOW_MAIN_ACTIVITY_GUIDE", SHOW_MAIN_ACTIVITY_GUIDE);
        editor.commit();
        this.SHOW_MAIN_ACTIVITY_GUIDE = SHOW_MAIN_ACTIVITY_GUIDE;
    }

    public void setMainViewTitleShouldChange(Boolean MAIN_VIEW_REMIND_COLOR_SHOULD_CHANGE) {
        this.MAIN_VIEW_REMIND_COLOR_SHOULD_CHANGE = MAIN_VIEW_REMIND_COLOR_SHOULD_CHANGE;
    }

    public boolean getListViewGuide() {
        SHOW_LIST_VIEW_GUIDE = PreferenceManager.
                getDefaultSharedPreferences(CreditApplication.getAppContext())
                .getBoolean("SHOW_LIST_VIEW_GUIDE", false);
        return SHOW_LIST_VIEW_GUIDE;
    }

    public void setListViewGuide(boolean SHOW_LIST_VIEW_GUIDE) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(CreditApplication.getAppContext()).edit();
        editor.putBoolean("SHOW_LIST_VIEW_GUIDE", SHOW_LIST_VIEW_GUIDE);
        editor.commit();
        this.SHOW_LIST_VIEW_GUIDE = SHOW_LIST_VIEW_GUIDE;
    }

    public Boolean getMAIN_VIEW_REMIND_COLOR_SHOULD_CHANGE() {
        return MAIN_VIEW_REMIND_COLOR_SHOULD_CHANGE;
    }
}
