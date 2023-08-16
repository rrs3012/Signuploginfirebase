package com.example.signuploginfirebase;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedpreferences {
    private static final MySharedpreferences ourInstance = new MySharedpreferences();

    public static MySharedpreferences getInstance() {
        return ourInstance;
    }

    private MySharedpreferences() {
    }

    public SharedPreferences sharedPreferencesInstance(Context mContext) {
        return mContext.getSharedPreferences("StoreDatabase", 0);
    }

    public void save(Context mContext, String key, String value) {
        mContext.getSharedPreferences("StoreDatabase", 0).edit().putString(key, value).commit();
    }

    public void remove(Context mContext, String key) {
        mContext.getSharedPreferences("StoreDatabase", 0).edit().remove(key).commit();
    }

    public String get(Context mContext, String key) {
        return mContext.getSharedPreferences("StoreDatabase", 0).getString(key, (String) null);
    }

    public void removeAll(Context mContext) {
        mContext.getSharedPreferences("StoreDatabase", 0).edit().clear().commit();
    }
}
