package com.example.fanarver3.SPscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fanarver3.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class sp_View_plan extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    Button rejectPlan, ApproveBotton , sendCorretlevelBotton , viewPlancontent;
    TextView  currentleveltect;
    TextInputLayout correct_levelm , comentOnPlan;
    TextInputEditText comentOnPlan1;
    AutoCompleteTextView correct_levelm1;

    ScrollView rejectView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sp__view_plan);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.sp_menu);
        chipNavigationBar.setItemSelected(R.id.sp_nav_home,true);
        bottonMenu();


        //FIND COMMPONENET BY ID
        rejectPlan = findViewById(R.id.RejectBotton);
        ApproveBotton = findViewById(R.id.ApproveBotton);
        sendCorretlevelBotton = findViewById(R.id.sendCorretlevelBotton);
        currentleveltect = findViewById(R.id.currentleveltect);
        comentOnPlan = findViewById(R.id.correct_level);
        correct_levelm1 = findViewById(R.id.correct_levelSelected);

        rejectView = findViewById(R.id.rejectview);
        viewPlancontent = findViewById(R.id.viewPlan3Botton);

        //set button listener FOR REJECT BOTTON
        rejectPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //set reject & aprove botton  visability as gone
             // make   sendCorretlevelBotton  & text & comment & send botton as visiably

                rejectPlan.setVisibility(view.GONE);
                ApproveBotton.setVisibility(view.GONE);
                rejectView.setVisibility(view.VISIBLE);
             }
        } );

        viewPlancontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sp_View_plan.this, SP_ViewPlanContent_days.class );
                startActivity(intent);
            }
        } );

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
                    case R.id.sp_nav_Community:
                        startActivity(new Intent(getApplicationContext(),sp_CommunityScreen.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });
    }
    // bottonMenu method end

}