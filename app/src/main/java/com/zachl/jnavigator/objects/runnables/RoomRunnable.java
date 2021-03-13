package com.zachl.jnavigator.objects.runnables;

import android.content.Context;

import androidx.room.Room;

import com.zachl.jnavigator.objects.managers.JournalManager;
import com.zachl.jnavigator.objects.room.AppDatabase;
import com.zachl.jnavigator.objects.room.Bookmark;
import com.zachl.jnavigator.objects.managers.RoomManager;

import java.util.List;

public class RoomRunnable implements Runnable {
    private Context context;
    public RoomRunnable(Context context){
        this.context = context;
    }
    @Override
    public void run() {
        RoomManager.setDatabase(Room.databaseBuilder(context, AppDatabase.class, "bookmark").build());
        List<Bookmark> bms = RoomManager.getDatabase().bookmarkDao().getBookmarks();
        JournalManager.setBookmarks(RoomManager.toJournalList(bms));
    }
}
