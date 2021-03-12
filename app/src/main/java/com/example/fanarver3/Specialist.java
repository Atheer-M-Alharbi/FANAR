package com.example.fanarver3;

import java.util.ArrayList;

public class Specialist {

    // this must be saved in database ****
    public static ArrayList PlanList;


    public Specialist() {


    }

    public boolean AddToSPlist(Plan plan) {

        PlanList.add(plan);
        return true;
    }

    ;


}
