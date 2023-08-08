package com.adminapp.nac_admin.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.adminapp.nac_admin.Home.Home_screen;
import com.adminapp.nac_admin.Login.Apirequest_login;
import com.adminapp.nac_admin.Login.Apiresponse_login;
import com.adminapp.nac_admin.Login.Login_activity;
import com.adminapp.nac_admin.R;
import com.adminapp.nac_admin.common.APP_API;
import com.adminapp.nac_admin.common.AlertDialog;
import com.adminapp.nac_admin.common.Baseurl;
import com.adminapp.nac_admin.common.Networkconnectivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_admin extends AppCompatActivity {

    RecyclerView recycler_admin;
    Adapter_Admin adapter;

    ProgressDialog progressdialog;
    AlertDialog alertDialog;
    Networkconnectivity conn;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    ArrayList<Admin_list> array_admin=new ArrayList<>();

    String mobile,token;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setTitle("Admin List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recycler_admin=findViewById(R.id.recycler_admin);

        progressdialog=new ProgressDialog(activity_admin.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        mobile=sharedPreferences.getString("mobile",null);
        token=sharedPreferences.getString("token",null);

        Log.d("mobileno",mobile);
        Log.d("token",token);


        try {
            if (conn.isConnectingToInternet()) {
                showProgressDialog();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                Log.d("level1","level1");

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

                httpClient.connectTimeout(60, TimeUnit.SECONDS);
                httpClient.readTimeout(60, TimeUnit.SECONDS);
                httpClient.addInterceptor(logging);
                Log.d("level2","level2");

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Baseurl.baseurl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Log.d("level3","level3");

                APP_API api = retrofit.create(APP_API.class);
                Apiresquest_admin requestdata = new Apiresquest_admin();

                usermob mob = new usermob();
                mob.setMobile_number(mobile);

                pass_sessiondata sess = new pass_sessiondata();
                sess.setUser(mob);
                sess.setToken(token);

                requestdata.setSession(sess);

                Log.d("reqdata",""+requestdata.toString());
                Call<Apiresponse_admin> myCall = api.getadmin(requestdata);

                myCall.enqueue(new Callback<Apiresponse_admin>() {
                    @Override
                    public void onResponse(Call<Apiresponse_admin> call, Response<Apiresponse_admin> response) {
                        hideProgressDialog();

                        Log.d("response1",response.body().getStatus());

                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                            Log.d("response",response.body().getStatus());

                            if(response.body().getResult()!=null){

                                array_admin=response.body().getResult();

                                Log.d("list",response.body().getResult().toString());

                            }

                            adapter = new Adapter_Admin(activity_admin.this,array_admin);
                            recycler_admin.setAdapter(adapter);

                           // recycler.setVisibility(View.VISIBLE);

                            layoutManager = new LinearLayoutManager(activity_admin.this);
                            recycler_admin.setLayoutManager(layoutManager);

                        }

                    }

                    @Override
                    public void onFailure(Call<Apiresponse_admin> call, Throwable t) {
                        hideProgressDialog();
                        // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", t.getMessage());
                    }
                });


            }

            else {

                alertDialog.RounderCornerDialog(activity_admin.this, "Alert", "Please check your connectivity");

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();

                //Toast.makeText(this,"Back button pressed!",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


}