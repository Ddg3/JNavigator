package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.managers.ConnectionManager;
import com.zachl.jnavigator.objects.managers.JournalManager;
import com.zachl.jnavigator.views.UserJournalAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminReviewActivity extends AppCompatActivity {
    private RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_review);
        recycler = findViewById(R.id.adminRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        UserJournalAdapter adapter = new UserJournalAdapter(this, getUserJournals());
        recycler.setAdapter(adapter);
    }
    private List<Journal> getUserJournals(){
        List<Object> objects = new ArrayList<>();
        List<Journal> journals = new ArrayList<>();
        Connection con = ConnectionManager.getConnection();
        String query = "select * from public.\"ReviewJournals\"";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Journal j = new Journal();
                j.url = rs.getString("URL");
                j.title = rs.getString("TITLE");
                j.author = rs.getString("AUTHOR");
                j.date = rs.getString("DATE");
                j.summary = rs.getString("SUMMARy");
                j.type = rs.getString("TYPE");
                j.sample = rs.getInt("SAMPLE");
                j.follow = rs.getInt("FOLLOW");
                j.user = rs.getString("USER");
                journals.add(j);
                objects.add(j);
            };
            JournalManager.setResultJournals(objects);
        }
        catch(SQLException e){
            Log.e("AdminReviewActivity", e.getMessage());
        }
        return journals;
    }
}
