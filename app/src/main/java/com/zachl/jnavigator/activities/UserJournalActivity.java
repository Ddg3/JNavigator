package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.managers.ConnectionManager;
import com.zachl.jnavigator.objects.managers.JournalManager;
import com.zachl.jnavigator.objects.managers.SqlManager;
import com.zachl.jnavigator.views.AddKeywordButton;
import com.zachl.jnavigator.views.KeywordAdapter;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserJournalActivity extends AppCompatActivity {
    private static final String SEPARATOR = "==", PAIRER = "::";
    private TextView title, author, summary, date, type, url, sample, follow;
    private Button save, remove, approve;
    private ImageButton addKey;
    private RecyclerView recycler;
    private List<String> keys = new ArrayList<>();
    private KeywordAdapter adapter;
    private View[] fieldViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_journal);
        String urlText = getIntent().getStringExtra("journalUrl");
        Journal j = JournalManager.getByUrl(urlText);

        save = findViewById(R.id.submitSave);
        remove = findViewById(R.id.submitRemove);
        approve = findViewById(R.id.submitApprove);
        title = findViewById(R.id.jTitle);
        author = findViewById(R.id.jAuthor);
        date = findViewById(R.id.jDate);
        summary = findViewById(R.id.jSummary);
        sample = findViewById(R.id.jSample);
        follow = findViewById(R.id.jFollow);
        type = findViewById(R.id.jType);
        url = findViewById(R.id.jUrl);
        recycler = findViewById(R.id.jKeyRecycler);
        addKey = findViewById(R.id.addKey);
        fieldViews = new View[]{title, author, date, summary, sample, follow, type, recycler};

        title.setText(j.title);
        author.setText(j.author);
        date.setText(j.date);
        summary.setText(j.summary);
        String sampleText = "" + j.sample;
        String followText = "" + j.follow;
        sample.setText(sampleText);
        follow.setText(followText);
        type.setText(j.type);
        url.setText(j.url);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        if(j.keywords == null)
            j.keywords = "";
        adapter = new KeywordAdapter(Arrays.asList(j.keywords.split(",")));
        recycler.setAdapter(adapter);

        AddKeywordButton button = new AddKeywordButton(this, keys, addKey, adapter, recycler);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connection con = ConnectionManager.getConnection();
                String[] fields = SqlManager.getFields(getApplicationContext(), fieldViews).split(SEPARATOR);
                String query = "update public.\"ReviewJournals\" SET ";
                for(String field : fields){
                    query += "\"" + field.split(PAIRER)[0] + "\" = ?, ";
                }
                query = query.substring(0, query.length() - 2) + " WHERE \"URL\" = ?";
                Log.d("UserJournalActivity", query);
                try {
                    PreparedStatement st = con.prepareStatement(query);
                    final String[] INT_FIELDS = {"SAMPLE", "FOLLOW"};
                    prepareLoop:
                    for(int i = 0; i < fields.length; i++){
                        for(String intField : INT_FIELDS){
                            if(fields[i].split(PAIRER)[0].equals(intField)){
                                st.setInt(i + 1, Integer.parseInt(fields[i].split(PAIRER)[1].trim()));
                                continue prepareLoop;
                            }
                            st.setString(i + 1, fields[i].split(PAIRER)[1].trim());
                        }
                    }
                    st.setString(fields.length + 1, url.getText().toString().trim());
                    st.executeUpdate();
                    st.close();
                }
                catch(SQLException e){
                    Log.e("UserJournalActivity", e.getMessage());
                }
                Toast.makeText(UserJournalActivity.this, "Saved User Submitted Journal Details", Toast.LENGTH_SHORT).show();
                startActivity(backToHome());
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
                Toast.makeText(UserJournalActivity.this, "Removed User Submitted Journal", Toast.LENGTH_SHORT).show();
                startActivity(backToHome());
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SqlManager.fieldsEmpty(fieldViews)) {
                    Connection connection = ConnectionManager.getConnection();
                    String query = "insert into public.\"Journals\"(\"TITLE\", \"AUTHOR\", \"DATE\", \"SUMMARY\", \"SAMPLE\", \"FOLLOW\", \"TYPE\", \"JOURNAL\", \"URL\", \"KEYWORDS\") " +
                            "VALUES(?,?,?,?,?,?,?,?,?,?)";
                    try {
                        PreparedStatement st = connection.prepareStatement(query);
                        st.setString(1, title.getText().toString().trim());
                        st.setString(2, author.getText().toString().trim());
                        st.setString(3, date.getText().toString().trim());
                        st.setString(4, summary.getText().toString().trim());
                        st.setInt(5, Integer.parseInt(sample.getText().toString().trim()));
                        st.setInt(6, Integer.parseInt(follow.getText().toString().trim()));
                        st.setString(7, type.getText().toString().trim());
                        st.setString(8, "null");
                        st.setString(9, url.getText().toString().trim());
                        st.setString(10, SqlManager.getKeywords(recycler));
                        st.executeUpdate();
                    } catch (SQLException e) {
                        Log.e("UserJournalActivity", e.getMessage());
                    }
                    remove();
                    Toast.makeText(UserJournalActivity.this, "Added User Submitted Journal to Database!", Toast.LENGTH_SHORT).show();
                    startActivity(backToHome());
                }
                else{
                    Toast.makeText(UserJournalActivity.this, "All Fields Must Be Filled in to Approve a New Journal", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void remove(){
        Connection con = ConnectionManager.getConnection();
        String query = "delete from public.\"ReviewJournals\" where \"URL\" = ?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, url.getText().toString().trim());
            st.executeUpdate();
            st.close();
        }
        catch(SQLException e){
            Log.e("UserJournalActivity", e.getMessage());
        }
    }
    private Intent backToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        return intent;
    }
}
