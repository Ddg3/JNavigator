package com.zachl.jnavigator.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.Journal;
import com.zachl.jnavigator.objects.JournalManager;

public class JournalActivity extends AppCompatActivity {
    private LinearLayout horiz;
    private TextView title, author, date, summary, sample, follow, type;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        String url = getIntent().getStringExtra("journalUrl");
        Journal journal = JournalManager.getByUrl(url);
        //horiz = findViewById(R.id.hLayout);
        title = findViewById(R.id.jTitle);
        author = findViewById(R.id.jAuthor);
        date = findViewById(R.id.jDate);
        summary = findViewById(R.id.jSummary);
        sample = findViewById(R.id.jSampleSize);
        follow = findViewById(R.id.jFup);
        type = findViewById(R.id.jType);

        title.setText(journal.title);
        author.setText(journal.author);
        date.setText(journal.date);
        summary.setText(journal.summary);
        sample.setText("" + journal.sample);
        follow.setText("" + journal.follow + " mo");
        type.setText(journal.type);
        /*for(String key : journal.keywords.split(",")){
            Button kButton = new Button(this);
            kButton.setText(key);
            kButton.setWidth(96);
            kButton.setHeight(32);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                kButton.setBackground(getDrawable(R.drawable.oval));
            }
            kButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Help page for keywords/definitions?
                }
            });
            horiz.addView(kButton);
        }*/
    }
}
