package com.example.fanarver3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Parent extends Home{

    ResultSet rs;

    public Parent() {

    }

    public Parent(String userID, String password, String email, String userName) {
        super(userID,password,email,userName);
    }

    @Override
    public String getUserID(String email) throws SQLException {
        String result = null;
        String q = "select parentID from parent where email=" + "'" + email + "';";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }

    @Override
    public String getPassword(String userID) throws SQLException {
        String result = null;
        String q = "select Password from parent where parentID='"+userID+"';";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }

    @Override
    public void setPassword(String password,String userID) throws SQLException {

            String q = "UPDATE parent SET Password ='"+password+"' where ParentID='"+userID+"';";
            rs = sqlConn(q);
    }

    @Override
    public int getCommuintyID(String userID) throws SQLException {
        int result =0;
        System.out.println("userID **** parent class =" +userID);
        String q = "select * from parent where parentID='"+userID+"';";
        rs = sqlConn(q);
        rs.next();
        result = rs.getInt("CommunityID");
       // int commID=Integer.parseInt(result);
        System.out.println("getCommuintyID : "+result);
        //return commID;
        return result;
    }


    @Override
    public void setCommuintyID(int commuintyID,String userID) throws SQLException {
        //set the community id in parent table
        String q = "UPDATE parent SET CommunityID ="+commuintyID+" where parentID='"+userID+"';";
        rs = sqlConn(q);
    }

    @Override
    public void deleteCommuinty(String userID,int commuintyID) throws SQLException {
        //remove the community id in parent table
        String q = "UPDATE parent SET CommunityID ="+null+" where parentID='"+userID+"';";
        rs = sqlConn(q);
    }

    @Override
    public boolean isCommuintyMember(String userID) throws SQLException {
        if(getCommuintyID(userID)==0)
            return false;
        else
            return true;
    }

    @Override
    public String getEmail(String userID) throws SQLException {
        String result = null;
        String q = "select email from PARENT where PARENTID='"+userID+"';";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }


    @Override
    public String setEmail(String email,String userID) throws SQLException {
        email=email.toLowerCase();
        String output=null;
        int isemail = email.indexOf("@gmail.com");
        if (isemail == 1) {
            // check both tables if the user is exist
            String parentQuery = "SELECT Email FROM Parent WHERE ParentID ='" + userID + "';";
            String SpecialistQuery = "SELECT Email FROM Specialist WHERE SpecialistID ='" + userID + "';";
            rs = sqlConn(parentQuery);
            ResultSet rs2 = sqlConn(SpecialistQuery);
            //check if exist
            if (rs.next() || rs2.next()) {
                output="this email " + email + "is already taken";
            }else {
            String q = "UPDATE parent SET email ='" + email + "' WHERE ParentID ='" + userID + "';";
            rs = sqlConn(q);}
            output="Your email has been updated successfully";
        } else {
            output="incorrect email, it should be like this example: example@example.com";
        }
        return output;
    }

    @Override
    public String getUserName(String userID) throws SQLException {
            String result = null;
            String q = "select name from parent where ParentID='"+userID+"'";
            rs = sqlConn(q);
            result = rs.getString(1);
            return result;
    }

    @Override
    public void setUserName(String userName,String userID) {
        String q = "UPDATE parent SET name ='"+userName+"' where ParentID='"+userID+"'";
        rs = sqlConn(q);
    }

}
