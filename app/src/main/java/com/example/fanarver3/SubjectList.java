package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fanarver3.TABMainPages.Parent_chatBot;
import com.example.fanarver3.TABMainPages.Parent_cummunity;
import com.example.fanarver3.TABMainPages.parent_Plan;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectList extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    SubjectAdapter subjectAdapter;
    ArrayList<Subject> subject_List;
    TextView message;
    Toolbar toolbar;
    Button leave, add;
    RecyclerView recyclerView;
    ResultSet rs;
    Parent P;
    Specialist s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get menu id & make homescreen selected
        chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setItemSelected(R.id.nav_home, true);
        bottonMenu();
        message = findViewById(R.id.messageSubjectList);
        toolbar = (Toolbar) findViewById(R.id.toolbarSubjectList);
        add = findViewById(R.id.add_subject);
        leave = findViewById(R.id.Leave_cummunity);

        String user_ID = getIntent().getStringExtra("user");
        int Community_ID = getIntent().getIntExtra("Commuinty", 0);
        String userType = Home.getUserType(user_ID);

        P = new Parent();
        s = new Specialist();

        if (Community_ID == 1) {
            toolbar.setTitle("Early age community");
        }
        if (Community_ID == 2) {
            toolbar.setTitle("Teenager community");
        }
        if (Community_ID == 3) {
            toolbar.setTitle("General community");
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectList.this, AddSubject2.class);
                intent.putExtra("user", user_ID);
                intent.putExtra("Commuinty", Community_ID);
                startActivity(intent);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (userType.equals("parent")) {
                        P.deleteCommuinty(user_ID, Community_ID);
                        Intent intent = new Intent(SubjectList.this, Parent_cummunity.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                    } else {
                        s.deleteCommuinty(user_ID, Community_ID);
                        Intent intent = new Intent(SubjectList.this, Parent_cummunity.class);
                        intent.putExtra("user", user_ID);
                        startActivity(intent);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        String q = "select * from CommunitySubject where CommunityID=" + Community_ID + ";";
        rs = Home.sqlConn(q);
        subject_List = new ArrayList<Subject>();
        boolean flag = false, isfinish = false;
        try {
            while (rs.next()) {
                flag = true;
                String subjectTitle = rs.getString("SubjectTitle");
                String Subject = rs.getString("Subject");
                String author = rs.getString("MemberName");
                subject_List.add(new Subject(subjectTitle, Subject, author));
            }
            if (flag == false)
                message.setText("There is no subject has been added yet.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        subjectAdapter = new SubjectAdapter(this, subject_List);
        recyclerView.setAdapter(subjectAdapter);
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