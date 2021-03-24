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
        String q = "select ID from parent where email=" + "'" + email + "'";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }

    @Override
    public String getPassword(String userID) throws SQLException {
        String result = null;
        String q = "select Password from parent where ID='"+userID+"'";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }


    @Override
    public void setPassword(String password,String userID) throws SQLException {

            String q = "UPDATE parent SET Password ='"+password+"' where ID='"+userID+"'";
            rs = sqlConn(q);
    }

    @Override
    public int getCommuintyID(String userID) throws SQLException {
        int result = 0;
        String q = "select CommunityID from parent where ID='"+userID+"'";
        rs = sqlConn(q);
        result = rs.getInt(1);
        return result;
    }


    @Override
    public void setCommuintyID(int commuintyID,String userID) throws SQLException {
        //insert the user to communitymember table
        String q="Insert into communitymember values("+commuintyID+",null,'"+userID+"')";
        rs = sqlConn(q);
        //set the community id in parent table
         q = "UPDATE parent SET CommunityID ="+commuintyID+" where ID='"+userID+"'";
        rs = sqlConn(q);
        // get the total partspent in this Community
        q = "SELECT count(*) FROM communitymember where CommunityID="+commuintyID+";";
        rs = sqlConn(q);
        int memberNum=rs.getInt(1)+1;
        //increment the patispent number
        q = "UPDATE Community SET NumberOfMembers ="+memberNum+"' where CommunityID="+commuintyID+";";
        rs = sqlConn(q);
    }

    @Override
    public void deleteCommuinty(String userID,int commuintyID) throws SQLException {
        //delete the user to communitymember table
        String q="delete from communitymember where ID='"+userID+"'";
        rs = sqlConn(q);
        //remove the community id in parent table
        q = "UPDATE parent SET CommunityID ="+0+" where ID='"+userID+"'";
        rs = sqlConn(q);
        // get the total partspent in this Community
        q = "SELECT count(*) FROM communitymember where CommunityID="+commuintyID+";";
        rs = sqlConn(q);
        int memberNum=rs.getInt(1)-1;
        //decrement the patispent number
        q = "UPDATE Community SET NumberOfMembers ="+memberNum+"' where CommunityID="+commuintyID+";";
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
        String q = "select email from PARENT where ID='"+userID+"'";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }


    @Override
    public String setEmail(String email,String userID) throws SQLException {
        String output=null;
        int isemail = email.indexOf("@");
        int isemail2 = email.indexOf(".com");
        if ((isemail & isemail2) == 1) {
            // check both tables if the user is exist
            String parentQuery = "SELECT Email FROM Parent WHERE ID ='" + userID + "';";
            String SpecialistQuery = "SELECT Email FROM Specialist WHERE ID ='" + userID + "';";
            rs = sqlConn(parentQuery);
            ResultSet rs2 = sqlConn(SpecialistQuery);
            //check if exist
            if (rs.next() || rs2.next()) {
                output="this email " + email + "is already taken";
            }else {
            String q = "UPDATE parent SET email ='" + email + "' WHERE ID ='" + userID + "';";
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
            String q = "select name from parent where email=" + "'" + email + "'OR ID='"+userID+"'";
            rs = sqlConn(q);
            result = rs.getString(1);
            return result;
    }

    @Override
    public void setUserName(String userName,String userID) {
        String q = "UPDATE parent SET name ='"+userName+"' where email=" + "'" + email + "'OR ID='"+userID+"'";
        rs = sqlConn(q);
    }




}
