package com.example.fanarver3.TABMainPages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fanarver3.Home;
import com.example.fanarver3.LogIn;
import com.example.fanarver3.Parent;
import com.example.fanarver3.R;
import com.example.fanarver3.SPscreen.SPhomeScreen;
import com.example.fanarver3.SPscreen.sp_CommunityScreen;
import com.example.fanarver3.Specialist;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.SQLException;

public class Parent_cummunity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    Button earlyComm;
    Button teenComm;
    Button genaralComm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_cummunity);


        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_com,true);
        earlyComm=findViewById(R.id.comunity1_Botton);
        teenComm=findViewById(R.id.comunity2_Botton);
        genaralComm=findViewById(R.id.comunity3_Botton);
        String user_ID = getIntent().getStringExtra("user");
        String userType=Home.getUserType(user_ID);
        if(userType.equals("parent"))
            bottonMenuParent();
        else
            bottonMenuSP();

        earlyComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userType.equals("parent")){
                    Parent p=new Parent();
                    try {
                        p.setCommuintyID(1,user_ID);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else{
                    Specialist s=new Specialist();
                    try {
                        s.setCommuintyID(1,user_ID);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        teenComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userType.equals("parent")){
                    Parent p=new Parent();
                    try {
                        p.setCommuintyID(2,user_ID);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else{
                    Specialist s=new Specialist();
                    try {
                        s.setCommuintyID(2,user_ID);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        }


    public void bottonMenuParent() {

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

    private void bottonMenuSP() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.sp_nav_home:
                        startActivity(new Intent(getApplicationContext(), SPhomeScreen.class));
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.sp_nav_Community:
                        startActivity(new Intent(getApplicationContext(), sp_CommunityScreen.class));
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
    }


}