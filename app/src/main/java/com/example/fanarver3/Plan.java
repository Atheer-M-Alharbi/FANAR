package com.example.fanarver3;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.fanarver3.Specialist.PlanList;

public class Plan implements Parcelable, Serializable {

    //  plan:
    //  1. view content & exercise in parent (done) & sp ( done )

    //  2. progress evaluation ( done )

    //  3. plan id in sp home screen(done)

    //  4. complete the needed quiry  (only left CHILDPLAN) (done)
    //  then assain which specialist to plan...(linked with reem part)

    // today task
    // if the application closed then opend or user login.. his plan must appear fetch from data base (done)
    // 5. in parent plan take parent id and check if he has plan and if it approved or not to set the sutabil view!!!!!!! (done)

    // review code (done)
    // 6. chose together the catagory name to store it in database & but in layout. (last step before testing)
    // make sure ever change on plan is updated ( fetch from db --> deserilized --> make change ---> serilise and store again)

    // var's for child info
    public int Planlevel;
    public boolean PlanState;
    public int childID = 1;
    public int PlanID = 10;
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
        childID = childID + 1;
        PlanID = PlanID + 1;
        childName = child_Name;
        ResourcesList = new ArrayList();
        SelectedSkillsList = new ArrayList();

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

    public int GenerateDelvlopmentPlan(String AutsmLevelSelected, String AgeSelected, String IqLevelSelected, String PerceptionSelected, String Parent_user_ID) {

        if (AutsmLevelSelected.equalsIgnoreCase("High function")) {
            AutsmLevel = 1;
            this.setAutsmLevel(AutsmLevel);
        }
        IqLevel = Integer.parseInt(IqLevelSelected);
        this.setIqLevel(IqLevel);
        age = Integer.parseInt(AgeSelected);
        this.setAge(age);
        switch (PerceptionSelected) {
            case "Low":
                Perception = 1;
                this.setPerception(Perception);
                break;
            case "medium":
                Perception = 2;
                this.setPerception(Perception);
                break;
            case "High":
                Perception = 3;
                this.setPerception(Perception);
                break;
        }

        // link the model to get result for the plan
        Python mypython = Python.getInstance();
        final PyObject pyobject = mypython.getModule("M"); // need to add the name of the model file
        PyObject obj = pyobject.callAttr("main", AutsmLevel, IqLevel, age, Perception);

        Planlevel = Integer.parseInt(obj.toString());
        this.setPlanlevel(Planlevel);
        // save child info in database
        insetINTOdatabase(Parent_user_ID);

        return Integer.parseInt(obj.toString());

    }

    public void fetchResources(ArrayList SelectedSkills) throws SQLException {

        this.setSelectedSkillsList(SelectedSkills);

        ResultSet resultSet_skills;
        ResultSet resultSet;

        // array contain the skills id  => SelectedSkillsList[0]
        for (int i = 0; i < this.getSelectedSkillsList().size(); i++) {
            //while through the array & fetch each resourse id and save it into another array
            resultSet_skills = Home.sqlConn("Select ExerciseID from Resources where Category  ='" + this.getSelectedSkillsList().get(i) + "' AND PlanLevel  ='" + this.getPlanlevel() + "';");
            while (resultSet_skills.next()) {
                ResourcesList.add(resultSet_skills.getString("ExerciseID"));
            }
            for (int x = 0; x < 4; x++) {
                //while through the array & fetch each resourse id and save it into another array
                resultSet = Home.sqlConn("INSERT INTO Exercise (Planid, Category, ExerciseID)" +
                        " VALUES (" + this.getPlanID() + ",'" + SelectedSkillsList.get(i).toString() + "'," + ResourcesList.get(x).toString() + ");");
            }
        }

        savePlanINTOdatabase(this);
    }


    /// called from GenerateDelvlopmentPlan to save child info + need Parentid ***
    public void insetINTOdatabase(String Parent_user_ID) {

        ResultSet resultSet_skills = Home.sqlConn("INSERT INTO Child (ChildID,ChildName, autismLevel, ChildAge, IqLevel , Perception, ParentID )" +
                "VALUES ('" + this.getChildID() + "','" + this.getChildName() + "','" + this.getAutsmLevel() + "'," + this.getAge() + "," + this.getIqLevel() + ",'" + this.getPerception() + "','" + Parent_user_ID + "');");

    }

    // need it when sp change state...
    public void savePlanINTOdatabase(Plan plan) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(baos);
            oout.writeObject(plan);
            oout.flush();
            oout.close();
            byte[] myObjBytes = baos.toByteArray();
            Home.sqlConn("UPDATE ChildPlan SET planobj = " + myObjBytes.hashCode() + " WHERE PlanID = " + plan.getPlanID() + ";");
            Log.d("debug7", "array second loop " + myObjBytes.hashCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Plan loadPlanFromdatabase(String quiry, int choise) throws SQLException, IOException, ClassNotFoundException {

        ResultSet rs = Home.sqlConn(quiry);
        Plan PrentPLAN = null;
        ObjectInputStream objectIn = null;
        switch (choise) {
            case 0:
                while (rs.next()) {
                    byte[] buf = rs.getBytes("planobj");
                    if (buf != null) {
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                        Object obj = objectIn.readObject();
                        Plan p = (Plan) obj;
                        PlanList.add(p);

                    }
                }
                break;
            case 1:
                // PARENT HAS ONLY ONE PLAN
                byte[] buf = rs.getBytes("planobj");
                if (buf != null) {
                      objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                    Object obj = objectIn.readObject();
                    PrentPLAN = (Plan) obj;
                }
                break;
        }
        objectIn.close();
        return PrentPLAN;
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


    public int getPlanlevel() {
        return Planlevel;
    }

    public void setPlanlevel(int planlevel) {
        Planlevel = planlevel;
    }

    public boolean isPlanState() {
        return PlanState;
    }

    public void setPlanState(boolean planState) {
        PlanState = planState;
    }

    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getPlanID() {
        return PlanID;
    }

    public void setPlanID(int planID) {
        PlanID = planID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getAutsmLevel() {
        return AutsmLevel;
    }

    public void setAutsmLevel(int autsmLevel) {
        AutsmLevel = autsmLevel;
    }

    public int getIqLevel() {
        return IqLevel;
    }

    public void setIqLevel(int iqLevel) {
        IqLevel = iqLevel;
    }

    public int getPerception() {
        return Perception;
    }

    public void setPerception(int perception) {
        Perception = perception;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList getResourcesList() {
        return ResourcesList;
    }

    public void setResourcesList(ArrayList resourcesList) {
        ResourcesList = resourcesList;
    }

    public ArrayList getSelectedSkillsList() {
        return SelectedSkillsList;
    }

    public void setSelectedSkillsList(ArrayList selectedSkillsList) {
        SelectedSkillsList = selectedSkillsList;
    }

}
