package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class SP_ViewPlanContent_days extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    Button viewResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_s_p__view_plan_content_days);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_home,true);
        bottonMenu();

        viewResource = findViewById(R.id.sp_viewday1Botton);
        viewResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SP_ViewPlanContent_days.this, SP_ViewPlanContent_Resource.class );
                startActivity(intent);
            }
        } );

    }


    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch(i){
                    case R.id.sp_nav_Community:
                        startActivity(new Intent(getApplicationContext(),sp_CommunityScreen.class));
                        overridePendingTransition(0,0);
                        break;

                }

            }
        }  );
    }
}