package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fanarver3.Home;
import com.example.fanarver3.Plan;
import com.example.fanarver3.R;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EvaluatePlan extends AppCompatActivity {

    String[] childLevel;
    LinearLayout SkillsList;
    Button sendEVALUTIOn , CancelEVALUTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_plan);

        // for evaluate each skill
        childLevel = new String[]{"  ", "Mastered", "Not Mastered"};

        SkillsList = findViewById(R.id.linearLayoutLIST);

        int size = parent_Plan.selrctedSkills.size();
        // RECIVE PASSED OBJECT
        Plan Plan = getIntent().getParcelableExtra("plan");


        for(int i = 0; i>= size; i++){
            addQUE(parent_Plan.selrctedSkills.get(i).toString());
        }

        // send evaluation & process it
        sendEVALUTIOn = findViewById(R.id.sendEVALUTIONBotton);
        sendEVALUTIOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList mastrendSK = new ArrayList();
                // get result of selected skills
                for(int i = 0;i<=SkillsList.getChildCount();i++){

                    View Parentlayout = SkillsList.getChildAt(i);
                    TextView catagory = (TextView) SkillsList.findViewById(R.id.Q);
                    com.google.android.material.textfield.TextInputLayout t = (com.google.android.material.textfield.TextInputLayout) SkillsList.findViewById(R.id.ANSWERQ1);
                    AutoCompleteTextView SelectedANS = (AutoCompleteTextView) SkillsList.findViewById(R.id.ANSWERQ1Selected);

                    if(SelectedANS.getText().toString() =="Mastered"){
                        mastrendSK.add(catagory);
                    }

                }

                // remove Mastered skill from child plan database
                 for(int i = 0;i<=mastrendSK.size();i++){
                    String quiry = "remove from Excrsise where catogary = "+mastrendSK.get(i)+" planid ="+Plan.PlanID+";";
                    ResultSet result = Home.sqlConn(quiry);

                }
                // if select from database where plan id = 0 raw
                // then show frame of next level skills
                String Selectquiry = "select from Excrsise where planid ="+Plan.PlanID+";";
                ResultSet Selectresult = Home.sqlConn(Selectquiry);
                try {
                    if(Selectresult.next() == false){
                        Plan.Planlevel = Plan.Planlevel+1;
                        parent_Plan.parentPLAN.PlanLevel(Plan.Planlevel, Plan);

                    }else{
                        // back to plancontent class & it will view the (not mastred) skill alone  bc we remove mastreded one above
                        Intent intent = new Intent(EvaluatePlan.this, Parent_PlanContent.class);
                        intent.putExtra("plan",Plan);
                        startActivity(intent);

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                }

            }
        );

        CancelEVALUTION = findViewById(R.id.CancelEVALUTIONBotton);
        CancelEVALUTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EvaluatePlan.this, Parent_PlanContent.class);
                intent.putExtra("plan",Plan);
                startActivity(intent);

                }

             }
        );

    }


    public void addQUE(String skill) {

        View createSKILLview = getLayoutInflater().inflate(R.layout.evaluate_plan_q, null,false);

        TextView catagory = (TextView) findViewById(R.id.Q);
        catagory.setText(skill);
        com.google.android.material.textfield.TextInputLayout t = (com.google.android.material.textfield.TextInputLayout) findViewById(R.id.ANSWERQ1);
        AutoCompleteTextView SelectedANS = (AutoCompleteTextView) findViewById(R.id.ANSWERQ1Selected);
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, childLevel);
        SelectedANS.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        SelectedANS.setAdapter(arrayAdaptermenu1);

        SkillsList.addView(createSKILLview);

    }


}