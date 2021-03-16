package com.example.fanarver3;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Parent extends Home{

    //get by query
    //set to update in the DB
    // COMMUINTY
    ResultSet rs;

    public Parent(String userID, String password, String email, String userName) {
        super(userID,password,email,userName);
    }

    @Override
    public String getUserID(String email) {
        String result = null;
        boolean finished = false;
        String q = "select ID from parent where email=" + "'" + email + "'";
        rs = sqlConn(q);
        try {
            if (rs.next())
                result = rs.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
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

    public  void setPlan (Plan p){
        //plan=new Plan();
    };

    public  void getPlan(boolean isApprove,URL url){
        if(isApprove){
            //show the plan
        }




    }

    @Override
    public void joinToCommunity(int commuintyID) {

    }



}
