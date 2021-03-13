package com.zachl.jnavigator.objects.managers;

import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.room.AppDatabase;
import com.zachl.jnavigator.objects.room.Bookmark;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    public static AppDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(AppDatabase database) {
        RoomManager.database = database;
    }

    private static AppDatabase database;

    public static Bookmark toBookmark(Journal journal){
        Bookmark b = new Bookmark();
        b.title = journal.title;
        b.author = journal.author;
        b.summary = journal.summary;
        b.sample = journal.sample;
        b.follow = journal.sample;
        b.date = Integer.parseInt(journal.date.trim());
        b.keywords = journal.keywords;
        b.url = journal.url;
        b.type = journal.type;
        return b;
    }
    public static ArrayList<Journal> toJournalList(List<Bookmark> bookmarks){
        ArrayList<Journal> journals = new ArrayList<>();
        for(Bookmark bm : bookmarks){
            Journal j = new Journal();
            j.bookmarked = true;
            j.title = bm.title;
            j.author = bm.author;
            j.summary = bm.summary;
            j.sample = bm.sample;
            j.follow = bm.sample;
            j.date = "" + bm.date;
            j.keywords = bm.keywords;
            j.url = bm.url;
            j.type = bm.type;
            journals.add(j);
        }
        return journals;
    }
}
