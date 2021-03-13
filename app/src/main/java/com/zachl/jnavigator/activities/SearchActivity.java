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

import com.zachl.jnavigator.R;
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
    private Button search, save;
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
        save = findViewById(R.id.saveSearch);
        addKey = findViewById(R.id.addKey);

        fieldViews = new View[]{title, author, sampleMax, sampleMin, followMax, followMin, url, keywords, type};

        keywords.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new KeywordAdapter(keys);
        keywords.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JournalResults.class);
                String fields = getFields();
                intent.putExtra("fields", fields);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View popup = inflater.inflate(R.layout.keyword_popup, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow window = new PopupWindow(popup, width, height, focusable);
                window.showAtLocation(addKey, Gravity.CENTER, 0,0);

                final EditText name = popup.findViewById(R.id.keyPopField);
                Button enter = popup.findViewById(R.id.addKeyPop);
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        keys.add(name.getText().toString());
                        adapter = new KeywordAdapter(keys);
                        keywords.setAdapter(adapter);
                        window.dismiss();
                    }
                });
            }
        });
    }

    private String getFields(){
        String fields = "";
        for(View view : fieldViews){
            if(view instanceof TextView){
                String name = getResources().getResourceEntryName(view.getId()).replace("Field", "");
                String content = ((TextView) view).getText().toString();
                if(!content.equalsIgnoreCase(""))
                    fields += name.toUpperCase() + PAIRER + content + SEPARATOR;
            }
            else if(view instanceof RecyclerView){
                int count = ((RecyclerView) view).getChildCount();
                for(int i = 0; i < count; i++){
                    View child = ((ConstraintLayout)((RecyclerView) view).getChildAt(i)).getChildAt(0);
                    String content = ((TextView)child).getText().toString();
                    fields += "KEYWORDS" + PAIRER + content + SEPARATOR;
                }
            }
        }
        return fields;
    }
}
