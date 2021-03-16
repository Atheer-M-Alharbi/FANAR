package com.example.fanarver3;

import java.util.ArrayList;

public class Specialist extends Home{
    //get by query
    //set to update in the DB
    // COMMUINTY
    // ASSIG SSHILST

    // this must be saved in database ****
    public static ArrayList PlanList;


    public Specialist(String userID, String password, String email, String userName) {
        super(userID,password,email,userName);

    }

    @Override
    public String getUserID(String email) {
        String Q="select ID from parent where";
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

    public boolean AddToSPlist(Plan plan) {

        PlanList.add(plan);
        return true;
    }

    // remove

    @Override
    public void joinToCommunity(int commuintyID) {

    }


}
