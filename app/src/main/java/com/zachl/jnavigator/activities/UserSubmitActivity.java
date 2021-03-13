package com.zachl.jnavigator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.zachl.jnavigator.objects.managers.ConnectionManager;
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
    private Button submit, bypass;
    private ImageButton addKey;
    private KeywordAdapter adapter;
    private List<String> keys = new ArrayList<>();
    private View[] fields;
    private Context context;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_user_submit);
        url = findViewById(R.id.urlFieldSub);
        title = findViewById(R.id.titleFieldSub);
        author = findViewById(R.id.authorFieldSub);
        summary = findViewById(R.id.summaryFieldSub);
        sample = findViewById(R.id.sampleFieldSub);
        follow = findViewById(R.id.followFieldSub);
        type = findViewById(R.id.typeFieldSub);
        date = findViewById(R.id.dateFieldSub);
        recycler = findViewById(R.id.keywords);
        submit = findViewById(R.id.submitSub);
        bypass = findViewById(R.id.submitBypass);
        addKey = findViewById(R.id.addKeySub);
        fields = new View[]{url, title, author, summary, sample, follow, type, date, recycler};
        adapter = new KeywordAdapter(keys);
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(adapter);

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
                        recycler.setAdapter(adapter);
                        window.dismiss();
                    }
                });
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                Connection con = ConnectionManager.getConnection();
                try {
                    String insert = "insert into public.\"ReviewJournals\"(";
                    String[] fields = getFields().split(SEPARATOR);
                    for(String field : fields){
                        insert += "\"" + field.split(PAIRER)[0] + "\", ";
                    }
                    insert = insert.substring(0, insert.length() - 2) + ") VALUES(";
                    for(String field : fields){
                        insert += "?,";
                    }
                    insert = insert.substring(0, insert.length() - 1) + ")";
                    PreparedStatement pst = con.prepareStatement(insert);
                    final String[] INT_FIELDS = {"SAMPLE", "FOLLOW"};
                    pstLoop:
                    for(int i = 0; i < fields.length; i++){
                        for(String intField : INT_FIELDS){
                            if(fields[i].equalsIgnoreCase(intField)){
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
                }
                catch(SQLException e){
                    Log.d("UserSubmitActivity", e.getMessage());
                }
            }
        });

        bypass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public String getFields(){
        String result = "";
        for(View view : fields){
            if(view instanceof TextView){
                String name = getResources().getResourceEntryName(view.getId()).replace("FieldSub", "");
                String content = ((TextView) view).getText().toString();
                if(!content.equalsIgnoreCase(""))
                    result += name.toUpperCase() + PAIRER + content + SEPARATOR;
            }
            else if(view instanceof RecyclerView){
                int count = ((RecyclerView) view).getChildCount();
                for(int i = 0; i < count; i++){
                    View child = ((ConstraintLayout)((RecyclerView) view).getChildAt(i)).getChildAt(0);
                    String content = ((TextView)child).getText().toString();
                    result += "KEYWORDS" + PAIRER + content + SEPARATOR;
                }
            }
        }
        Log.d("UserSubmitActivity", result);
        return result;
    }
}
