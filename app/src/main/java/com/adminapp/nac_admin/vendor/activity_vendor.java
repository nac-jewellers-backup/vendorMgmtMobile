package com.adminapp.nac_admin.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.adminapp.nac_admin.Admin.Adapter_Admin;
import com.adminapp.nac_admin.Admin.Admin_list;
import com.adminapp.nac_admin.Admin.Apiresponse_admin;
import com.adminapp.nac_admin.Admin.Apiresquest_admin;
import com.adminapp.nac_admin.Admin.activity_admin;
import com.adminapp.nac_admin.Admin.pass_sessiondata;
import com.adminapp.nac_admin.Admin.usermob;
import com.adminapp.nac_admin.R;
import com.adminapp.nac_admin.common.APP_API;
import com.adminapp.nac_admin.common.AlertDialog;
import com.adminapp.nac_admin.common.Baseurl;
import com.adminapp.nac_admin.common.Networkconnectivity;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_vendor extends AppCompatActivity {

    RecyclerView recycler_vendor;
    Adapter_vendor adapter;

    ProgressDialog progressdialog;
    AlertDialog alertDialog;
    Networkconnectivity conn;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    ArrayList<list_vendor> array_vendor=new ArrayList<>();

    String mobile,token;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        getSupportActionBar().setTitle("Vendor List");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recycler_vendor=findViewById(R.id.recycler_vendor);

        progressdialog=new ProgressDialog(activity_vendor.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        mobile=sharedPreferences.getString("mobile",null);
        token=sharedPreferences.getString("token",null);

        getvendorlist_fromserver();

    }

    private void getvendorlist_fromserver() {

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
                Apirequest_vendor requestdata=new Apirequest_vendor();

                Usermob_vendor mob=new Usermob_vendor();
                mob.setMobile_number(mobile);

                pass_sessionvendor session=new pass_sessionvendor();

                session.setUser(mob);
                session.setToken(token);

                requestdata.setSession(session);

                Call<Apiresponse_vendor> myCall = api.getvendor(requestdata);

                myCall.enqueue(new Callback<Apiresponse_vendor>() {
                    @Override
                    public void onResponse(Call<Apiresponse_vendor> call, Response<Apiresponse_vendor> response) {
                        hideProgressDialog();

                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {

                            if(response.body().getResult()!=null){

                                array_vendor=response.body().getResult();


                            }

                            adapter = new Adapter_vendor(activity_vendor.this,array_vendor);
                            recycler_vendor.setAdapter(adapter);

                            // recycler.setVisibility(View.VISIBLE);

                            layoutManager = new LinearLayoutManager(activity_vendor.this);
                            recycler_vendor.setLayoutManager(layoutManager);

                        }

                    }

                    @Override
                    public void onFailure(Call<Apiresponse_vendor> call, Throwable t) {
                        hideProgressDialog();
                        // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", t.getMessage());
                    }
                });


            }

            else {

                alertDialog.RounderCornerDialog(activity_vendor.this, "Alert", "Please check your connectivity");

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