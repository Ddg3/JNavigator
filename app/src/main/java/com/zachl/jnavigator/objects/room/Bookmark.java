package com.zachl.jnavigator.objects.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.ParcelField;

@Entity
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "TITLE")
    public String title;

    @ColumnInfo(name = "AUTHOR")
    public String author;

    @ColumnInfo(name = "JOURNAL")
    public String journal;

    @ColumnInfo(name = "DATE")
    public int date;

    @ColumnInfo(name = "SAMPLE")
    public int sample;

    @ColumnInfo(name = "FOLLOW")
    public int follow;

    @ColumnInfo(name = "SUMMARY")
    public String summary;

    @ColumnInfo(name = "KEYWORDS")
    public String keywords;

    @ColumnInfo(name = "TYPE")
    public String type;

    @ColumnInfo(name = "URL")
    public String url;
}
