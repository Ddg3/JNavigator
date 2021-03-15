package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.managers.SqlManager;
import com.zachl.jnavigator.views.AddKeywordButton;
import com.zachl.jnavigator.views.KeywordAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static Context context;
    private static final String SEPARATOR = "==", PAIRER = "::";
    private View[] fieldViews;
    private TextView title, author, type, url;
    private TextView sampleMin, sampleMax, followMin, followMax;
    private RecyclerView keywords;
    private Button search;
    private KeywordAdapter adapter;
    private ImageButton addKey;
    private List<String> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_search);
        title = findViewById(R.id.titleField);
        author = findViewById(R.id.authorField);
        sampleMin = findViewById(R.id.sampleMin);
        sampleMax = findViewById(R.id.sampleMax);
        followMin = findViewById(R.id.followMin);
        followMax = findViewById(R.id.followMax);
        type = findViewById(R.id.typeField);
        url = findViewById(R.id.urlField);
        keywords = findViewById(R.id.keywords);
        search = findViewById(R.id.searchButton);
        addKey = findViewById(R.id.addKey);

        fieldViews = new View[]{title, author, sampleMax, sampleMin, followMax, followMin, url, keywords, type};

        keywords.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new KeywordAdapter(keys);
        keywords.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SqlManager.allFieldsEmpty(fieldViews)) {
                    Intent intent = new Intent(context, JournalResultsActivity.class);
                    String fields = SqlManager.getFields(context, fieldViews);
                    intent.putExtra("fields", fields);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SearchActivity.this, "Must Fill in at least One Field to Search", Toast.LENGTH_LONG).show();
                }
            }
        });

        AddKeywordButton button = new AddKeywordButton(this, keys, addKey, adapter, keywords);
    }
}
