package com.adminapp.nac_admin.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    String session,name;

    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        progressdialog=new ProgressDialog(activity_admin.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
        editor=sharedPreferences.edit();

       // session=sharedPreferences.getString("sessiondata",null);
        name=sharedPreferences.getString("name",null);
        Log.d("getname",name);

        recycler_admin=findViewById(R.id.recycler_admin);

        sessiondata getdata=new sessiondata();
        getdata.data();

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
                Apiresquest_admin requestdata = new Apiresquest_admin();
              //  user.setSession(getdata);
                requestdata.getSession().getUser().setMobile_number("453454534");
                requestdata.getSession().setToken("4346366363463634");

                Log.d("printreq",""+requestdata);
                Call<Apiresponse_admin> myCall = api.getadmin(requestdata);

                myCall.enqueue(new Callback<Apiresponse_admin>() {
                    @Override
                    public void onResponse(Call<Apiresponse_admin> call, Response<Apiresponse_admin> response) {

                        hideProgressDialog();
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

    public class sessiondata {

        SharedPreferences sharedPreferences = null;
        SharedPreferences.Editor editor;

        public void data(){

            sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
            editor=sharedPreferences.edit();

            sharedPreferences.getString("sessiondata",null);

            Log.d("data",sharedPreferences.getString("sessiondata",null));

        }

    }

}