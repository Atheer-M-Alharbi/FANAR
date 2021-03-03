package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.fanarver3.R;
import com.example.fanarver3.Plan;
import com.google.android.material.textfield.TextInputLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class parent_Plan extends AppCompatActivity{

    ChipNavigationBar chipNavigationBar;
    //var's => needed for educational skill
    android.widget.Button createPlan;
    android.widget.AutoCompleteTextView AutismLevelSelected;
    android.widget.AutoCompleteTextView ageSelected;
    android.widget.AutoCompleteTextView IQLevelSelected;
    android.widget.AutoCompleteTextView PerceptionSelected;
    TextInputLayout child_Name;

    ArrayList selrctedSkills;

    ImageView UserProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__plan);

        // get menu id & make homescreen selecteds
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_plan,true);
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
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        //create bottom - move to plan content fragment
        createPlan = findViewById(R.id.createPlanBottom);
        createPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((!AutismLevelSelected.getText().equals(" "))&&(!ageSelected.getText().equals(" "))&&(!IQLevelSelected.getText().equals(" "))&&(!PerceptionSelected.getText().equals(" "))){
                    //2. send required factor to the model

                 Plan planLevel = new Plan(child_Name.getEditText().toString());
                 int thePlanLevel=  planLevel.GenerateDelvlopmentPlan(AutismLevelSelected.getText().toString(),ageSelected.getText().toString(),IQLevelSelected.getText().toString(),PerceptionSelected.getText().toString());

                 // week1 = findViewById(R.id.week1);
                 // week1.setText(thePlanLevel);

                 switch (thePlanLevel){
                        case 0:
                          //  Intent intent = new Intent(parent_Plan.this, skills_level_1.class);
                            //startActivity(intent);
                            setContentView(R.layout.skils_level_1);
                            getSelectedLevel(planLevel);
                            break;
                        case 1:
                            setContentView(R.layout.skils_level_2);
                            break;
                        case 2:
                            Intent intent = new Intent(parent_Plan.this, Parent_PlanContent.class);
                            startActivity(intent);
                            setContentView(R.layout.skils_level_3);
                            break;
                        case 3:
                            setContentView(R.layout.skils_level_4);
                            break;


                 }
                    // selrctedSkills.add();

                }

            }
        });
    }


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

    public void getSelectedLevel(Plan planLevel){

        CheckBox skill1 = findViewById(R.id.ex1);
        CheckBox skill2 = findViewById(R.id.ex2);
        CheckBox skill3 = findViewById(R.id.ex3);
        CheckBox skill4 = findViewById(R.id.ex4);
        CheckBox skill5 = findViewById(R.id.ex5);


        if (skill1.isSelected()){
            selrctedSkills.add(skill1.getId());
        }
        if (skill2.isSelected()){
            selrctedSkills.add(skill2.getId());
        }
        if (skill3.isSelected()){
            selrctedSkills.add(skill3.getId());
        }
        if (skill4.isSelected()){
            selrctedSkills.add(skill4.getId());
        }
        if (skill5.isSelected()){
            selrctedSkills.add(skill5.getId());
        }

        planLevel.fetchResources(selrctedSkills);


    };

    public void fillAUTSIMLEVEL(parent_Plan view){

        AutismLevelSelected = (AutoCompleteTextView) view.findViewById(R.id.AutismLevelSelected);

        String[] AutismLevel = {"  ", "High function"}; //
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option , AutismLevel);
        AutismLevelSelected.setText(arrayAdaptermenu1.getItem(0).toString(),false);
        AutismLevelSelected.setAdapter(arrayAdaptermenu1);

    };
    public void fillPERCEPTION(parent_Plan view){

        PerceptionSelected = (AutoCompleteTextView) view.findViewById(R.id.PerceptionSelected);

        String[] AutismLevel = {"  ","Low","medium","High" };
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option , AutismLevel);
        PerceptionSelected.setText(arrayAdaptermenu1.getItem(0).toString(),false);
        PerceptionSelected.setAdapter(arrayAdaptermenu1);

    };
    public void fillAGE(parent_Plan view){

        ageSelected = (AutoCompleteTextView) view.findViewById(R.id.ageSelected);

        String[] AutismLevel = {"  ","6", "7" ,"8" , "9" , "10" ,"11", "12" ,"13" , "14" , "15","16", "17" ,"18" , "19" };
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option , AutismLevel);
        ageSelected.setText(arrayAdaptermenu1.getItem(0).toString(),false);
        ageSelected.setAdapter(arrayAdaptermenu1);

    };
    public void fillIQLEVEL(parent_Plan view){

        IQLevelSelected = (AutoCompleteTextView) view.findViewById(R.id.IQLevelSelected);

        //or is it better as text input??
        String[] AutismLevel = {"  ","70", "71" ,"72" ,"73", "74" , "75" ,"76", "77" ,"78" , "79" ,"80","81","82" ,"83", "84" , "85" ,"86", "87" ,"88" , "89"
                ,"90","91", "92" ,"93" , "94" , "95" ,"96", "97" ,"98" , "99" , "99","100", };
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option , AutismLevel);
        IQLevelSelected.setText(arrayAdaptermenu1.getItem(0).toString(),false);
        IQLevelSelected.setAdapter(arrayAdaptermenu1);

    };

}