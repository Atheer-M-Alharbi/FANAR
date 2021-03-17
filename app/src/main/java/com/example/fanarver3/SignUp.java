package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

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
    AutoCompleteTextView UserType;
    TextInputLayout EMAIL, PASSWORD,NAME,CONFIRM_PASSWORD;
    TextView  testview, newUser;
    static int currUserID;

    //var's
    private static String ip ="192.168.43.13";
    private static String userName ="FANAR";
    private static String Password ="qwer";
    private static String Port ="1433";
    private static String classes ="net.sourceforge.jtds.jdbc.Driver";
    private static String DataBase ="FANARDB";
    private static String url ="jdbc:jtds:sqlserver://"+ip+":"+Port+"/"+DataBase;

    private static Connection CONNECTION = null;

    boolean isCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        logInBOT = findViewById(R.id.button4);
        EMAIL = findViewById(R.id.Email);
        PASSWORD = findViewById(R.id.password);
        NAME= findViewById(R.id.user_name);
        CONFIRM_PASSWORD=findViewById(R.id.confirmPassword);
        UserType= findViewById(R.id.UserTypeSelected);

        //for testing
        testview = findViewById(R.id.textView);
        newUser = findViewById(R.id.newUser);

        logInBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LogIn.class );
                startActivity(intent);
            }
        });

        //ms sql server database connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);
            SignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createNewUser();
                    Intent intent = new Intent(SignUp.this, HomeScreen.class );
                    startActivity(intent);
                }
            });

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            testview.setText("error, TRY AGAIN!!!!");
        }
        //FILL PLAN OPTION
        fillUSERTYPE(this);


    }

    private void createNewUser() {
        String userName = NAME.getEditText().toString();
        String email = EMAIL.getEditText().toString();
        String pass = PASSWORD.getEditText().toString();
        int confirmPass = Integer.parseInt(CONFIRM_PASSWORD.getEditText().toString());
        String type = UserType.getText().toString();
        String userID;
        if (!(userName.equals("Name"))) {
            if (CONNECTION != null) {
                Statement statement = null;
                try {
                    //check the email
                    int isemail = email.indexOf("@");
                    int isemail2 = email.indexOf(".com");
                    if ((isemail & isemail2) == 1) {
                        // check both tables if the user is exist
                        String query = "SELECT Email,Password, ParentID FROM Parent" +
                                "WHERE Email=" + email +
                                "UNION" +
                                "SELECT Email,Password,SpecialistID FROM Specialist" +
                                "WHERE Email=" + email;
                        statement = CONNECTION.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        //check if exist
                        if (resultSet.next()) {
                            testview.setText("this email " + email + "is already exist, if it was yours please go to LogIn and choose forget password");
                        } else {
                            // check the pass
                            if (pass.equals(confirmPass)) {
                                // ganeratr the ID and insert the object to DB
                                if (type.equals("Parent")) {
                                    userID = "0" + currUserID;
                                    Home parent = new Parent(userID, pass, email, userName);
                                    query = "INSERT INTO Parent (ParentID,ParentName ,Email,Password)" +
                                            "VALUES ('" + userID + "', '" + userName + "' ,'" + email + "'," + pass + ");";
                                    resultSet = statement.executeQuery(query);
                                    currUserID++;
                                } else if (type.equals("Specialist")) {
                                    userID = "1" + currUserID;
                                    Home specialist = new Specialist(userID, pass, email, userName);
                                    query = "INSERT INTO Specialist (SpecialistID,SpecialistName ,Email,Password  )\n" +
                                            "VALUES ('" + userID + "', '" + userName + "' ,'" + email + "'," + pass + ");";
                                    resultSet = statement.executeQuery(query);
                                    currUserID++;
                                }
                            } else {
                                testview.setText("the password and comfirm password do not match");
                            }
                        }
                    }
                    //check email
                    else {
                        testview.setText("the email incorrect, it should be like this example: example@example.com");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else{
            testview.setText("The Name is incorrect");
        }
    }

    public void fillUSERTYPE(SignUp view){

        UserType = (AutoCompleteTextView) view.findViewById(R.id.UserTypeSelected);

        String[] UserTypes = {"  ", "Parent", "Specialist"}; //
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.activity_sign_in , UserTypes);
        UserType.setText(arrayAdaptermenu1.getItem(0).toString(),false);
        UserType.setAdapter(arrayAdaptermenu1);

    };
}