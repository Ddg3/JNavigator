package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.entities.User;
import com.zachl.jnavigator.objects.managers.PreferenceManager;

public class ProfileActivity extends AppCompatActivity {
    private Chip offline, push, search, download, admin;
    private TextView fullName;
    private Button save, signOut;
    private static Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_profile);
        offline = findViewById(R.id.offline);
        push = findViewById(R.id.push);
        search = findViewById(R.id.search);
        download = findViewById(R.id.download);
        admin = findViewById(R.id.admin);
        save = findViewById(R.id.save);
        fullName = findViewById(R.id.fullName);
        signOut = findViewById(R.id.signOut);

        user = PreferenceManager.getUser();
        offline.setChecked(user.offline);
        push.setChecked(user.pushNotifs);
        search.setChecked(user.searchOnData);
        download.setChecked(user.downloadOnData);
        admin.setChecked(user.isAdmin);
        fullName.setText(user.name);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                user.offline = offline.isChecked();
                user.pushNotifs = push.isChecked();
                user.searchOnData = search.isChecked();
                user.downloadOnData = download.isChecked();
                user.isAdmin = admin.isChecked();
                PreferenceManager.saveUser(user);
                context.startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                PreferenceManager.saveUser(new User());
                context.startActivity(intent);
            }
        });
    }
}
