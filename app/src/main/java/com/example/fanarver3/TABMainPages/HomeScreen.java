package com.example.fanarver3.TABMainPages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.example.fanarver3.R;
import com.example.fanarver3.SPscreen.sp_CommunityScreen;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeScreen extends AppCompatActivity {


    ChipNavigationBar chipNavigationBar;
    static HomeScreen h;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_home,true);
        bottonMenu();

   }


    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Activity InWhichActivity = null;
                switch(i){
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