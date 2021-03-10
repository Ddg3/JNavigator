package com.zachl.jnavigator.objects;

import android.content.SharedPreferences;
import android.media.Image;

import com.google.gson.Gson;

import java.util.ArrayList;

public class User {
    private SharedPreferences prefs;

    public boolean isAdmin;
    public String name;
    public ArrayList<Journal> bookmarks;
    public Settings settings;
    public Image pfp;

    public User(SharedPreferences prefs){
        this.prefs = prefs;
    }
    public ArrayList<Journal> retrieveBookmarks(){
        Gson gson = new Gson();
        String json = prefs.getString("bookmarks", "");
        bookmarks = gson.fromJson(json, ArrayList.class);
        return bookmarks;
    }
    public Settings retrieveSettings(){
        Gson gson = new Gson();
        String json = prefs.getString("settings", "");
        settings = gson.fromJson(json, Settings.class);
        return settings;
    }
    public void saveSettings(){
        SharedPreferences.Editor edit = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(settings);
        edit.putString("settings", json);
        edit.apply();
    }
    public void saveBookmarks(){
        SharedPreferences.Editor edit = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarks);
        edit.putString("bookmarks", json);
        edit.apply();
    }
}
