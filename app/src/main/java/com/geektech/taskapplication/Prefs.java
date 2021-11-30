package com.geektech.taskapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context){
        preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    public void saveBoardState(){
        preferences.edit().putBoolean("isBoardShown", true).apply();

    }
    public boolean isBoardShown(){
        return preferences.getBoolean("isBoardShown", false);
    }

    public void saveProfileImage(Uri uri){
        preferences.edit().putString("profile_image", String.valueOf(uri)).apply();
    }

    public String getProfileImage(){
        return preferences.getString("profile_image",null);
    }

}
