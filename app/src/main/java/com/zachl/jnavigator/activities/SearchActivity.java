package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.Settings;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {
    private static Context context;
    private static final String SEPARATOR = "==", PAIRER = "::";
    private View[] fieldViews;
    private TextView title, author, sample, follow, type, url;
    private LinearLayout keywords;
    private Button search, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_search);
        title = findViewById(R.id.titleField);
        author = findViewById(R.id.authorField);
        sample = findViewById(R.id.sampleField);
        follow = findViewById(R.id.followField);
        //TYPE
        url = findViewById(R.id.urlField);
        keywords = findViewById(R.id.keywords);
        search = findViewById(R.id.searchButton);
        save = findViewById(R.id.saveSearch);

        fieldViews = new View[]{title, author, sample, follow, url, keywords};

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
            else if(view instanceof LinearLayout){
                int count = ((LinearLayout) view).getChildCount();
                for(int i = 0; i < count; i++){
                    View child = ((LinearLayout) view).getChildAt(i);
                    fields += ((TextView)child).getText().toString() + SEPARATOR;
                }
            }
        }
        return fields;
    }
}
