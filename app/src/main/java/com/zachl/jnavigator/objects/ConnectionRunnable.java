package com.zachl.jnavigator.objects;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionRunnable implements Runnable {
    public Connection connection;
    @Override
    public void run() {
        String url = "jdbc:postgresql://10.0.2.2:5432/JNavigatorDB";
        String user = "postgres";
        String pw = "1470_Project!";
        try{
            Connection c = DriverManager.getConnection(url, user, pw);
            Log.d("JournalResults", "Connected!");
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT VERSION()");
            Log.d("JournalResults", "Made Query!");
            if(rs.next()){
                Log.d("JournalResults", rs.getString(1));
            }
            connection = c;
            return;
        }
        catch(SQLException e){
            Log.e("JournalResults", "SQL Connection Error: " + e.getMessage());
            connection = null;
            return;
        }
    }
}
