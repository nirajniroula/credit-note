package com.wolf.nniroula.creditrecorder.activity;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class CreditApplication extends Application {

    public static final int VERSION = 106;
    private static Context mContext;

    public static RefWatcher getRefWatcher(Context context) {
        CreditApplication application = (CreditApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);
        CreditApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return CreditApplication.mContext;
    }

//    public static String getAndroidId() {
//        return Settings.Secure.getString(
//                getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//    }

}
