package com.zachl.jnavigator.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.managers.JournalManager;

public class BookmarkButton {
    public BookmarkButton(final Context context, ImageButton button, final Journal journal){
        if(journal.bookmarked){
            button.setBackground(context.getDrawable(R.drawable.filled_bm));
            button.setTag(R.drawable.filled_bm);
        }
        else{
            button.setBackground(context.getDrawable(R.drawable.empty_bm));
            button.setTag(R.drawable.empty_bm);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Integer) v.getTag() == R.drawable.empty_bm){
                    v.setBackground(context.getDrawable(R.drawable.filled_bm));
                    v.setTag(R.drawable.filled_bm);
                    JournalManager.addBookmark(context, journal);
                    journal.bookmarked = true;
                    Toast.makeText(context, "Saved Journal to Bookmarks!", Toast.LENGTH_SHORT).show();
                }
                else{
                    v.setBackground(context.getDrawable(R.drawable.empty_bm));
                    v.setTag(R.drawable.empty_bm);
                    JournalManager.removeByUrl(context, journal.url);
                    journal.bookmarked = false;
                    Toast.makeText(context, "Removed Journal from Bookmarks", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
