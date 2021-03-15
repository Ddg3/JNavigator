package com.zachl.jnavigator.objects.managers;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zachl.jnavigator.objects.entities.User;

public class PreferenceManager {
    private static SharedPreferences prefs;
    private static User user;
    public static void setPrefs(SharedPreferences tPrefs){
        prefs = tPrefs;
    }
    public static void saveUser(User s){
        user = s;
        SharedPreferences.Editor edit = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(s);
        edit.putString("user", json);
        edit.apply();
    }
    public static User getUser(){
        Gson gson = new Gson();
        String json = prefs.getString("user", "");
        user = gson.fromJson(json, User.class);
        if(user == null)
            user = new User();
        return user;
    }
}
