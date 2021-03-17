package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.fanarver3.Home;
import com.example.fanarver3.Plan;
import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.net.URL;
import java.sql.ResultSet;

public class Parent_PlanResourse extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    android.widget.Button EX1;
    android.widget.Button EX2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__plan_resourse);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_plan,true);
        bottonMenu();


        // RECIVE PASSED VALUE
        int numOfSkill = Integer.parseInt(getIntent().getStringExtra("numOFskill"));

        if (numOfSkill>=1) {
            EX1.findViewById(R.id.p_viewEX1Botton);
            EX1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ExerciseID = getIntent().getParcelableExtra("skill1");
                    ResultSet Exercise = Home.sqlConn("select Exercise from Resources where ExerciseID ="+ExerciseID+";");
                    ViewResources(Exercise.toString());
                }
            });


            EX2.findViewById(R.id.p_viewEX2Botton);
            EX2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ExerciseID = getIntent().getParcelableExtra("skill2");
                    ResultSet Exercise = Home.sqlConn("select Exercise from Resources where ExerciseID ="+ExerciseID+";");
                    ViewResources(Exercise.toString());
                }
            });
        }


        if (numOfSkill<1) {
            EX2.setVisibility(View.GONE);

            EX1.findViewById(R.id.p_viewEX1Botton);
            EX1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ExerciseID = getIntent().getParcelableExtra("skill");
                    ResultSet Exercise = Home.sqlConn("select Exercise from Resources where ExerciseID ="+ExerciseID+";");
                    ViewResources(Exercise.toString());
                }
            });
        }

    }

    public void ViewResources(String Resourceurl){

        Uri url = Uri.parse(Resourceurl);
        Intent openWebPage = new Intent(Intent.ACTION_VIEW, url );
        startActivity(openWebPage);

    };


    private void bottonMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Activity InWhichActivity = null;
                switch(i){
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_plan:
                        startActivity(new Intent(getApplicationContext(),parent_Plan.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_com:
                        startActivity(new Intent(getApplicationContext(),Parent_cummunity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_chatBot:
                        startActivity(new Intent(getApplicationContext(),Parent_chatBot.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        } );
    }

}