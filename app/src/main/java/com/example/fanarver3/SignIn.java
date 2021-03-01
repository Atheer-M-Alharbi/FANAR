package com.example.fanarver3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SignIn extends AppCompatActivity {

    //var's
    Button logInBOT;

    //var's
    private static String ip ="192.168.1.21";
    private static String userName ="FANAR";
    private static String Password ="qwer";
    private static String Port ="1433";
    private static String classes ="net.sourceforge.jtds.jdbc.Driver";
    private static String DataBase ="FANAR";
    private static String url ="jdbc:jtds:sqlserver://"+ip+":"+Port+"/"+DataBase;

    private Connection CONNECTION = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide state bar (the one show the sharge and time in phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);

        logInBOT = findViewById(R.id.button4);
        logInBOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, LogIn.class );
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


    }
}