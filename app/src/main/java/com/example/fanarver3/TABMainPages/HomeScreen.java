package com.example.fanarver3.TABMainPages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.fanarver3.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {


    ChipNavigationBar chipNavigationBar;
    static HomeScreen h;
    String Parent_user_ID;
    TextView test;

    TextView textthetest;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        test = findViewById(R.id.textView8);
        Parent_user_ID = getIntent().getStringExtra("user");
        test.setText(Parent_user_ID);

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
                        Intent intent = new Intent(getApplicationContext(), parent_Plan.class);
                        intent.putExtra("user",Parent_user_ID);
                        startActivity(intent);
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