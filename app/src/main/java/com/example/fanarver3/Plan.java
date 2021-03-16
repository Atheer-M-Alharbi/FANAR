package com.example.fanarver3;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Plan implements Parcelable {

    //  plan:
    //  1. view content & exercise ( in parent & sp) (   )
    //  2. progress evaluation (   )
    //  3. save list's to database ( make sure every thing is  saved to database so user will find everything if he open app again)(   )
    //  4. just don't forget to add parent id in quiry(   )  + plan id in sp home screen(done)


    // var's for child info
    public int Planlevel;
    public boolean PlanState;
    public int childID;
    public String childName;
    public int AutsmLevel;
    public int IqLevel;
    public int Perception;
    public int age;

    // var's for retrive plan resurse
    // this must be saved in database ****
    public ArrayList ResourcesList;
    public ArrayList SelectedSkillsList;


    public Plan(String child_Name) {
        PlanState = false;
        childID += childID;
        childName = child_Name;

    }


    protected Plan(Parcel in) {
        Planlevel = in.readInt();
        PlanState = in.readByte() != 0;
        childID = in.readInt();
        childName = in.readString();
        AutsmLevel = in.readInt();
        IqLevel = in.readInt();
        Perception = in.readInt();
        age = in.readInt();
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

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
        final PyObject pyobject = mypython.getModule("M"); // need to add the name of the model file
        PyObject obj = pyobject.callAttr("main", AutsmLevel, IqLevel, age, Perception);

        Planlevel = Integer.parseInt(obj.toString());

        // save child info in database
        insetINTOdatabase();

        return Integer.parseInt(obj.toString());

    }

    public void fetchResources(ArrayList SelectedSkills) {

        this.SelectedSkillsList = SelectedSkills;
        //var's for database connection
        Connection CONNECTION = null;
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
        String test = null;

        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

            if (CONNECTION != null) {
                Statement statement = null;

                try {
                    statement = CONNECTION.createStatement();

                    ResultSet resultSet_skills;

                    // array contain the skills id  => SelectedSkillsList[0]
                    for (int i = 0; i <= SelectedSkillsList.size(); i++) {
                        //while through the array & fetch each resourse id and save it into another array
                        resultSet_skills = statement.executeQuery("Select Exercise from Resources where ExerciseID =" + SelectedSkillsList.get(i) + " AND DifficultyLevel =" + this.Planlevel + ";");

                        if (resultSet_skills != null)
                            ResourcesList.add(resultSet_skills.toString());

                        //end of the for loop
                    }

                    //end of second try
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                test = "con is null";
            }

            //end of first try
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            test = "error!!!!";
            // show textbox show error
        }


    }


    /// called from GenerateDelvlopmentPlan to save child info + need Parentid
    public void insetINTOdatabase() {

        //var's for database connection
        Connection CONNECTION = null;
        String ip = "192.168.1.21";
        String userName = "FANAR";
        String Password = "qwer";
        String Port = "1433";
        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String DataBase = "FANAR";
        String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;

        // establish ms sql server database connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ResultSet resultSet_skills;
        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

            if (CONNECTION != null) {
                Statement statement = null;

                try {
                    statement = CONNECTION.createStatement();
                    resultSet_skills = statement.executeQuery("INSERT INTO Child(ChildID,ChildName, autismLevel, ChildAge, IqLevel , Perception, ParentID )" +
                            "VALUES (" + childID + "," + childName + "," + AutsmLevel + "," + age + "," + IqLevel + "," + Perception);//+","+Parentid+");");

                    //end of second try
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                // show textbox show error
            }

            //end of first try
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // show textbox show error
        }


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Planlevel);
        parcel.writeByte((byte) (PlanState ? 1 : 0));
        parcel.writeInt(childID);
        parcel.writeString(childName);
        parcel.writeInt(AutsmLevel);
        parcel.writeInt(IqLevel);
        parcel.writeInt(Perception);
        parcel.writeInt(age);
    }
}
