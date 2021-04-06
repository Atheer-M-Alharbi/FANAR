package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fanarver3.SPscreen.SPhomeScreen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.example.fanarver3.TABMainPages.HomeScreen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {

    //var's
    Button logInBOT, SignInBtn;
    String UserType;
    TextInputEditText EMAIL, PASSWORD, NAME, CONFIRM_PASSWORD;
    TextView testview;
    int newUserID;
    RadioGroup radioGroup;
    RadioButton radioButton, radio_Specialist, radio_Parent;
    String Parent_user_ID;
    String Specialist_user_ID;

    boolean isCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        logInBOT = findViewById(R.id.button4);
        EMAIL = findViewById(R.id.Email);
        PASSWORD = findViewById(R.id.password);
        NAME = findViewById(R.id.user_name);
        CONFIRM_PASSWORD = findViewById(R.id.confirmPassword);
        SignInBtn = findViewById(R.id.SignIN);
        radioGroup = findViewById(R.id.radioGroup);
        //for testing
        testview = findViewById(R.id.test);

        //FILL PLAN OPTION
        //fillUSERTYPE(this);

        logInBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LogIn.class);
                startActivity(intent);
            }
        });

        //ms sql server database connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(" be call");
                try {
                    createNewUser();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println(" after call" + isCreate);
                if (isCreate) {
                    Intent intent;
                    if (Parent_user_ID != null) {
                        intent = new Intent(SignUp.this, HomeScreen.class);
                        intent.putExtra("user", Parent_user_ID);
                        startActivity(intent);
                    } else if (Specialist_user_ID != null) {
                        System.out.println(" enter if 222");
                        intent = new Intent(SignUp.this, SPhomeScreen.class);
                        System.out.println(" after move");
                        intent.putExtra("user", Specialist_user_ID);
                        System.out.println(" after move 2");
                        startActivity(intent);
                        System.out.println(" after move 3");
                    }
                }
            }
        });


    }

    private void createNewUser() throws SQLException {
        System.out.println("in c");
        String Name = NAME.getText().toString();
        String email = EMAIL.getText().toString();
        String pass = PASSWORD.getText().toString();
        String confirmPass = CONFIRM_PASSWORD.getText().toString();
        System.out.println("input done");
        String userID = null;

        //start
        // check both tables if the user is exist
        String parentQuery = "SELECT ParentID FROM Parent WHERE Email ='" + email + "';";
        ResultSet parentResult = Home.sqlConn(parentQuery);

        String SpecialistQuery = "SELECT SpecialistID FROM Specialist WHERE Email ='" + email + "';";
        ResultSet SpecialistResult = Home.sqlConn(SpecialistQuery);

        if (checkName(Name))
            if (checkEmail(email, SpecialistResult, parentResult)) {
                System.out.println("be pass");
                if (checkPass(pass, confirmPass)) {
                    System.out.println("af pass");
                    if (checkradio()) {
                        System.out.println("af r");
                        // ganeratr the ID and insert the object to DB
                        UserType = radioButton.getText().toString();
                        System.out.println(" else 6 " + UserType);
                        if (UserType.equals("I'm Parent")) {
                            System.out.println("12 if 7");
                            newUserID = Home.rowCount("Parent") + 1;
                            System.out.println("13 if 7 " + newUserID);
                            userID = "0" + newUserID;
                            Home parent = new Parent(userID, pass, email, Name);
                            String q = "INSERT INTO Parent (ParentID,ParentName ,Email,Password) VALUES ('" + userID + "', '" + Name + "' ,'" + email + "','" + pass + "');";
                            parentResult = Home.sqlConn(q);
                            Parent_user_ID = newUserID + "";
                            isCreate = true;
                            System.out.println("finsh if 7");
                        } else if (UserType.equals("I'm Specialist")) {
                            System.out.println(" else 7");
                            newUserID = Home.rowCount("Specialist") + 1;
                            System.out.println("13 if 7 " + newUserID);
                            userID = "1" + newUserID;
                            Home specialist = new Specialist(userID, pass, email, Name);
                            String q = "INSERT INTO Specialist (SpecialistID,SpecialistName ,Email,Password  ) VALUES ('" + userID + "', '" + Name + "' ,'" + email + "','" + pass + "');";
                            SpecialistResult = Home.sqlConn(q);
                            isCreate = true;
                            System.out.println("finsh else 7");
                        }
                    }
                }
            }
    }

    public boolean checkName(String userName) {
        if (!(userName.isEmpty()))
            return true;
        else {
            testview.setText("The Name is incorrect");
            return false;
        }
    }

    public boolean checkEmail(String email, ResultSet SpecialistResult, ResultSet parentResult) throws SQLException {
        int isemail = email.indexOf("@gmail.com");
        if (isemail >= 2) {
            //check if exist
            if (parentResult.next() || SpecialistResult.next()) {
                testview.setText("This email " + email + " is already exist, if it was yours please go to Log In and choose forget password");
                return false;
            } else
                return true;
        } else {
            testview.setText("the email incorrect, it should be like this example: example@gmail.com");
            return false;
        }
    }

    public boolean checkPass(String password, String confirm) {
        if(password.isEmpty() || confirm.isEmpty()) {
            testview.setText("The password and confirm password do not match");
            return false;
        }else if ((password.equals(confirm))){
            return true;}
        else {
            testview.setText("The password and confirm password do not match");
            return false;
        }
    }

    public boolean checkradio() {

        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        radio_Parent = findViewById(R.id.radio_Parent);
        radio_Specialist = findViewById(R.id.radio_Specialist);

        //check the radio button
        if (radio_Parent.isChecked() || radio_Specialist.isChecked()) {
            return true;
        }
        //check the radiobutton
        else {
            testview.setText("Please choose either 'I'm Parent' or 'I'm Specialist'");
            return false;
        }
    }


}

