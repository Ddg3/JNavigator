package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.ConnectionRunnable;
import com.zachl.jnavigator.objects.Journal;
import com.zachl.jnavigator.objects.JournalManager;
import com.zachl.jnavigator.views.JournalAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JournalResults extends AppCompatActivity {
    private static final String SEPARATOR = "==", PAIRER = "::";
    private RecyclerView recycler;
    private Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fields = getIntent().getStringExtra("fields");
        setContentView(R.layout.activity_journal_results);
        recycler = findViewById(R.id.recycler);
        List<Object> journals = search(fields);
        JournalManager.setResultJournals(journals);
        JournalAdapter adapter = new JournalAdapter(this, journals);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public Connection connect(){
        ConnectionRunnable connect = new ConnectionRunnable();
        runOnUiThread(connect);
        return connect.connection;
    }

    private List<Object> search(String concat){
        List<Object> journals= new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        con = connect();
        String[] fields = concat.split(SEPARATOR);
        try{
            String query = "select * from public.\"Journals\" where"; //where \"AUTHOR\" like '%' || ? || '%';"
            for(String field : fields){
                query += " \"" + field.split(PAIRER)[0].trim() + "\" like '%' || ? || '%' AND";
            }
            query = query.substring(0, query.length() - 4);
            Log.d("JournalResults", query);
            PreparedStatement st = con.prepareStatement(query);
            for(int i = 0; i < fields.length; i++){
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
