package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fanarver3.Plan;
import com.example.fanarver3.Specialist;
import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import java.sql.SQLException;

public class SPhomeScreen extends AppCompatActivity {

    ChipNavigationBar ChipNavigationBar1;
    Button viewPlan1,viewPlan2,viewPlan3;
    TextView plan_id1, plan_id2, plan_id3;

    LinearLayout LL1,LL2,LL3;
    String Specialist_user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LL1 = findViewById(R.id.layout1);
        LL2 = findViewById(R.id.layout2);
        LL3 = findViewById(R.id.layout3);
        // the sp has no plan in the list
        Specialist_user_ID = getIntent().getStringExtra("user");
        Log.d("debug9","the user id  "+ Specialist_user_ID);
        Specialist.filllist(Specialist_user_ID);

        // first check how many plan in the sp list
        switch (Specialist.PlanList.size()) {
            case 0:
                Log.d("debug8","plan array size= 0");
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
        ChipNavigationBar1 = findViewById(R.id.sp_menu);
        ChipNavigationBar1.setItemSelected(R.id.sp_nav_home, true);
        bottonMenu();


    }


    private void bottonMenu() {

        ChipNavigationBar1.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
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
                intent.putExtra("plan", (Parcelable) plan);
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
                intent.putExtra("plan", (Parcelable) plan);
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
                intent.putExtra("plan", (Parcelable) plan);
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
                intent.putExtra("plan", (Parcelable) plan);
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
                intent.putExtra("plan", (Parcelable) plan);
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
                intent.putExtra("plan", (Parcelable) plan);
                startActivity(intent);
            }
        });
    }


}