package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fanarver3.TABMainPages.Parent_chatBot;
import com.example.fanarver3.TABMainPages.Parent_cummunity;
import com.example.fanarver3.TABMainPages.parent_Plan;
import com.google.android.material.textfield.TextInputEditText;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.ResultSet;
import java.util.ArrayList;

public class view_subject extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    //TextView message;
    Toolbar toolbar;
   // Button leave, add;
  //  Parent P;
   // Specialist s;

    //TextView MESSAGE;
    TextView TITLE;
    TextView SUBJECT;
    //Button OK, CANCEL;
    //String user_ID;
    //int Community_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject);

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_home, true);
        bottonMenu();
        toolbar = (Toolbar) findViewById(R.id.toolbarView);
        TITLE=findViewById(R.id.titleVeiw);
        SUBJECT=findViewById(R.id.subjectView);

        //String user_ID = getIntent().getStringExtra("user");
        int Community_ID = getIntent().getIntExtra("Commuinty", 0);
        //String userType = Home.getUserType(user_ID);

        if (Community_ID == 1) {
            toolbar.setTitle("Early age community");
        }
        if (Community_ID == 2) {
            toolbar.setTitle("Teenager community");
        }
        if (Community_ID == 3) {
            toolbar.setTitle("General community");
        }
        TITLE.setText(getIntent().getStringExtra("title"));
        SUBJECT.setText(getIntent().getStringExtra("sub"));


    }

    private void bottonMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Activity InWhichActivity = null;
                Intent intent;
                String user_ID = getIntent().getStringExtra("user");
                System.out.println("home ** user_ID" + user_ID);

                switch (i) {
                    case R.id.nav_plan:
                        intent = new Intent(getApplicationContext(), parent_Plan.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_com:
                        intent = new Intent(getApplicationContext(), Parent_cummunity.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.nav_chatBot:
                        intent = new Intent(getApplicationContext(), Parent_chatBot.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        break;
                }
            }
        });
    }

}