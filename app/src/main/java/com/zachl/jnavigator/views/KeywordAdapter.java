package com.zachl.jnavigator.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zachl.jnavigator.R;

import org.w3c.dom.Text;

import java.util.List;

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.ViewHolder> {
    List<String> keywords;

    public KeywordAdapter(List<String> keywords){
        this.keywords = keywords;
    }
    @NonNull
    @Override
    public KeywordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_layout, parent, false);
        return new KeywordAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String key = " " + keywords.get(position) + " ";
        holder.key.setText(key);
    }

    @Override
    public int getItemCount() {
        return keywords.size();
    }

    public static class ViewHolder extends JournalAdapter.ViewHolder{
        TextView key;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.keywordText);
        }
    }
}
