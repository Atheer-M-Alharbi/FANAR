package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fanarver3.SPscreen.SPhomeScreen;
import com.example.fanarver3.TABMainPages.HomeScreen;
import com.example.fanarver3.TABMainPages.Parent_cummunity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;

// Done but won't work
public class LogIn extends AppCompatActivity {

    //var's
    Button goToSignUp, logInBOT , forgetPassword;
    ImageView IMAGE;
    TextView DEC_TEXT, testview, user;
    TextInputEditText EMAIL, PASSWORD;
    String Parent_user_ID;
    String Specialist_user_ID;
    static ArrayList <Home> parent;
    static ArrayList <Home> specaliste;
    //PopupWindow popupWindow;
    // not sure if it's correct or even work ,but in case :1-static get mothed userID


    //var's
    //REEM IP
    private static String ip ="192.168.100.2";
    //ATHEER IP
    //private static String ip = "192.168.1.21";
    private static String userName = "FANAR";
    private static String Password = "qwer";
    private static String Port = "1433";
    private static String classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String DataBase = "FANAR";
    private static String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;
    private Connection CONNECTION = null;

    public boolean isverify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);
        parent=new ArrayList<>();
        specaliste=new ArrayList<>();

        //var
        goToSignUp = findViewById(R.id.sign_up_button);
        IMAGE = findViewById(R.id.imageView6);
        DEC_TEXT = findViewById(R.id.textView3);
        EMAIL = findViewById(R.id.Email);
        PASSWORD = findViewById(R.id.password);
        logInBOT = findViewById(R.id.button3);

        //for testing
        testview = findViewById(R.id.textView);

        testview.setText(getIntent().getStringExtra("changeMessage"));

        forgetPassword=(Button) findViewById(R.id.Forget_password2);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(LogIn.this, change_Password.class);
                startActivity(intent);
            }
        });

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
            DriverManager.setLoginTimeout(100);
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

            System.out.println("CONNECTION is successed");

            if (CONNECTION != null) {
                logInBOT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verifyUser();
                        if (isverify) {
                            Intent intent;
                            if(Parent_user_ID!=null){
                                intent = new Intent(LogIn.this, Parent_cummunity.class);
                                intent.putExtra("user",Parent_user_ID);
                                startActivity(intent);

                            }
                            else if(Specialist_user_ID!=null){
                                System.out.println(" enter if 222");
                                intent = new Intent(LogIn.this, SPhomeScreen.class);
                                System.out.println(" after move");
                                intent.putExtra("user",Specialist_user_ID);
                                System.out.println(" after move 2");
                                startActivity(intent);
                                System.out.println(" after move 3");
                            }
                        }
                    }
                });

            } else {
                testview.append("CONNECTION is null");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            testview.setText("ERROR1"+e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            testview.setText("ERROR2"+throwables.getMessage());
        }



    }

   private void verifyUser() {

            try {
                String email = EMAIL.getText().toString().toLowerCase();
                String pass = PASSWORD.getText().toString();
                // check both tables if the user is exist
                String parentQuery = "SELECT ParentID FROM Parent WHERE Email ='" + email + "' AND Password ='" + pass +"';";
                String SpecialistQuery = "SELECT SpecialistID FROM Specialist WHERE Email ='" + email + "' AND Password ='" + pass +"';";
                Statement Parent = CONNECTION.createStatement();
                System.out.println(" *** SP1");

                Statement Specialist = CONNECTION.createStatement();
                ResultSet parentResult = Parent.executeQuery(parentQuery);
                System.out.println(" *** SP2");
                ResultSet SpecialistResult = Specialist.executeQuery(SpecialistQuery);
                System.out.println(" *** SP3");

                // if THE USE IS exist
                if (parentResult.next()) {
                    // to save the id of the user
                    Parent_user_ID = parentResult.getString(1);
                    System.out.println("******************* after "+Parent_user_ID);
                    isverify= true;
                }
                else if(SpecialistResult.next()) {
                    System.out.println(" enter if 2");
                    Specialist_user_ID=SpecialistResult.getString(1);
                    System.out.println(" after "+Specialist_user_ID);
                    isverify= true;
                }
                else  {
                    // if it was invaled or dosen't have an accuont
                    testview.setText("Invalid Email or Password ");
                    isverify = false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


    }

}