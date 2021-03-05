package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.example.fanarver3.Plan;
import com.example.fanarver3.Parent;

public class Parent_PlanContent extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    android.widget.Button day1;
    android.widget.Button day2;
    android.widget.Button day3;
    android.widget.Button day4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent__plan_content);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_plan,true);
        bottonMenu();

        day1.findViewById(R.id.PlanDay1);
        day2.findViewById(R.id.PlanDay2);
        day3.findViewById(R.id.PlanDay3);
        day4.findViewById(R.id.PlanDay4);

        // i need plan...
        //

        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);

                startActivity(intent);
            }
        });

        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                startActivity(intent);
            }
        });

        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                startActivity(intent);
            }
        });

        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Parent_PlanContent.this, Parent_PlanResourse.class);
                startActivity(intent);
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
                    case R.id.nav_plan:
                        startActivity(new Intent(getApplicationContext(),parent_Plan.class));
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
}