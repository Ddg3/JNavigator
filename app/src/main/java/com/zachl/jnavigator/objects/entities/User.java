package com.zachl.jnavigator.objects.entities;

import android.media.Image;

public class User {
    public boolean pushNotifs, offline, searchOnData, downloadOnData, isAdmin, signedIn;
    public String name;

    public User signIn(){
        signedIn = true;
        return this;
    }
}
