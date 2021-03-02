package com.example.fanarver3;

import android.content.Context;
import android.os.StrictMode;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Plan {


    // var's for child info
    public boolean PlanState;
    public int childID;
    public String childName;
    public int AutsmLevel;
    public int IqLevel;
    public int Perception;
    public int age;

    // var's for retrive plan resurse
    public ArrayList ResourcesList;
    public ArrayList SelectedSkillsList;


    public Plan (String child_Name) {
        PlanState = false;
        childID +=childID;
        childName = child_Name;

     }

    public Plan (ArrayList SelectedSkillsList) {
         fetchResources(SelectedSkillsList);
    }


    //return ***int***
    public int GenerateDelvlopmentPlan(String AutsmLevelSelected, String AgeSelected, String IqLevelSelected, String PerceptionSelected) {

        if (AutsmLevelSelected.equalsIgnoreCase("High function")) {
            AutsmLevel = 1;
        }
        IqLevel = Integer.parseInt(IqLevelSelected);
        age = Integer.parseInt(AgeSelected);
        switch (PerceptionSelected) {
            case "Low":
                Perception = 1;
                break;
            case "medium":
                Perception = 2;
                break;
            case "High":
                Perception = 3;
                break;
        }

        // link the model to get result for the plan
        Python mypython = Python.getInstance();
        PyObject pyobject = mypython.getModule("FANAR_Model.py"); // need to add the name of the model file
        PyObject obj = pyobject.callAttr( "main" , AutsmLevel, IqLevel, age, Perception);

        return Integer.parseInt(obj.toString());

    }

    public void fetchResources(ArrayList SelectedSkillsList ){

        //var's for database connection
          Connection CONNECTION  = null;
          String ip = "192.168.1.21";
          String userName = "FANAR";
          String Password = "qwer";
          String Port = "1433";
          String classes = "net.sourceforge.jtds.jdbc.Driver";
          String DataBase = "FANAR";
          String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;


        // fetch from dataset
        // establish ms sql server database connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String  test = null;

        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

            if (CONNECTION != null) {
                Statement statement = null;

                try {
                    statement = CONNECTION.createStatement();

                    ResultSet resultSet_skills;

                    // arraycontain the skills id  => SelectedSkillsList[0]
                    for (int i =0; i<=SelectedSkillsList.size();i++) {
                        //while through the array & fetch each resourse id and save it into another array
                        resultSet_skills = statement.executeQuery("Select Exercise from Resources where ExerciseID =" + SelectedSkillsList.get(i) + ";");
                        // level + let the frame return ids of the selected skills
                        // + make 2 array one contain the skills id & another that contain the
                        // resourse after fetch it from database

                        if (resultSet_skills != null)
                            ResourcesList.add(resultSet_skills.toString());

                        //end of the while loop
                    }

                    //end of second try
                } catch (SQLException throwables) {
                    throwables.printStackTrace();}

            } else{
                test ="con is null";  }

            //end of first try
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            test = "error!!!!";
            // show textbox show error
        }

    }


    public void GenerateDelvlopmentPlan(){

        //adjust plan

    }

    }
