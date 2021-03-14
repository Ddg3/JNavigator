package com.zachl.jnavigator.objects.managers;

import android.content.Context;
import android.os.StrictMode;

import com.zachl.jnavigator.objects.runnables.ConnectionRunnable;

import java.sql.Connection;

public class ConnectionManager {
    public static Connection getConnection() {
        if(connection == null){
            connection = connect();
        }
        return connection;
    }

    public static Connection connect(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConnectionRunnable connect = new ConnectionRunnable();
        connect.run();
        return connect.connection;
    }
    private static Connection connection;
}
