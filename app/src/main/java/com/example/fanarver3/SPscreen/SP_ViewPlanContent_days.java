package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.fanarver3.Home;
import com.example.fanarver3.Plan;
import com.example.fanarver3.R;
import com.example.fanarver3.TABMainPages.Parent_PlanContent;
import com.example.fanarver3.TABMainPages.Parent_PlanResourse;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SP_ViewPlanContent_days extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    Button viewResource;
    android.widget.Button day1;
    android.widget.Button day2;
    android.widget.Button day3;
    android.widget.Button day4;
    Plan Plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_s_p__view_plan_content_days);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_home,true);
        bottonMenu();

        day1.findViewById(R.id.sp_viewday1Botton);
        day2.findViewById(R.id.sp_viewday2Botton);
        day3.findViewById(R.id.sp_viewday3Botton);
        day4.findViewById(R.id.sp_viewday4Botton);

        // RECIVE PASSED OBJECT
        Plan = getIntent().getParcelableExtra("plan");

        try {
            distrbutePlan();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void distrbutePlan() throws SQLException {

        ResultSet Category;
        ArrayList skill_1 = new ArrayList();
        ArrayList skill_2 = new ArrayList();

        ArrayList AllResourse = Plan.getSelectedSkillsList();
        int numberOfSkille = AllResourse.size();


        if (numberOfSkille >= 1) {
            // more than two skill.
            // show the first two skills only
            for (int i = 0; i <= 1; i++) {
                Category = Home.sqlConn("Select ExerciseID from Exercise where PlanID =" + Plan.getPlanID() + "AND Category =" + Plan.getSelectedSkillsList().get(i) + ";");
                if (Category.getString("Category").equals(Plan.getSelectedSkillsList().get(0))) {
                    while (Category.next()) {
                        skill_1.add(Category.getString("ExerciseID"));
                    }
                }
                if (Category.getString("Category").equals(Plan.getSelectedSkillsList().get(1))) {
                    while (Category.next()) {
                        skill_2.add(Category.getString("ExerciseID"));
                    }
                }


            }

            day1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                    intent.putExtra("skill1", skill_1.get(0).toString());
                    intent.putExtra("skill2", skill_2.get(0).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                    intent.putExtra("skill1", skill_1.get(1).toString());
                    intent.putExtra("skill2", skill_2.get(1).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                    intent.putExtra("skill1", skill_1.get(2).toString());
                    intent.putExtra("skill2", skill_2.get(2).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(skill_1.get(3) != null) {
                        Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                        intent.putExtra("skill1", skill_1.get(3).toString());
                        intent.putExtra("skill2", skill_2.get(3).toString());
                        intent.putExtra("numOFskill", numberOfSkille);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                        intent.putExtra("skill1", skill_1.get(0).toString());
                        intent.putExtra("skill2", skill_2.get(0).toString());
                        intent.putExtra("numOFskill", numberOfSkille);
                        startActivity(intent);
                    }
                }
            });

        }

        if (numberOfSkille < 1) {

            // only one skill left in this level
            // one Exercise each day
            ArrayList skill = new ArrayList();
            Category = Home.sqlConn("Select ExerciseID from Exercise where PlanID =" + Plan.getPlanID() + "AND Category =" + Plan.getSelectedSkillsList().get(0) + ";");
            while (Category.next()) {
                skill.add(Category.getString("ExerciseID"));
            }

            day1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                    intent.putExtra("skill", skill.get(0).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                    intent.putExtra("skill", skill.get(1).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                    intent.putExtra("skill", skill.get(2).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(skill.get(3) != null) {
                        Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                        intent.putExtra("skill1", skill.get(3).toString());
                        intent.putExtra("numOFskill", numberOfSkille);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class);
                        intent.putExtra("skill1", skill.get(0).toString());
                        intent.putExtra("numOFskill", numberOfSkille);
                        startActivity(intent);
                    }

                }
            });

        }

    }

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