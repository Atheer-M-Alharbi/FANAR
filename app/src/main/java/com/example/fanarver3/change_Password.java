package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fanarver3.TABMainPages.HomeScreen;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.ResultSet;
import java.sql.SQLException;

public class change_Password extends AppCompatActivity {

    Button changePassword;
    TextView testview ;
    TextInputEditText EMAIL, PASSWORD, CONFIRM_PASSWORD;
    private String user_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(" on change pass class");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__password);

        changePassword = findViewById(R.id.changePassword);
        testview = findViewById(R.id.testCHANGE);

        // backarraw code  -- copy it to the rest of the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.backarrow); // the drawable
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        EMAIL = findViewById(R.id.user_email);
        PASSWORD = findViewById(R.id.New_Password);
        CONFIRM_PASSWORD = findViewById(R.id.Conform_New_Password);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" lis forgetPassword ok button 1");
                // check both tables if the user is exist
                String email=EMAIL.getText().toString().toLowerCase();
                String pass = PASSWORD.getText().toString();
                String con_pass = CONFIRM_PASSWORD.getText().toString();


                String parentQuery = "SELECT ParentID FROM Parent WHERE Email ='" + email + "';";
                ResultSet parentResult = Home.sqlConn(parentQuery);

                String SpecialistQuery = "SELECT SpecialistID FROM Specialist WHERE Email ='" + email + "';";
                ResultSet SpecialistResult = Home.sqlConn(SpecialistQuery);

                try {
                    if (checkEmail(email,SpecialistResult, parentResult)) {
                        if (checkPass(pass,con_pass)) {
                            String UserType = Home.getUserType(user_ID);
                            if (UserType.equals("parent")) {
                                Home parent = new Parent();
                                parent.setPassword(pass, user_ID);

                            } else if (UserType.equals("Specialist")) {
                                System.out.println(" set pass Specialist 1");
                                Home Specialist = new Specialist();
                                System.out.println(" set pass Specialist 2");
                                Specialist.setPassword(pass, user_ID);
                                System.out.println(" set pass Specialist 3");
                            }
                            System.out.println(" after set pass ");
                            Intent intent = new Intent(change_Password.this, LogIn.class);
                            System.out.println(" move from forgpass to log in");
                            intent.putExtra("changeMessage", "Your password has change successfully, please log in with your new password");
                            startActivity(intent);
                            System.out.println(" done !!!!!!!!!!!!!!");
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public boolean checkEmail(String email,ResultSet SpecialistResult, ResultSet parentResult) throws SQLException {
        int isemail = email.toString().indexOf("@gmail.com");
        if (isemail >= 2) {
            //check if exist
            if (parentResult.next() || SpecialistResult.next()) {
                user_ID = parentResult.getString(1);
                return true;
            } else
                testview.setText("Invalid Email");
            return false;
        } else {
            testview.setText("the email incorrect, it should be like this example: example@gmail.com");
            return false;
        }
    }


    public boolean checkPass(String password,String confirm){
        if((password.isEmpty()&&confirm.isEmpty())&&(!password.equals(confirm))) {
            testview.setText("The password and confirm password do not match");
            return false;
        }
        else {
            return true;
        }

    }

}