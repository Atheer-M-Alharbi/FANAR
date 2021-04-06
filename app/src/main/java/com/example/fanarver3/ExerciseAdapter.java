package com.example.fanarver3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fanarver3.TABMainPages.Parent_cummunity;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

        ArrayList<Exercise> ExerciseList;
    Resources resources;


    Context context;

        public ExerciseAdapter(Context context,ArrayList<Exercise> exercises_List) {
            this.ExerciseList = exercises_List;
            this.context = context;
             resources=new Resources();

        }

        @NonNull
        @Override
        public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.resources_menu,parent,false);
            return new ExerciseHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
            final Exercise exercise = ExerciseList.get(position);
            holder.Name.setText(exercise.getName());
            holder.Date.setText(exercise.getDiffcaluty());
            holder.Image.setImageResource(exercise.getImage());

        }

        @Override
        public int getItemCount() {
            return ExerciseList.size();
        }


        public class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            ImageView Image;
            TextView Name;
            TextView Date;

            public ExerciseHolder(@NonNull View itemView) {
                super(itemView);
                Image = itemView.findViewById(R.id.image_view_home);
                Name = itemView.findViewById(R.id.text_view_1);
                Date = itemView.findViewById(R.id.text_view_2);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position=getAdapterPosition();
                String url=ExerciseList.get(position).getURL();
                System.out.println(url);
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);

            }
        }

    }
