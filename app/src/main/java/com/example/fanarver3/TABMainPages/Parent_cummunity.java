package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Parent_cummunity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_cummunity);


        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_com,true);
        bottonMenu();



    }

    public void bottonMenu() {

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