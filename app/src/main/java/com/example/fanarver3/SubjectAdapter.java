package com.example.fanarver3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {

    ArrayList<Subject> SubList;
    Context context;

    public SubjectAdapter(Context context,ArrayList<Subject> Sub_List) {
        this.SubList = Sub_List;
        this.context = context;

    }

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_subject,parent,false);
        return new SubjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectHolder holder, int position) {
        final Subject subject = SubList.get(position);
        holder.Titel.setText(subject.getSubjectTitle());
        holder.Description.setText(subject.getSubjectDescription());
        holder.author.setText(subject.getAuthorName());

    }

    @Override
    public int getItemCount() {
        return SubList.size();
    }


    public class SubjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Titel;
        TextView Description;
        TextView author;

        public SubjectHolder(@NonNull View itemView) {
            super(itemView);
            Titel = itemView.findViewById(R.id.Subject_title);
            Description = itemView.findViewById(R.id.description);
            author=itemView.findViewById(R.id.Author_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index=getAdapterPosition();
            String title=SubList.get(index).getSubjectTitle();
            String sub=SubList.get(index).getSubject();

            Intent intent = new Intent(context, view_subject.class);
            intent.putExtra("title",title);
            intent.putExtra("sub",sub);
            context.startActivity(intent);


        }
    }

}
