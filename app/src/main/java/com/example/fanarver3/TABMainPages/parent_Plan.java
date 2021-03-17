package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.fanarver3.Home;
import com.example.fanarver3.Observable;
import com.example.fanarver3.OnChangeListener;
import com.example.fanarver3.R;
import com.example.fanarver3.Plan;
import com.example.fanarver3.SPscreen.SPhomeScreen;
import com.google.android.material.textfield.TextInputLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.fanarver3.Specialist;


public class parent_Plan extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    //var's => needed for educational skill
    android.widget.Button createPlan;
    ImageView UserProfile;
    android.widget.AutoCompleteTextView AutismLevelSelected;
    android.widget.AutoCompleteTextView ageSelected;
    android.widget.AutoCompleteTextView IQLevelSelected;
    android.widget.AutoCompleteTextView PerceptionSelected;
    TextInputLayout child_Name;
    Button okayBot;
    // to be able to reach it from evaluation plan class
    public static ArrayList selrctedSkills;

    // need it to call method of this class with the same object
    public static parent_Plan parentPLAN;
    public static Observable obsInt;

    String Parent_user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Parent_user_ID = getIntent().getStringExtra("user");

        // check if this parent has plan or not
        String quiry = "SELECT planobj FROM ChildPlan WHERE ParentID  =  "+ Parent_user_ID + ";";  // <---('.')<---
        ResultSet rs = Home.sqlConn(quiry);
        try {
            if (rs.next() != false) {
                Plan plan = Plan.loadPlanFromdatabase(quiry, 1);
                rs.getBlob(0);
                if (plan.isPlanState() == false) {
                    setContentView(R.layout.wating_for_approval);
                } else {
                    Intent intent = new Intent(parent_Plan.this, Parent_PlanContent.class);
                    intent.putExtra("plan", plan);
                    startActivity(intent);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        setContentView(R.layout.activity_parent__plan);
        // get menu id & make homescreen selecteds
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_plan, true);
        bottonMenu();


        //userProfileOnClick
        UserProfile = findViewById(R.id.profile_icon);
        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent_Plan.this, User_Profile.class);
                startActivity(intent);
            }
        });


        //FILL PLAN OPTION
        fillAUTSIMLEVEL(this);
        fillAGE(this);
        fillIQLEVEL(this);
        fillPERCEPTION(this);
        child_Name = findViewById(R.id.childName);
        // 1. start python
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }


        //create bottom - move to plan content fragment
        createPlan = findViewById(R.id.createPlanBottom);
        createPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ctrl + alt + L

                // check child name is not empty!!!!!!!!!!!!!!!!!!!
                if ((!child_Name.getEditText().toString().equals(" ")) && (!AutismLevelSelected.getText().toString().equals(" ")) && (!ageSelected.getText().toString().equals(" ")) && (!IQLevelSelected.getText().toString().equals(" ")) && (!PerceptionSelected.getText().toString().equals(" "))) {
                    //2. send required factor to the model

                    Plan plan = new Plan(child_Name.getEditText().toString());
                    int thePlanLevel = plan.GenerateDelvlopmentPlan(AutismLevelSelected.getText().toString(), ageSelected.getText().toString(), IQLevelSelected.getText().toString(), PerceptionSelected.getText().toString());

                    // create new object of Observableboolean to check on plan state
                    obsInt = new Observable();
                    PlanLevel(thePlanLevel, plan);

                    // added new col ---> parent id + plan object
                    Home.sqlConn("INSERT INTO ChildPlan(PlanID, PlanLevel, Approve, planobj, SpecialistID, ChildID, ParentID)" +
                            "VALUES (" + plan.getPlanID() + "," + thePlanLevel + ",0, NULL, NULL," + plan.childID+ "," + Parent_user_ID + ");");

                } else {
                    openDialog();
                }


            }
        });


    }


    public void openDialog() {
        exampleDialog ed = new exampleDialog();
        ed.show(getSupportFragmentManager(), "error dialog");
    }

    public void PlanLevel(int thePlanLevel, Plan plan) {

        switch (thePlanLevel) {
            case 0:
                setContentView(R.layout.skils_level_1);
                break;
            case 1:
                setContentView(R.layout.skils_level_2);
                break;
            case 2:
                setContentView(R.layout.skils_level_3);
                break;
            case 3:
                setContentView(R.layout.skils_level_4);
                break;
        }


        okayBot = findViewById(R.id.Okay);
        okayBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSelectedLevel(plan);
            }
        });


        obsInt.setOnChangeListener(new OnChangeListener() {
            @Override
            public void onBooleanChanged(boolean newValue) {

                if (newValue == false) {
                    setContentView(R.layout.wating_for_approval);
                } else {
                    Intent intent = new Intent(parent_Plan.this, Parent_PlanContent.class);
                    intent.putExtra("plan", plan);
                    startActivity(intent);
                }
            }


        });
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

    public void getSelectedLevel(Plan plan) {

        CheckBox skill1 = findViewById(R.id.ex1);
        CheckBox skill2 = findViewById(R.id.ex2);
        CheckBox skill3 = findViewById(R.id.ex3);
        CheckBox skill4 = findViewById(R.id.ex4);


        if (skill1.isSelected()) {
            selrctedSkills.add(skill1.getText().toString());
        }
        if (skill2.isSelected()) {
            selrctedSkills.add(skill2.getText().toString());
        }
        if (skill3.isSelected()) {
            selrctedSkills.add(skill3.getText().toString());
        }
        if (skill4.isSelected()) {
            selrctedSkills.add(skill4.getText().toString());
        }


        // setselrctedSkills for the plan
        try {
            plan.fetchResources(selrctedSkills);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Specialist.AssigSpTolist(plan.getPlanID());

    }


    public void fillAUTSIMLEVEL(parent_Plan view) {

        AutismLevelSelected = (AutoCompleteTextView) view.findViewById(R.id.AutismLevelSelected);

        String[] AutismLevel = {"  ", "High function"}; //
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, AutismLevel);
        AutismLevelSelected.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        AutismLevelSelected.setAdapter(arrayAdaptermenu1);

    }


    public void fillPERCEPTION(parent_Plan view) {

        PerceptionSelected = (AutoCompleteTextView) view.findViewById(R.id.PerceptionSelected);

        String[] AutismLevel = {"  ", "Low", "medium", "High"};
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, AutismLevel);
        PerceptionSelected.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        PerceptionSelected.setAdapter(arrayAdaptermenu1);

    }


    public void fillAGE(parent_Plan view) {

        ageSelected = (AutoCompleteTextView) view.findViewById(R.id.ageSelected);

        String[] AutismLevel = {"  ", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, AutismLevel);
        ageSelected.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        ageSelected.setAdapter(arrayAdaptermenu1);

    }


    public void fillIQLEVEL(parent_Plan view) {

        IQLevelSelected = (AutoCompleteTextView) view.findViewById(R.id.IQLevelSelected);

        //or is it better as text input??
        String[] AutismLevel = {"  ", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89"
                , "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "99", "100",};
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, AutismLevel);
        IQLevelSelected.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        IQLevelSelected.setAdapter(arrayAdaptermenu1);

    }


}