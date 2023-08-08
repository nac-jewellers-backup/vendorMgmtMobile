package com.adminapp.nac_admin.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adminapp.nac_admin.Forgotpassword.Entermobileno_activity;
import com.adminapp.nac_admin.Home.Home_screen;
import com.adminapp.nac_admin.R;
import com.adminapp.nac_admin.common.APP_API;
import com.adminapp.nac_admin.common.AlertDialog;
import com.adminapp.nac_admin.common.Baseurl;
import com.adminapp.nac_admin.common.Networkconnectivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_activity extends AppCompatActivity {

    EditText txt_mobileno,txtpassword;
    RelativeLayout loginlay;
    TextView txt_forgotpass;

    ProgressDialog progressdialog;
    AlertDialog alertDialog;
    Networkconnectivity conn;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressdialog=new ProgressDialog(Login_activity.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        Initializeview();

        loginlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValid(txt_mobileno) && isValid(txtpassword)){

                    Loginprocess();
                }

                else {

                    alertDialog.RounderCornerDialog(Login_activity.this, "Alert", "Please fill out all the fields");

                }

            }
        });

        txt_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Login_activity.this,Entermobileno_activity.class);
                finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

    private void Loginprocess() {

        try {
            if (conn.isConnectingToInternet()) {
                showProgressDialog();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                httpClient.connectTimeout(60, TimeUnit.SECONDS);
                httpClient.readTimeout(60, TimeUnit.SECONDS);
                httpClient.addInterceptor(logging);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Baseurl.baseurl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APP_API api = retrofit.create(APP_API.class);
                Apirequest_login user = new Apirequest_login();
                user.setMobile_number(txt_mobileno.getText().toString());
                user.setPassword(txtpassword.getText().toString());
                user.setTableName("nac_cms_admin");

                Call<Apiresponse_login> myCall = api.doLoginRequest(user);

                myCall.enqueue(new Callback<Apiresponse_login>() {
                    @Override
                    public void onResponse(Call<Apiresponse_login> call, Response<Apiresponse_login> response) {

                        hideProgressDialog();
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                            editor.putString("mobile",response.body().getSession().getUser().getMobile_number());
                            editor.putString("token",response.body().getSession().getToken());
                            editor.putString("loginstatus","true");
                            editor.commit();

                            Intent intent=new Intent(Login_activity.this, Home_screen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                        else {
                            // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", response.body().getMessage());
                        }
                    }


                    @Override
                    public void onFailure(Call<Apiresponse_login> call, Throwable t) {
                        hideProgressDialog();
                        // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", t.getMessage());
                    }
                });


            }

            else {

                alertDialog.RounderCornerDialog(Login_activity.this, "Alert", "Please check your connectivity");

            }
        }

            catch (Exception e)
        {
            hideProgressDialog();
            //alertDialog.RounderCornerDialog(Login_Activity.this, "Error", "Something went wrong");
            e.printStackTrace();
        }

            }

    private void Initializeview() {

        txt_mobileno=findViewById(R.id.mobileno);
        txtpassword=findViewById(R.id.password);
        loginlay=findViewById(R.id.loginbtn_lay);
        txt_forgotpass=findViewById(R.id.forgot_password);

    }

    private void showProgressDialog() {
        try
        {
            progressdialog.setIndeterminate(true);
            progressdialog.setMessage("Processing");
            progressdialog.show();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
    private void hideProgressDialog()
    {

        //progressDailaog.hide();
        progressdialog.dismiss();
    }

    private boolean isValid(EditText edt)
    {
        if(!edt.getText().toString().isEmpty())
            return true;
        else
            return false;
    }
    }
