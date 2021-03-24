package com.example.fanarver3;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.SQLException;

public class Specialist extends Home {

    ResultSet rs;

    // this must be saved in database ****
    public static ArrayList<Plan> PlanList;

    public Specialist(){

    }

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
    public String getUserID(String email) throws SQLException {
        String result = null;
        String q = "select ID from Specialist where email='" + email + "'";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }

    @Override
    public String getPassword(String userID) throws SQLException {
        String result = null;
        String q = "select Password from Specialist where  ID='"+userID+"'";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }


    @Override
    public void setPassword(String password,String userID) throws SQLException {

        String q = "UPDATE Specialist SET Password ='"+password+"' where ID='"+userID+"'";
        rs = sqlConn(q);
    }

    @Override
    public int getCommuintyID(String userID) throws SQLException {
        int result = 0;
        String q = "select CommunityID from Specialist where  ID='"+userID+"'";
        rs = sqlConn(q);
        result = rs.getInt(1);
        return result;
    }

    @Override
    public void setCommuintyID(int commuintyID,String userID) throws SQLException {
        //insert the user to communitymember table
        String q="Insert into communitymember values("+commuintyID+",null,"+userID+"')";
        rs = sqlConn(q);
        //set the community id in parent table
        q = "UPDATE Specialist SET CommunityID ="+commuintyID+" where ID='"+userID+"'";
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
    public void deleteCommuinty(String userID, int commuintyID) throws SQLException {
        //delete the user to communitymember table
        String q="delete from communitymember where ID='"+userID+"'";
        rs = sqlConn(q);
        //remove the community id in parent table
        q = "UPDATE Specialist SET CommunityID ="+0+" where ID='"+userID+"'";
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
        String q = "select email from Specialist where ID='"+userID+"'";
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
                String q = "UPDATE Specialist SET email ='" + email + "' WHERE ID ='" + userID + "';";
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
        String q = "select name from Specialist where email=" + "'" + email + "'OR ID='"+userID+"'";
        rs = sqlConn(q);
        result = rs.getString(1);
        return result;
    }

    @Override
    public void setUserName(String userName,String userID) {
        String q = "UPDATE Specialist SET name ='"+userName+"' where email=" + "'" + email + "'OR ID='"+userID+"'";
        rs = sqlConn(q);
    }

}
