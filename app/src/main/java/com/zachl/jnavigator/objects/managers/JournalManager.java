package com.zachl.jnavigator.objects.managers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zachl.jnavigator.objects.entities.Journal;

import java.util.ArrayList;
import java.util.List;

public class JournalManager {
    public static void removeByUrl(Context context, String url){
        for(Journal j : bookmarks){
            if(j.url.equalsIgnoreCase(url)){
                bookmarks.remove(j);
                final Journal journal = j;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        RoomManager.getDatabase().bookmarkDao().delete(journal.url);
                    }
                });
                Toast.makeText(context, "Removed Journal from Bookmarks", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    public static void addBookmark(Context context, final Journal journal){
        bookmarks.add(journal);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RoomManager.getDatabase().bookmarkDao().insert(RoomManager.toBookmark(journal));
            }
        });
        Toast.makeText(context, "Saved Journal to Bookmarks!", Toast.LENGTH_SHORT).show();
    }
    public static List<Object> getBookmarks() {
        List<Object> objects = new ArrayList<>();
        for(Journal j : bookmarks){
            objects.add(j);
        }
        return objects;
    }

    public static Journal getBookmarkByUrl(String url){
        for(Journal j : bookmarks){
            if(j.url.equalsIgnoreCase(url))
                return j;
        }
        return null;
    }

    public static void setBookmarks(ArrayList<Journal> bookmarks) {
        JournalManager.bookmarks = bookmarks;
    }

    private static ArrayList<Journal> bookmarks = new ArrayList<>();
    public static List<Object> getResultJournals() {
        List<Object> objects = new ArrayList<>();
        for(Journal j : resultJournals){
           objects.add(j);
        }
        return objects;
    }

    public static void setResultJournals(List<Object> result) {
        resultJournals.clear();
        for(Object o : result){
            resultJournals.add((Journal)o);
        }
        for(Journal b : bookmarks){
            Log.d("JournalManager", b.title);
            for(Journal j : resultJournals){
                if(b.url.equalsIgnoreCase(j.url)){
                    j.bookmarked = true;
                }
            }
        }
    }

    private static ArrayList<Journal> resultJournals = new ArrayList<>();

    public static Journal getByUrl(String url){
        for(Journal j : resultJournals){
            if(j.url.equalsIgnoreCase(url)){
                return j;
            }
        }
        return null;
    }
}
