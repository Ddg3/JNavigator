package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.entities.User;
import com.zachl.jnavigator.objects.managers.PreferenceManager;
import com.zachl.jnavigator.objects.runnables.RoomRunnable;

public class MainActivity extends AppCompatActivity {
    private ImageButton pfp, bookmark;
    private Button search, save, defs, submit, review;
    private TextView welcome;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        RoomRunnable r = new RoomRunnable(this);
        AsyncTask.execute(r);

        pfp = findViewById(R.id.pfp);
        bookmark = findViewById(R.id.myBookmarks);
        search = findViewById(R.id.search);
        //save = findViewById(R.id.savedSearches);
        defs = findViewById(R.id.help);
        submit = findViewById(R.id.submit);
        review = findViewById(R.id.review);
        welcome = findViewById(R.id.name);

        checkAdminPermission();
        if(!PreferenceManager.getUser().signedIn) {
            String userName = getIntent().getStringExtra("userName");
            welcome.setText(welcome.getText().toString().replace("Name", userName.split(" ")[0]));
            User signedIn = PreferenceManager.getUser().signIn();
            signedIn.name = userName;
            PreferenceManager.saveUser(signedIn);
        }
        else{
            welcome.setText(welcome.getText().toString().replace("Name", PreferenceManager.getUser().name.split(" ")[0]));
        }

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

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JournalResults.class);
                intent.putExtra("bookmarksOnly", true);
                context.startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserSubmitActivity.class);
                intent.putExtra("isAdmin", checkAdminPermission());
                context.startActivity(intent);
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminReviewActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAdminPermission();
    }

    private boolean checkAdminPermission(){
        if(PreferenceManager.getUser().isAdmin){
            review.setVisibility(View.VISIBLE);
            return true;
        }
        else{
            review.setVisibility(View.INVISIBLE);
            return false;
        }
    }
}
