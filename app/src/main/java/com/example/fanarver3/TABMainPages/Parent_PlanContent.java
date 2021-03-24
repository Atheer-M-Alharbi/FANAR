package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fanarver3.Home;
import com.example.fanarver3.R;
import com.example.fanarver3.SPscreen.SP_ViewPlanContent_Resource;
import com.example.fanarver3.SPscreen.SP_ViewPlanContent_days;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.example.fanarver3.Plan;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class Parent_PlanContent extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    android.widget.Button day1;
    android.widget.Button day2;
    android.widget.Button day3;
    android.widget.Button day4;
    Button sendFeedBackBotton;
    Button Done;
    TextView evaluationHINT;
    Plan Plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__plan_content);

        // RECIVE PASSED OBJECT
        Plan = getIntent().getParcelableExtra("plan");


        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_plan, true);
        bottonMenu();

        //
        day1.findViewById(R.id.PlanDay1);
        day2.findViewById(R.id.PlanDay2);
        day3.findViewById(R.id.PlanDay3);
        day4.findViewById(R.id.PlanDay4);
        evaluationHINT.findViewById(R.id.sendFeedBackTEXT);
        sendFeedBackBotton.findViewById(R.id.sendEVALUTIONBotton);
        Done.findViewById(R.id.CompleteBotton);

        // set send feedback botton visability to gone
        sendFeedBackBotton.setVisibility(View.GONE);
        evaluationHINT.setVisibility(View.GONE);

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Done.setVisibility(View.GONE);
                sendFeedBackBotton.setVisibility(View.VISIBLE);
                evaluationHINT.setVisibility(View.VISIBLE);
            }
        });

        sendFeedBackBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_PlanContent.this, EvaluatePlan.class);
                intent.putExtra("plan", (Parcelable) Plan);
                startActivity(intent);
            }
        });

        try {
            distrbutePlan();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void distrbutePlan() throws SQLException {

        ResultSet Category;
        ArrayList skill_1 = new ArrayList();
        ArrayList skill_2 = new ArrayList();

        ArrayList Allskills = Plan.getSelectedSkillsList();
        int numberOfSkille = Allskills.size();


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
                    Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                    intent.putExtra("skill1", skill_1.get(0).toString());
                    intent.putExtra("skill2", skill_2.get(0).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                    intent.putExtra("skill1", skill_1.get(1).toString());
                    intent.putExtra("skill2", skill_2.get(1).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
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
                        Intent intent = new Intent(Parent_PlanContent.this, SP_ViewPlanContent_Resource.class);
                        intent.putExtra("skill1", skill_1.get(3).toString());
                        intent.putExtra("skill2", skill_2.get(3).toString());
                        intent.putExtra("numOFskill", numberOfSkille);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(Parent_PlanContent.this, SP_ViewPlanContent_Resource.class);
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
                    Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                    intent.putExtra("skill", skill.get(0).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                    intent.putExtra("skill", skill.get(1).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                    intent.putExtra("skill", skill.get(2).toString());
                    intent.putExtra("numOFskill",numberOfSkille);
                    startActivity(intent);

                }
            });

            day4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(skill.get(3) != null) {
                        Intent intent = new Intent(Parent_PlanContent.this, SP_ViewPlanContent_Resource.class);
                        intent.putExtra("skill1", skill.get(3).toString());
                        intent.putExtra("numOFskill", numberOfSkille);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(Parent_PlanContent.this, SP_ViewPlanContent_Resource.class);
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
                Activity InWhichActivity = null;
                switch (i) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_plan:
                        startActivity(new Intent(getApplicationContext(), parent_Plan.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_com:
                        startActivity(new Intent(getApplicationContext(), Parent_cummunity.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_chatBot:
                        startActivity(new Intent(getApplicationContext(), Parent_chatBot.class));
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
    }

}