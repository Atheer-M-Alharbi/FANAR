package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fanarver3.Home;
import com.example.fanarver3.Plan;
import com.example.fanarver3.Specialist;
import com.example.fanarver3.R;
import com.example.fanarver3.SPscreen.sp_View_plan;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SPhomeScreen extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    Button viewPlan1;
    Button viewPlan2;
    Button viewPlan3;
    TextView plan_id1, plan_id2, plan_id3;

    LinearLayout LL1;
    LinearLayout LL2 ;
    LinearLayout LL3;
    TextView userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

          LL1 = findViewById(R.id.layout1);
          LL2 = findViewById(R.id.layout2);
          LL3 = findViewById(R.id.layout3);
          userid = findViewById(R.id.userID);

        Specialist.filllist(userid.getText().toString());

        // first check how many plan in the sp list
        switch (Specialist.PlanList.size()) {
            case 0:
                // the sp has no plan in the list
                LL1.setVisibility(View.GONE);
                LL2.setVisibility(View.GONE);
                LL3.setVisibility(View.GONE);
                break;
            case 1:
                case1();
                break;
            case 2:
                case2();
                break;
            case 3:
                case3();
                break;
            default:
                // then show activity
                setContentView(R.layout.activity_s_phome_screen);

        }

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_home, true);
        bottonMenu();


    }


    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i) {
                    case R.id.sp_nav_Community:
                        startActivity(new Intent(getApplicationContext(), sp_CommunityScreen.class));
                        overridePendingTransition(0, 0);
                        break;

                }

            }
        });
    }

    private void case1() {

        LL2.setVisibility(View.GONE);
        LL3.setVisibility(View.GONE);
        // then show activity
        setContentView(R.layout.activity_s_phome_screen);
        // lastly set Listener for button
        viewPlan1 = findViewById(R.id.viewPlan1Botton);
        viewPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan plan = Specialist.PlanList.get(0);
                plan_id1.findViewById(R.id.Plan1);
                plan_id1.append(" " + plan.getPlanID());
                // IF THERE IS ERROR MOVE THIS INSIDE THE SP_VIEW_PLAN ACTIVITY
                try {
                    sp_View_plan.fetchchildInfro(plan.getPlanID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(SPhomeScreen.this, sp_View_plan.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
            }
        });
    }

    private void case2() {
        LL3.setVisibility(View.GONE);
        // then show activity
        setContentView(R.layout.activity_s_phome_screen);
        // lastly set Listener for button
        viewPlan1 = findViewById(R.id.viewPlan1Botton);
        viewPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan plan = Specialist.PlanList.get(0);
                plan_id1.findViewById(R.id.Plan1);
                plan_id1.append(" " + plan.getPlanID());
                // IF THERE IS ERROR MOVE THIS INSIDE THE SP_VIEW_PLAN ACTIVITY
                try {
                    sp_View_plan.fetchchildInfro(plan.getPlanID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(SPhomeScreen.this, sp_View_plan.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
            }
        });

        viewPlan2 = findViewById(R.id.viewPlan2Botton);
        viewPlan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan plan = Specialist.PlanList.get(1);
                plan_id2.findViewById(R.id.Plan2);
                plan_id2.append(" " + plan.getPlanID());
                // IF THERE IS ERROR MOVE THIS INSIDE THE SP_VIEW_PLAN ACTIVITY
                try {
                    sp_View_plan.fetchchildInfro(plan.getPlanID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(SPhomeScreen.this, sp_View_plan.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
            }
        });

    }

    private void case3() {

        // then show activity
        setContentView(R.layout.activity_s_phome_screen);
        // lastly set Listener for button
        viewPlan1 = findViewById(R.id.viewPlan1Botton);
        viewPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan plan = Specialist.PlanList.get(0);
                plan_id1.findViewById(R.id.Plan1);
                plan_id1.append(" " + plan.getPlanID());
                // IF THERE IS ERROR MOVE THIS INSIDE THE SP_VIEW_PLAN ACTIVITY
                try {
                    sp_View_plan.fetchchildInfro(plan.getPlanID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(SPhomeScreen.this, sp_View_plan.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
            }
        });

        viewPlan2 = findViewById(R.id.viewPlan2Botton);
        viewPlan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan plan = Specialist.PlanList.get(1);
                plan_id2.findViewById(R.id.Plan2);
                plan_id2.append(" " + plan.getPlanID());
                // IF THERE IS ERROR MOVE THIS INSIDE THE SP_VIEW_PLAN ACTIVITY
                try {
                    sp_View_plan.fetchchildInfro(plan.getPlanID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(SPhomeScreen.this, sp_View_plan.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
            }
        });

        viewPlan3 = findViewById(R.id.viewPlan3Botton);
        viewPlan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Plan plan = Specialist.PlanList.get(2);
                plan_id3.findViewById(R.id.Plan3);
                plan_id3.append(" " + plan.getPlanID());
                // IF THERE IS ERROR MOVE THIS INSIDE THE SP_VIEW_PLAN ACTIVITY
                try {
                    sp_View_plan.fetchchildInfro(plan.getPlanID());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent intent = new Intent(SPhomeScreen.this, sp_View_plan.class);
                intent.putExtra("plan", plan);
                startActivity(intent);
            }
        });
    }


}