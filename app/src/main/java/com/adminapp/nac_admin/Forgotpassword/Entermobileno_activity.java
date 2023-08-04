package com.adminapp.nac_admin.Forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.adminapp.nac_admin.Login.Apirequest_login;
import com.adminapp.nac_admin.Login.Apiresponse_login;
import com.adminapp.nac_admin.Login.Login_activity;
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

public class Entermobileno_activity extends AppCompatActivity {

    EditText edt_mobileno;
    TextView txt_sub;

    ProgressDialog progressdialog;
    AlertDialog alertDialog;
    Networkconnectivity conn;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entermobileno_activity);

        edt_mobileno=findViewById(R.id.entermobileno);
        txt_sub=findViewById(R.id.btn_sub);

        progressdialog=new ProgressDialog(Entermobileno_activity.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        txt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValid(edt_mobileno)) {

                    verifymobile();
                }

                else {

                    alertDialog.RounderCornerDialog(Entermobileno_activity.this, "Alert", "Please fill out all the fields");
                }

            }
        });
    }

    private void verifymobile() {

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
                Apirequest_getotp user = new Apirequest_getotp();
                user.setMobile_number(edt_mobileno.getText().toString());
                user.setTableName("nac_cms_admin");

                Call<Apiresponse_getotp> myCall = api.getotp(user);

                myCall.enqueue(new Callback<Apiresponse_getotp>() {
                    @Override
                    public void onResponse(Call<Apiresponse_getotp> call, Response<Apiresponse_getotp> response) {

                        hideProgressDialog();
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success") && response.body().getMessage().equalsIgnoreCase("User found")) {
                       // editor = sharedPreferences.edit();
                      //  editor.putString("loginstatus","true");

                        editor.putString("userid", response.body().getData().getId());
                       // editor.putString("usermail",edtemail.getText().toString().trim());

                        editor.commit();
                        //alertDialog.RounderCornerDialog(Login_Activity.this, "success", response.body().getMessage());

                                Intent intent = new Intent(Entermobileno_activity.this, activity_enterotp.class);
                                intent.putExtra("validateOTP",response.body().getData().getOtp());
                                finish();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                        }
                   /* else if(response.body()!=null && response.body().getStatus().equalsIgnoreCase("failure")){
                       // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", response.body().getMessage());
                    }*/
                        else {
                            // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", response.body().getMessage());
                        }
                    }


                    @Override
                    public void onFailure(Call<Apiresponse_getotp> call, Throwable t) {
                        hideProgressDialog();
                        // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", t.getMessage());
                    }
                });


            }

            else {

                alertDialog.RounderCornerDialog(Entermobileno_activity.this, "Alert", "Please check your connectivity");

            }
        }

        catch (Exception e)
        {
            hideProgressDialog();
            //alertDialog.RounderCornerDialog(Login_Activity.this, "Error", "Something went wrong");
            e.printStackTrace();
        }


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