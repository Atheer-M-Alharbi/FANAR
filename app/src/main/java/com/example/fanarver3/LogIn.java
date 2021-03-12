package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.PopupWindow;

import com.example.fanarver3.TABMainPages.HomeScreen;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogIn extends AppCompatActivity {

    //var's
    Button goToSignUp, logInBOT;
    ImageView IMAGE;
    TextView DEC_TEXT , testview;
    TextInputLayout EMAIL, PASSWORD;
    //PopupWindow popupWindow;
    // not sure if it's correct or even work ,but in case :1-static get mothed userID
    static int userID;

    //var's
    private static String ip = "192.168.1.21";
    private static String userName = "FANAR";
    private static String Password = "qwer";
    private static String Port = "1433";
    private static String classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String DataBase = "FANAR";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;
    private Connection CONNECTION = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);

        //var
        goToSignUp = findViewById(R.id.sign_up_button);
        IMAGE = findViewById(R.id.imageView6);
        DEC_TEXT = findViewById(R.id.textView3);
        EMAIL = findViewById(R.id.Email);
        PASSWORD = findViewById(R.id.password);
        logInBOT = findViewById(R.id.button3);

        //for testing
        testview = findViewById(R.id.textView);




        // animation while moving to the next activity
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View, String>(IMAGE, "logo_image");
                pairs[1] = new Pair<View, String>(DEC_TEXT, "text");
                pairs[2] = new Pair<View, String>(EMAIL, "email_trans");
                pairs[3] = new Pair<View, String>(PASSWORD, "password_image");
                pairs[4] = new Pair<View, String>(logInBOT, "login_trans");
                pairs[5] = new Pair<View, String>(goToSignUp, "signin_trans");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LogIn.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });


        //ms sql server database connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);
            testview.setText("con is success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            testview.setText("error,failural");
        }


    }
// note the password still need edit
    private void verifyUser() {
        //boolean flag=false;
        if (CONNECTION != null) {
            Statement statement = null;
            try {
                String email=EMAIL.getEditText().toString();
                int pass= Integer.parseInt(PASSWORD.getEditText().toString());
                // check both tables if the user is exist
                String query = "SELECT Email,Password, ParentID FROM Parent" +
                        "WHERE Email=" + email + " AND Password=" + pass +
                        "UNION"+
                        "SELECT Email,Password,SpecialistID FROM Specialist" +
                        "WHERE Email="+email+" AND Password="+pass;
                statement = CONNECTION.createStatement();
                ResultSet resultSet =  statement.executeQuery(query);
                // if the rows=0
                if (!resultSet.next()){
                    // popup alar message
                    //AlertDialog dialog
                   // return false;
                   // pop.showMessageDialog("email or password are invalid")
                    }
                else{
                    // not sure
                   userID= resultSet.getInt("ParentID");
                    //test homepage & navigation bottom
                    logInBOT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(LogIn.this, HomeScreen.class);
                            startActivity(intent);
                        }
                    });

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else{
            testview.setText("con is null");

        }
    }

}