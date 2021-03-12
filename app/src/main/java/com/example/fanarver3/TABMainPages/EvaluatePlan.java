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

import com.example.fanarver3.R;

public class EvaluatePlan extends AppCompatActivity {

    String[] childLevel;
    LinearLayout SkillsList;
    Button sendEVALUTIOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_plan);

        childLevel = new String[]{"  ", "Mastered", "Not Mastered"};
        SkillsList = findViewById(R.id.linearLayoutLIST);
        int size = parent_Plan.selrctedSkills.size();


        // need to fetch skill from database and store it in selrctedSkills but where????? (i'm lost \T^T/)
        for(int i = 0; i>= size; i++){
            addQUE(parent_Plan.selrctedSkills.get(i).toString());
        }

        sendEVALUTIOn = findViewById(R.id.sendEVALUTIONBotton);
        sendEVALUTIOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get result of selected child level
                // remove mastred skill from child plan database
                // if fetch from database where plan id = to this plan id = 0 raw
                // then show frame of skills level-2
                // else no change (?) i gusse
                // + quiry method please


                  }

            }
        );

    }


    public void addQUE(String skill) {

        View createSKILLview = getLayoutInflater().inflate(R.layout.evaluate_plan_q, null,false);
        TextView qustion = (TextView) findViewById(R.id.Q);
        qustion.setText(skill);
        com.google.android.material.textfield.TextInputLayout t = (com.google.android.material.textfield.TextInputLayout) findViewById(R.id.ANSWERQ1);
        AutoCompleteTextView SelectedANS = (AutoCompleteTextView) findViewById(R.id.ANSWERQ1Selected);
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.plan_information_option, childLevel);
        SelectedANS.setText(arrayAdaptermenu1.getItem(0).toString(), false);
        SelectedANS.setAdapter(arrayAdaptermenu1);

        SkillsList.addView(createSKILLview);

    }


}