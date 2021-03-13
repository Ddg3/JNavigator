package com.zachl.jnavigator.objects.entities;

import android.content.Intent;

import java.util.ArrayList;

public class Journal {
    public String title, author, summary, date, url, type, journal, keywords, user;
    public int sample, follow;
    public boolean bookmarked;

    public Journal(){
        bookmarked = false;
    }
}
