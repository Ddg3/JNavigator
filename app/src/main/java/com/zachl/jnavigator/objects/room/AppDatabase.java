package com.zachl.jnavigator.objects.room;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.zachl.jnavigator.views.BookmarkButton;

@Database(entities = {Bookmark.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookmarkDao bookmarkDao();
}
