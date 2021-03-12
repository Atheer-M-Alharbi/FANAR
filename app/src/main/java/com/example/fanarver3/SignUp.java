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

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {

    //var's
    Button logInBOT;
    AutoCompleteTextView UserType;
    TextInputLayout EMAIL, PASSWORD,NAME,CONFIRM_PASSWORD;
    //PopupWindow popupWindow;
    // not sure if it's correct or even work ,but in case :1-static get mothed userID
     static String userID;

    //var's
    private static String ip ="192.168.1.21";
    private static String userName ="FANAR";
    private static String Password ="qwer";
    private static String Port ="1433";
    private static String classes ="net.sourceforge.jtds.jdbc.Driver";
    private static String DataBase ="FANAR";
    private static String url ="jdbc:jtds:sqlserver://"+ip+":"+Port+"/"+DataBase;

    private static Connection CONNECTION = null;

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

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //FILL PLAN OPTION
        fillUSERTYPE(this);


    }

    // note the password still need edit
    // WE NEED TO ASK THE USER WHO IS HE?
    private void createNewUser() {
        String userName=NAME.getEditText().toString();
        String email = EMAIL.getEditText().toString();
        int pass = Integer.parseInt(PASSWORD.getEditText().toString());
        int confirmPass = Integer.parseInt(CONFIRM_PASSWORD.getEditText().toString());
        String type= UserType.getText().toString();

        if (CONNECTION != null) {
            Statement statement = null;
            try {
                // check both tables if the user is exist
                String query = "SELECT Email,Password, ParentID FROM Parent" +
                        "WHERE Email=" + email  +
                        "UNION" +
                        "SELECT Email,Password,SpecialistID FROM Specialist" +
                        "WHERE Email=" + email ;
                statement = CONNECTION.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                // if the rows=0
                //check if exist
                if (resultSet.next()) {
                    // popup alar message
                    //AlertDialog dialog
                    // pop.showMessageDialog("this email "+email+"is already exist, if this was you please press forget password")
                } //create new user
                 else {
                     //check the email - it's ok

                    int isemail=email.indexOf("@");
                    int isemail2=email.indexOf(".com");
                    if(!((isemail & isemail2) == 0)){
                    if(pass==confirmPass){
                        int ID = Integer.parseInt(userID);
                        if(type.equals("Parent")){
                            ID=ID++;
                            userID="0"+ID;
                            Home parent=new Parent(userID,pass,email,userName);
                            query="INSERT INTO Parent (ParentID,ParentName ,Email,Password  )\n" +
                                    "VALUES ('"+userID+"', '"+userName+"' ,'"+email+"',"+pass+");";
                            resultSet = statement.executeQuery(query);


                        }
                        else if (type.equals("Specialist")){
                            userID= "1"+userID;
                            Home specialist=new Specialist(userID,pass,email,userName);
                            query="INSERT INTO Specialist (SpecialistID,SpecialistName ,Email,Password  )\n" +
                                    "VALUES ('"+userID+"', '"+userName+"' ,'"+email+"',"+pass+");";
                            resultSet = statement.executeQuery(query);
                        }

                    }
                    else{
                        // popup alar message
                        //AlertDialog dialog
                        // pop.showMessageDialog("the password and comfirm password isn't simmiler")
                    }
                        //

                }//check email
                    else{
                        // popup alar message
                        //AlertDialog dialog
                        // pop.showMessageDialog("the password and comfirm password isn't simmiler")
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }}
    public void fillUSERTYPE(SignUp view){

        UserType = (AutoCompleteTextView) view.findViewById(R.id.UserTypeSelected);

        String[] UserTypes = {"  ", "Parent", "Specialist"}; //
        ArrayAdapter arrayAdaptermenu1 = new ArrayAdapter(this, R.layout.activity_sign_in , UserTypes);
        UserType.setText(arrayAdaptermenu1.getItem(0).toString(),false);
        UserType.setAdapter(arrayAdaptermenu1);

    };
}