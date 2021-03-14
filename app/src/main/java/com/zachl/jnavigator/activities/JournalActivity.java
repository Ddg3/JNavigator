package com.zachl.jnavigator.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.managers.JournalManager;
import com.zachl.jnavigator.views.BookmarkButton;
import com.zachl.jnavigator.views.KeywordAdapter;

import java.util.Arrays;

public class JournalActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private TextView title, author, date, summary, sample, follow, type, urlView;
    private ImageButton bookmark;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        String url = getIntent().getStringExtra("journalUrl");
        boolean bookmarksOnly = getIntent().getBooleanExtra("bookmarksOnly", false);
        Journal journal;
        if(!bookmarksOnly)
            journal = JournalManager.getByUrl(url);
        else
            journal = JournalManager.getBookmarkByUrl(url);
        title = findViewById(R.id.jTitle);
        author = findViewById(R.id.jAuthor);
        date = findViewById(R.id.jDate);
        summary = findViewById(R.id.jSummary);
        sample = findViewById(R.id.jSample);
        follow = findViewById(R.id.jFollow);
        type = findViewById(R.id.jType);
        bookmark = findViewById(R.id.jBookmark);
        urlView = findViewById(R.id.jUrl);

        recycler = findViewById(R.id.jKeyRecycler);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        KeywordAdapter adapter = new KeywordAdapter(Arrays.asList(journal.keywords.split(",")));
        recycler.setAdapter(adapter);

        BookmarkButton button = new BookmarkButton(this, bookmark, journal);

        title.setText(journal.title);
        author.setText(journal.author);
        date.setText(journal.date);
        summary.setText(journal.summary);
        sample.setText("" + journal.sample);
        follow.setText("" + journal.follow + " mo");
        type.setText(journal.type);
        urlView.setText(journal.url);
        //urlView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
