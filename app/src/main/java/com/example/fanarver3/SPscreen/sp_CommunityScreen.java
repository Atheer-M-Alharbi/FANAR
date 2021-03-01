package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class sp_CommunityScreen extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sp__community_screen);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_Community,true);
        bottonMenu();

    }

    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch(i){
                     case R.id.sp_nav_home:
                        startActivity(new Intent(getApplicationContext(),SPhomeScreen.class));
                         overridePendingTransition(0,0);
                        break;

                }

            }
        }  );
    }

}