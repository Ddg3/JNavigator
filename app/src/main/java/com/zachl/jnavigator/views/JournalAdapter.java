package com.zachl.jnavigator.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.activities.JournalActivity;
import com.zachl.jnavigator.objects.entities.Journal;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {
    private List<Object> journals;
    private Context context;
    private boolean bookmarksOnly;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, author;
        ImageButton bookmark;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            bookmark = itemView.findViewById(R.id.bookmark);
            layout = itemView.findViewById(R.id.cLayout);
        }
    }
    public JournalAdapter(Context context, List<Object> journals, boolean bookmarksOnly){
        this.journals = journals;
        this.context = context;
        this.bookmarksOnly = bookmarksOnly;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_result_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Journal journal = (Journal)journals.get(position);
        holder.title.setText(journal.title);
        holder.author.setText(journal.author);
        BookmarkButton button = new BookmarkButton(context, holder.bookmark, journal);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JournalActivity.class);
                intent.putExtra("journalUrl", journal.url);
                intent.putExtra("bookmarksOnly", bookmarksOnly);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }
}
