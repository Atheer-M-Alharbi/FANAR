package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fanarver3.Plan;
import com.example.fanarver3.R;
import com.example.fanarver3.Specialist;
import com.example.fanarver3.TABMainPages.parent_Plan;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sp_View_plan extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    Button rejectPlan, ApproveBotton, sendCorretlevelBotton, viewPlancontent;
    TextView currentleveltect;
    static TextView childinfo;
    AutoCompleteTextView correct_levelm1;
    ScrollView rejectView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sp__view_plan);


        //// get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_home, true);
        bottonMenu();
        FillPlanLEVEL(this);
        ////

        // RECIVE PASSED OBJECT
        Plan thisPlan = getIntent().getParcelableExtra("plan");
        // RECIVE PASSED VALUE
        int Planid = Integer.parseInt(getIntent().getStringExtra("planid"));


        //// FIND COMMPONENET BY ID
        rejectPlan = findViewById(R.id.RejectBotton);
        ApproveBotton = findViewById(R.id.ApproveBotton);
        sendCorretlevelBotton = findViewById(R.id.sendCorretlevelBotton);
        correct_levelm1 = findViewById(R.id.correct_levelSelected);
        rejectView = findViewById(R.id.rejectview);
        viewPlancontent = findViewById(R.id.viewPlan3Botton);
        childinfo = findViewById(R.id.ChildINFO);
        ////


        //set button listener FOR REJECT BOTTON
        rejectPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set reject & aprove botton  visability as gone
                // make rejectPlan view  ( select right level) as visiably
                rejectPlan.setVisibility(view.GONE);
                ApproveBotton.setVisibility(view.GONE);
                sendCorretlevelBotton.setVisibility(View.GONE);
                rejectView.setVisibility(view.VISIBLE);

                if (correct_levelm1.isSelected()) {
                    sendCorretlevelBotton.setVisibility(View.VISIBLE);
                    sendCorretlevelBotton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // thisPlan.AdjustDelvlopmentPlan();
                            int level;
                            switch (correct_levelm1.getText().toString()) {
                                case "Easy":
                                    level = 0;
                                case "Average":
                                    level = 1;
                                case "High":
                                    level = 2;
                                case "advanced":
                                    level = 3;
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + correct_levelm1.getText().toString());
                            }
                            parent_Plan.parentPLAN.PlanLevel(level, thisPlan);
                            Intent intent = new Intent(sp_View_plan.this, SPhomeScreen.class);
                            startActivity(intent);

                        }
                    });

                }// end of if statment


            }// end of onclick
        });


        //set button listener FOR approve BOTTON (DONE)
        ApproveBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisPlan.PlanState = true;
                // remove this plan from sp list
                Specialist.PlanList.remove(thisPlan);
                // change plan state to true(approve)
                parent_Plan.obsInt.set(true);
                Intent intent = new Intent(sp_View_plan.this, SPhomeScreen.class);
                startActivity(intent);

            }
        });

        // ... need to work on this but wait for the sp replay..(-_-)
        viewPlancontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sp_View_plan.this, SP_ViewPlanContent_days.class);
                intent.putExtra("plan", thisPlan); //  pass object to the next activity
                startActivity(intent);
            }
        });


    }

    @SuppressLint("SetTextI18n")
    public static void fetchchildInfro(int id) {

        //var's for database connection
        Connection CONNECTION = null;
        String ip = "192.168.1.21";
        String userName = "FANAR";
        String Password = "qwer";
        String Port = "1433";
        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String DataBase = "FANAR";
        String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;

        // fetch from dataset
        // establish ms sql server database connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ResultSet resultSet_childInfo;
        String test = null;

        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

            if (CONNECTION != null) {
                Statement statement = null;

                try {
                    statement = CONNECTION.createStatement();
                    resultSet_childInfo = statement.executeQuery("SELECT ChildName,autismLevel,ChildAge,IqLevel,Perception FROM Child WHERE ChildID =" + id + ";");

                    String ChildName = resultSet_childInfo.getString("ChildName");
                    int autismLevel = resultSet_childInfo.getInt("autismLevel");
                    int IqLevel = resultSet_childInfo.getInt("IqLevel");
                    int ChildAge = resultSet_childInfo.getInt("ChildAge");
                    int Perception = resultSet_childInfo.getInt("Perception");
                    childinfo.setText("ChildName: " + ChildName + "\n autismLevel" + autismLevel + "\nChildAge" + ChildAge + "\n" +
                            "IqLevel" + IqLevel + "\nPerception" + Perception + "\n");

                    //end of second try
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                test = "con is null";
            }

            //end of first try
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            test = "error!!!!";
            // show textbox show error
        }


    }

    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.sp_nav_home:
                        startActivity(new Intent(getApplicationContext(), SPhomeScreen.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.sp_nav_Community:
                        startActivity(new Intent(getApplicationContext(), sp_CommunityScreen.class));
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
    }

    public void FillPlanLEVEL(sp_View_plan view) {

        String[] AutismLevel = {"  ", "Easy", "Average", "High", "advanced"};
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, AutismLevel);
        correct_levelm1.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        correct_levelm1.setAdapter(arrayAdaptermenu1);

    }
}