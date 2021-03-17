package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.fanarver3.Home;
import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.ResultSet;

public class SP_ViewPlanContent_Resource extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    android.widget.Button EX1;
    android.widget.Button EX2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_s_p__view_plan_content__resource);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_home,true);
        bottonMenu();

        // RECIVE PASSED VALUE
        int numOfSkill = Integer.parseInt(getIntent().getStringExtra("numOFskill"));

        if (numOfSkill>=1) {
            EX1.findViewById(R.id.sp_viewEX1Botton);
            EX1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ExerciseID = getIntent().getParcelableExtra("skill1");
                    ResultSet Exercise = Home.sqlConn("select Exercise from Resources where ExerciseID ="+ExerciseID+";");
                    ViewResources(Exercise.toString());
                }
            });


            EX2.findViewById(R.id.sp_viewEX2Botton);
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

            EX1.findViewById(R.id.sp_viewEX1Botton);
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

                switch(i){
                    case R.id.sp_nav_Community:
                        startActivity(new Intent(getApplicationContext(),sp_CommunityScreen.class));
                        overridePendingTransition(0,0);
                        break;

                }

            }
        }  );
    }


}