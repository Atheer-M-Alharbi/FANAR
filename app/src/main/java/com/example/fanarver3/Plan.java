package com.example.fanarver3;


import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;

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
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.fanarver3.Specialist.PlanList;

public class Plan implements Parcelable {

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

    public void fetchResources(ArrayList SelectedSkills) throws SQLException {

        this.setSelectedSkillsList(SelectedSkills);

        ResultSet resultSet_skills;
        ResultSet resultSet;

        // array contain the skills id  => SelectedSkillsList[0]
        for (int i = 0; i <= SelectedSkills.size(); i++) {
            // need modification
            //while through the array & fetch each resourse id and save it into another array
            resultSet_skills = Home.sqlConn("Select ExerciseID from Resources where Category  =" + SelectedSkillsList.get(i) + " AND PlanLevel  =" + this.Planlevel + ";");
            while (resultSet_skills.next()) {
                ResourcesList.add(resultSet_skills.getString("ExerciseID"));
            }
        }

        for (int i = 0; i <= ResourcesList.size(); i++) {
            //while through the array & fetch each resourse id and save it into another array
            resultSet = Home.sqlConn("INSERT INTO Exercise (Planid, ExersiseID, Category)" +
                    "VALUES (" + this.getPlanID() + "," + ResourcesList.get(i) + "," + "," + SelectedSkillsList.get(i) + ";");
        }

        savePlanINTOdatabase(this);
    }


    /// called from GenerateDelvlopmentPlan to save child info + need Parentid ***
    public void insetINTOdatabase() {

        ResultSet resultSet_skills = Home.sqlConn("INSERT INTO Child(ChildID,ChildName, autismLevel, ChildAge, IqLevel , Perception, ParentID )" +
                "VALUES (" + childID + "," + childName + "," + AutsmLevel + "," + age + "," + IqLevel + "," + Perception);//+","+Parentid+");");

    }

    // need it when sp change state...
    public void savePlanINTOdatabase(Plan plan) {

        Connection con = Home.connection();
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("UPDATE ChildPlan SET planobj = ? WHERE PlanID = " + plan.getPlanID() + ";");
            write(plan, ps);
            ps.execute();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Plan loadPlanFromdatabase(String s, int choise) throws SQLException, IOException, ClassNotFoundException {

        Connection con = Home.connection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(s);
        Plan PrentPLAN = null;
        switch (choise) {
            case 0:
                while (rs.next()) {
                    Object obj = read(rs, "planobj");
                    Plan p = (Plan) obj;
                    PlanList.add(p);
                }
                rs.close();
                st.close();
                break;
            case 1:
                // PARENT HAS ONLY ONE PLAN
                Object obj = read(rs, "planobj");
                PrentPLAN = (Plan) obj;
                rs.close();
                st.close();
                break;
        }
        return PrentPLAN;
    }

    public static Object read(ResultSet rs, String column) throws SQLException,
            IOException, ClassNotFoundException {
        byte[] buf = rs.getBytes(column);
        if (buf != null) {
            ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            return objectIn.readObject();
        }
        return null;
    }


    public static void write(Object obj, PreparedStatement ps) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(baos);
            oout.writeObject(obj);
            oout.close();
            ps.setBytes(1, baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
