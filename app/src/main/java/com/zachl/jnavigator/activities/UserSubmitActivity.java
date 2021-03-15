package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.managers.ConnectionManager;
import com.zachl.jnavigator.objects.managers.SqlManager;
import com.zachl.jnavigator.views.AddKeywordButton;
import com.zachl.jnavigator.views.KeywordAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSubmitActivity extends AppCompatActivity {
    private static final String SEPARATOR = "==", PAIRER = "::";
    private TextView url, title, author, summary, sample, follow, type, date;
    private RecyclerView recycler;
    private Button submit;
    private ImageButton addKey;
    private KeywordAdapter adapter;
    private List<String> keys = new ArrayList<>();
    private View[] fieldViews;
    private Context context;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_user_submit);
        url = findViewById(R.id.urlField);
        title = findViewById(R.id.titleField);
        author = findViewById(R.id.authorField);
        summary = findViewById(R.id.summaryField);
        sample = findViewById(R.id.sampleField);
        follow = findViewById(R.id.followField);
        type = findViewById(R.id.typeField);
        date = findViewById(R.id.dateField);
        recycler = findViewById(R.id.keywords);
        submit = findViewById(R.id.submit);
        addKey = findViewById(R.id.addKey);
        fieldViews = new View[]{url, title, author, summary, sample, follow, type, date, recycler};
        adapter = new KeywordAdapter(keys);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(adapter);

        AddKeywordButton button = new AddKeywordButton(this, keys, addKey, adapter, recycler);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!url.getText().toString().equalsIgnoreCase("")) {
                    Connection con = ConnectionManager.getConnection();
                    try {
                        String insert = "insert into public.\"ReviewJournals\"(";
                        String[] fields = SqlManager.getFields(getApplicationContext(), fieldViews).split(SEPARATOR);
                        for (String field : fields) {
                            insert += "\"" + field.split(PAIRER)[0] + "\", ";
                        }
                        insert = insert.substring(0, insert.length() - 2) + ") VALUES(";
                        for (String field : fields) {
                            insert += "?,";
                        }
                        insert = insert.substring(0, insert.length() - 1) + ")";
                        PreparedStatement pst = con.prepareStatement(insert);
                        final String[] INT_FIELDS = {"SAMPLE", "FOLLOW"};
                        pstLoop:
                        for (int i = 0; i < fields.length; i++) {
                            for (String intField : INT_FIELDS) {
                                if (fields[i].equalsIgnoreCase(intField)) {
                                    pst.setInt(i + 1, Integer.parseInt(fields[i].split(PAIRER)[1].trim()));
                                    continue pstLoop;
                                }
                            }
                            pst.setString(i + 1, fields[i].split(PAIRER)[1].trim());
                        }
                        pst.executeUpdate();
                        pst.close();
                        Toast.makeText(UserSubmitActivity.this, "Sent Journal in for Review! Thank you for your contribution", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    } catch (SQLException e) {
                        Log.d("UserSubmitActivity", e.getMessage());
                    }
                }
                else{
                    Toast.makeText(context, "The Url Field must be filled in to submit a journal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
