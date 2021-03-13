package com.zachl.jnavigator.objects.entities;

import android.media.Image;

public class User {
    public boolean pushNotifs, offline, searchOnData, downloadOnData, isAdmin, signedIn;
    public Image pfp;
    public String name;

    public User signIn(){
        signedIn = true;
        return this;
    }
    public User signOut(){
        signedIn = false;
        return this;
    }
}
