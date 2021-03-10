package com.zachl.jnavigator.objects;

import android.content.Intent;

import java.util.ArrayList;

public class Journal {
    public String title, author, summary, date, url, type, journal, keywords;
    public int sample, follow;

    public Journal(){}
    public Journal(String title, String author, String url, String date, String type, int sample, int follow, String summary, String keywords, String journal){
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.date = date;
        this.url = url;
        this.type = type;
        this.sample = sample;
        this.follow = follow;
        this.keywords = keywords;
        this.journal = journal;
    }
}
