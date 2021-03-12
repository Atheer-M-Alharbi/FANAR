package com.example.fanarver3;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Home {
    protected String userID;
    // need to change
    protected int password;
    protected int commuintyID;
    protected String email;
    protected String userName;
    private String userType;


    public Home(String userID, int password, String email, String userName) {
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getCommuintyID() {
        return commuintyID;
    }

    public void setCommuintyID(int commuintyID) {
        this.commuintyID = commuintyID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public abstract void joinToCommunity(int commuintyID);


    public void newParent(String userID, int password, String email, String userName) {
        Parent parent = new Parent(userID, password, email, userName);
    }

    public static ResultSet sqlConn(String query) {
        String ip = "192.168.1.21";
        String userName = "FANAR";
        String Password = "qwer";
        String Port = "1433";
        String classes = "net.sourceforge.jtds.jdbc.Driver";
        String DataBase = "FANAR";
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

}

