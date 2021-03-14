package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.managers.ConnectionManager;
import com.zachl.jnavigator.objects.runnables.ConnectionRunnable;
import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.managers.JournalManager;
import com.zachl.jnavigator.views.JournalAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JournalResults extends AppCompatActivity {
    private static final String SEPARATOR = "==", PAIRER = "::";
    private RecyclerView recycler;
    private Connection con;
    private List<Object> journals;
    private boolean bookmarksOnly;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fields = getIntent().getStringExtra("fields");
        setContentView(R.layout.activity_journal_results);
        bookmarksOnly = getIntent().getBooleanExtra("bookmarksOnly", false);
        recycler = findViewById(R.id.recycler);
        if(!bookmarksOnly) {
            journals = search(fields);
            JournalManager.setResultJournals(journals);
        }
        else{
            journals = JournalManager.getBookmarks();
        }
        JournalAdapter adapter = new JournalAdapter(this, journals, bookmarksOnly);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        super.onResume();
        recycler = findViewById(R.id.recycler);
        if(!bookmarksOnly) {
            journals = JournalManager.getResultJournals();
        }
        else{
            journals = JournalManager.getBookmarks();
        }
        JournalAdapter adapter = new JournalAdapter(this, journals, bookmarksOnly);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    private List<Object> search(String concat){
        List<Object> journals= new ArrayList<>();
        con = ConnectionManager.getConnection();
        String[] fields = concat.split(SEPARATOR);
        final String[] INT_FIELDS = {"SAMPLE", "FOLLOW"};
        try{
            String query = "select * from public.\"Journals\" where"; //where \"AUTHOR\" like '%' || ? || '%';"
            fieldLoop:
            for(String field : fields){
                for(String intField : INT_FIELDS) {
                    if(field.split(PAIRER)[0].trim().contains(intField)) {
                        if(field.split(PAIRER)[0].contains("MAX"))
                            query += " \"" + field.split(PAIRER)[0].trim().replace("MAX", "") + "\" <= ? AND";
                        else
                            query += " \"" + field.split(PAIRER)[0].trim().replace("MIN", "") + "\" >= ? AND";
                        continue fieldLoop;
                    }
                }
                query += " \"" + field.split(PAIRER)[0].trim() + "\" like '%' || ? || '%' AND";
            }
            query = query.substring(0, query.length() - 4);
            Log.d("JournalResults", query);
            PreparedStatement st = con.prepareStatement(query);
            preparedLoop:
            for(int i = 0; i < fields.length; i++){
                for(String intField : INT_FIELDS) {
                    if (fields[i].split(PAIRER)[0].trim().contains(intField)) {
                        st.setInt(i + 1, Integer.parseInt(fields[i].split(PAIRER)[1].trim()));
                        continue preparedLoop;
                    }
                }
                st.setString(i + 1, fields[i].split(PAIRER)[1].trim());
            }
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Journal journal = new Journal();
                journal.title = rs.getString("TITLE");
                Log.d("JournalResults", journal.title);
                journal.author = rs.getString("AUTHOR");
                journal.summary = rs.getString("SUMMARY");
                journal.date = rs.getString("DATE");
                journal.url = rs.getString("URL");
                journal.journal = rs.getString("JOURNAL");
                journal.keywords = rs.getString("KEYWORDS");
                journal.sample = rs.getInt("SAMPLE");
                journal.follow = rs.getInt("FOLLOW");
                journal.type = rs.getString("TYPE");
                journals.add(journal);
            }
            return journals;
        }
        catch(SQLException ex){
            Log.e("JournalResults", "SQL Query Error: " + ex.getMessage());
        }
        return null;
    }
}
