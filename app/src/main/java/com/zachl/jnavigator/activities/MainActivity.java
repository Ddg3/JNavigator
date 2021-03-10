package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.zachl.jnavigator.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton pfp, bookmark;
    private Button search, save, defs, submit;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        pfp = findViewById(R.id.pfp);
        bookmark = findViewById(R.id.myBookmarks);
        search = findViewById(R.id.search);
        save = findViewById(R.id.savedSearches);
        defs = findViewById(R.id.help);
        submit = findViewById(R.id.submit);

        pfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
