package com.example.fanarver3;

import java.util.ArrayList;

public class Specialist extends Home{

    // this must be saved in database ****
    public static ArrayList PlanList;


    public Specialist(String userID, int password, String email, String userName) {
        super(userID,password,email,userName);

    }

    public boolean AddToSPlist(Plan plan) {

        PlanList.add(plan);
        return true;
    }

    ;

    @Override
    public void joinToCommunity(int commuintyID) {

    }


}
