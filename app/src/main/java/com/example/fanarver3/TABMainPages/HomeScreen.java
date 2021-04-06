package com.example.fanarver3.TABMainPages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.fanarver3.Exercise;
import com.example.fanarver3.ExerciseAdapter;
import com.example.fanarver3.Home;
import com.example.fanarver3.R;
import com.example.fanarver3.Resources;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    ExerciseAdapter exerciseAdapter;
    Resources resources;
    ArrayList<Exercise> exercises_List;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resources=new Resources();


        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_home, true);
        bottonMenu();
        String q = "select skillName ,PlanLevel, Exercise from Resources;";
        ResultSet rs = Home.sqlConn(q);
         exercises_List = new ArrayList<Exercise>();

        try {
            while (rs.next()) {
                String skillName=rs.getString("skillName");
                String url=rs.getString("Exercise");
                String PlanLevel=rs.getString("PlanLevel");

                if(PlanLevel.equals("0")){
                    exercises_List.add(new Exercise(skillName, "Difficulty level: LOW", R.drawable.ic_human_rights,url));
                }
                else if(PlanLevel.equals("1")){
                    exercises_List.add(new Exercise(skillName, "Difficulty level: MEDIUM", R.drawable.ic_human_rights,url));

                }
                else if(PlanLevel.equals("2")){
                    exercises_List.add(new Exercise(skillName, "Difficulty level: HARD", R.drawable.ic_human_rights,url));
                }
                else if(PlanLevel.equals("3")){
                    exercises_List.add(new Exercise(skillName, "Difficulty level: ADVANCED", R.drawable.ic_human_rights,url));

                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

         exerciseAdapter = new ExerciseAdapter(this,exercises_List);
        recyclerView.setAdapter(exerciseAdapter);
    }


    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Activity InWhichActivity = null;
                Intent intent;
                String user_ID = getIntent().getStringExtra("user");
                System.out.println("home ** user_ID"+user_ID);

                switch (i) {
                    case R.id.nav_plan:
                        intent = new Intent(getApplicationContext(), parent_Plan.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_com:
                        intent = new Intent(getApplicationContext(), Parent_cummunity.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_chatBot:
                        intent = new Intent(getApplicationContext(), Parent_chatBot.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
    }


}