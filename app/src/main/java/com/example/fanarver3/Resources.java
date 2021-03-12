package com.example.fanarver3;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class Resources extends AppCompatActivity {


    public void ViewResources(String Resourceurl) {

        Uri url = Uri.parse(Resourceurl);
        Intent openWebpage = new Intent(Intent.ACTION_VIEW, url);
        startActivity(openWebpage);

    }

    ;

}


