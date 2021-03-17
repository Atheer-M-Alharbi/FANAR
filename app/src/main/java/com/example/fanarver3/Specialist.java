package com.example.fanarver3;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.SQLException;

public class Specialist extends Home {
    //get by query
    //set to update in the DB
    // COMMUINTY
    // ASSIG SSHILST

    // this must be saved in database ****
    public static ArrayList<Plan> PlanList;


    public Specialist(String userID, String password, String email, String userName) {
        super(userID, password, email, userName);

    }

    public static void filllist(String id) {

        // check if this parent has plan or not
        String quiry = "SELECT planobj FROM ChildPlan WHERE SpecialistID  = "+ id + ";";  // <---('.')<---
        ResultSet rs = Home.sqlConn(quiry);
        try {
            if (rs.next() != false) {
                Plan.loadPlanFromdatabase(quiry, 0);
            }
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    public static void AssigSpTolist(int planid) {

        ArrayList Specialist = null;
        String quiry = "Select SpecialistID from Specialist;";

        ResultSet rs = Home.sqlConn(quiry);
        try {
            while (rs.next())
                Specialist.add(rs.getInt(0));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int random = (int) (Math.random() * Specialist.size() +1);
        if(Specialist.size()>0) {
            Home.sqlConn("UPDATE ChildPlan SET SpecialistID = "+Specialist.get(random)+"WHERE PlanID = "+planid+";");
        }else{

        }


}

    @Override
    public String getUserID(String email) {
        String Q = "select ID from parent where";
        return Q;
    }

    @Override
    public String getPassword(String userID, String email) {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public int getCommuintyID(String userID, String email) {
        return 0;
    }

    @Override
    public int getCommuintyID() {
        return 0;
    }

    @Override
    public void setCommuintyID(int commuintyID) {

    }

    @Override
    public String getEmail(String userID) {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public String getUserName(String userID, String email) {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public void setUserName(String userName) {

    }

    @Override
    public String getUserType() {
        return null;
    }


    @Override
    public void joinToCommunity(int commuintyID) {

    }


}
