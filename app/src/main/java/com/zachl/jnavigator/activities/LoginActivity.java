package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.objects.entities.Journal;
import com.zachl.jnavigator.objects.entities.User;
import com.zachl.jnavigator.objects.managers.ConnectionManager;
import com.zachl.jnavigator.objects.managers.PreferenceManager;
import com.zachl.jnavigator.objects.runnables.ConnectionRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    private TextView name, email, password;
    private Button signin;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        PreferenceManager.setPrefs(getPreferences(MODE_PRIVATE));
        User user = PreferenceManager.getUser();
        if(user.signedIn){
            goToMainActivity(user.name);
            return;
        }
        name = findViewById(R.id.nameField);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);
        signin = findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Connection con = ConnectionManager.getConnection();
                try{
                    String query = "select * from public.\"Users\" where \"EMAIL\" = ?";
                    Log.d("LoginActivity", query);
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, email.getText().toString().trim());
                    ResultSet rs = st.executeQuery();
                    if(rs.next()){
                        if(password.getText().toString().equals(rs.getString("PASSWORD"))){
                            Log.d("LoginActivity", "Found User!");
                            goToMainActivity();
                            return;
                        }
                    }
                    Log.d("LoginActivity", "Registering New User!");
                    String insert = "insert into public.\"Users\"(\"NAME\", \"EMAIL\", \"PASSWORD\") VALUES(?,?,?)";
                    PreparedStatement pst = con.prepareStatement(insert);
                    pst.setString(1, name.getText().toString());
                    pst.setString(2, email.getText().toString());
                    pst.setString(3, password.getText().toString());
                    pst.executeUpdate();
                    pst.close();
                    PreferenceManager.saveUser(new User());
                    goToMainActivity();
                }
                catch(SQLException ex){
                    Log.e("LoginActivity", "SQL Query Error: " + ex.getMessage());
                }
            }
        });
    }

    public void goToMainActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("userName", name.getText().toString().trim());
        startActivity(intent);
    }
    public void goToMainActivity(String user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("userName", user);
        startActivity(intent);
    }
}
