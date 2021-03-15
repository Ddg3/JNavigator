package com.zachl.jnavigator.views;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;

import com.zachl.jnavigator.R;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AddKeywordButton {
    private static KeywordAdapter adapter;
    public AddKeywordButton(final Context context, final List<String> keys, final ImageButton addKey, KeywordAdapter adapter, final RecyclerView recycler){
        AddKeywordButton.adapter = adapter;
        addKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
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
                        AddKeywordButton.adapter = new KeywordAdapter(keys);
                        recycler.setAdapter(AddKeywordButton.adapter);
                        window.dismiss();
                    }
                });
            }
        });
    }
}
