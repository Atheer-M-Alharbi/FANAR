package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class User_Profile extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Account");

        //
        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        bottonMenu();

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