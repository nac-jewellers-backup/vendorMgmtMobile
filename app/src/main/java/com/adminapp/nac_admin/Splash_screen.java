package com.adminapp.nac_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.adminapp.nac_admin.Home.Home_screen;
import com.adminapp.nac_admin.Login.Login_activity;

public class Splash_screen extends AppCompatActivity {

    ImageView imageview;
    private static int splash_timeout=3000;

    SharedPreferences sharedpre=null;
    SharedPreferences.Editor editor;

    String signupflag="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedpre=getSharedPreferences("Appsettings",MODE_PRIVATE);

        editor=sharedpre.edit();
        editor.commit();

        imageview=findViewById(R.id.splashimage);
        // imageview.setAnimation(fadeout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              checklogin_setup();

            }
        },splash_timeout);

    }

    private void checklogin_setup() {

        signupflag = sharedpre.getString("loginstatus","false");

        if(signupflag.equalsIgnoreCase("true"))
        {
            finish();
            startActivity(new Intent(Splash_screen.this, Home_screen.class)); //HomeActivity2
        }
        else{
            finish();
            startActivity(new Intent(Splash_screen.this, Login_activity.class)); //Login_Activity
        }
    }
}