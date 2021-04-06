package com.example.fanarver3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddSubject2 extends AppCompatActivity {

    TextView MESSAGE;
    TextInputEditText TITLE;
    EditText SUBJECT;
    Button OK, CANCEL;
    String user_ID;
    int Community_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject2);

        MESSAGE=findViewById(R.id.messageAddSubject);
        TITLE=findViewById(R.id.addTitle);
        SUBJECT=findViewById(R.id.subject);
        OK=findViewById(R.id.OkayAddSubject);
        CANCEL=findViewById(R.id.cancelAddSubject);

         user_ID = getIntent().getStringExtra("user");
         Community_ID = getIntent().getIntExtra("Commuinty", 0);
        //String userType = Home.getUserType(user_ID);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(addNewSubject()){
                        Intent intent = new Intent(AddSubject2.this, SubjectList.class);
                        intent.putExtra("user", user_ID);
                        intent.putExtra("Commuinty", Community_ID);
                        startActivity(intent);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        CANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AddSubject2.this);
                    dialog.setTitle("Are you sure want to cancel you subject");
                    dialog.setMessage("Write your message here.");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(AddSubject2.this, SubjectList.class);
                                    intent.putExtra("user", user_ID);
                                    intent.putExtra("Commuinty", Community_ID);
                                    startActivity(intent);
                                }
                            });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = dialog.create();
                    alert.show();
                }

            });
    }
    private boolean addNewSubject() throws SQLException {
        boolean flag=false;
        String title=TITLE.getText().toString();
        String subject=SUBJECT.getText().toString();

        if(title.isEmpty()||subject.isEmpty())
            MESSAGE.setText("Please enter the field with appropriate input.");
        else{
            String q="select parentname from parent where parentid='"+user_ID+"';";
            ResultSet resultSet=Home.sqlConn(q);
            resultSet.next();
            String auther=resultSet.getString("parentname");
            Subject s=new Subject(title,subject,auther);
            q="insert into communitysubject values("+Community_ID+",'"+title+"','"+subject+"','"+auther+"');";
            resultSet=Home.sqlConn(q);
            flag=true;
        }
        return flag;
    }
}