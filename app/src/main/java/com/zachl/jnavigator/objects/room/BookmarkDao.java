package com.zachl.jnavigator.objects.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmark")
    List<Bookmark> getBookmarks();

    @Insert
    void insert(Bookmark bookmark);

    @Query("DELETE FROM bookmark WHERE URL = :url")
    void delete(String url);
}
