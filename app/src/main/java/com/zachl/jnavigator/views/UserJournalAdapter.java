package com.zachl.jnavigator.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.zachl.jnavigator.R;
import com.zachl.jnavigator.activities.UserJournalActivity;
import com.zachl.jnavigator.objects.entities.Journal;

import java.util.List;

public class UserJournalAdapter extends RecyclerView.Adapter<UserJournalAdapter.ViewHolder> {
    private Context context;
    private List<Journal> journals;
    public UserJournalAdapter(Context context, List<Journal> journals){
        this.context = context;
        this.journals = journals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_journal, parent, false);
        return new UserJournalAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Journal j = journals.get(position);
        holder.url.setText(j.url);
        holder.user.setText(j.user);
        holder.constraints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserJournalActivity.class);
                intent.putExtra("journalUrl", j.url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView url, user;
        ConstraintLayout constraints;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.recyclerUser);
            url = itemView.findViewById(R.id.recyclerUrl);
            constraints = itemView.findViewById(R.id.recyclerConstraint);
        }
    }
}
