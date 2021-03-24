package com.example.fanarver3;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Home {

    protected String userID;
    // need to change
    protected String password;
    protected int commuintyID;
    protected String email;
    protected String userName;
    private String userType;

    public Home() {
    }

    public Home(String userID, String password, String email, String userName) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.userType = userType;
    }

    public abstract String getUserID(String email) throws SQLException;

    public abstract String getPassword(String userID) throws SQLException;

    public abstract void setPassword(String password, String userID) throws SQLException;

    public abstract int getCommuintyID(String userID) throws SQLException;

    public abstract void setCommuintyID(int commuintyID, String userID) throws SQLException;

    public abstract void deleteCommuinty(String userID,int commuintyID) throws SQLException;

    public abstract boolean  isCommuintyMember(String userID) throws SQLException;

    public abstract String getEmail(String userID) throws SQLException;

    public abstract String setEmail(String email,String userID) throws SQLException;

    public abstract String getUserName(String userID) throws SQLException;

    public abstract void setUserName(String userName,String userID);

    public static   String getUserType(String userID){
        char t=userID.charAt(0);
        if (t==0)
            return "parent";
        else
            return "Specialist";

    }

    public static ResultSet sqlConn(String query) {
        String ip = "192.168.1.21";
        String userName = "FANAR";
        String Password = "qwer";
        String Port = "1433";
        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String DataBase = "FANARDB";
        String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;
        Connection CONNECTION = null;
        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        if (CONNECTION != null) {
            Statement statement = null;
            try {
                statement = CONNECTION.createStatement();
                 resultSet = statement.executeQuery(query);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return resultSet;
    }

    public static Connection connection() {

        String ip = "192.168.1.21";
        String Port = "1433";

        String userName = "FANAR";
        String Password = "qwer";

        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String DataBase = "FANARDB";
        String url = "jdbc:jtds:sqlserver://" + ip + ":" + Port + "/" + DataBase;

        Connection CONNECTION = null;
        try {
            Class.forName(classes);
            CONNECTION = DriverManager.getConnection(url, userName, Password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return CONNECTION;
    }
}

