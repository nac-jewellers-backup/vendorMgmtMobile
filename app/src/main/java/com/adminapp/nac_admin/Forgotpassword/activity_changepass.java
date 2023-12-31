package com.adminapp.nac_admin.Forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adminapp.nac_admin.Login.Login_activity;
import com.adminapp.nac_admin.R;
import com.adminapp.nac_admin.common.APP_API;
import com.adminapp.nac_admin.common.AlertDialog;
import com.adminapp.nac_admin.common.Baseurl;
import com.adminapp.nac_admin.common.Networkconnectivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_changepass extends AppCompatActivity {

    EditText edt_newpass,edt_confirmpass;
    TextView txt_submit;

    ProgressDialog progressdialog;
    AlertDialog alertDialog;
    Networkconnectivity conn;

    String userid;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        progressdialog=new ProgressDialog(activity_changepass.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        sharedPreferences=getSharedPreferences("Appsettings",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        userid= sharedPreferences.getString("userid",null);

      // Log.d("showmobile",mobileno);

        edt_newpass=findViewById(R.id.enternewpassword);
        edt_confirmpass=findViewById(R.id.enterconfirmpassword);
        txt_submit=findViewById(R.id.changepass_sub);

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValid(edt_newpass) && isValid(edt_confirmpass)){

                   if(!isValidPassword(edt_newpass.getText().toString()) && !isValidPassword(edt_newpass.getText().toString())){

                       alertDialog.RounderCornerDialog(activity_changepass.this, "Alert", "Invalid password, password must contains atleast one uppercase, special character and numeric");

                    }

                   if(edt_newpass.getText().toString().length()<8 && edt_newpass.getText().toString().length()>8){

                       alertDialog.RounderCornerDialog(activity_changepass.this, "Alert", "Password length should be 8 characters");

                   }

                    changepass_process();

                }

               else {
                    alertDialog.RounderCornerDialog(activity_changepass.this, "Alert", "Please fill out all the fields");

                }

            }
        });
    }

    private void changepass_process() {

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
                Apirequest_changepass user = new Apirequest_changepass();
                user.setPassword(edt_newpass.getText().toString());
                user.setTableName("nac_cms_admin");
                user.setId(userid);

                Call<Apiresponse_changepass> myCall = api.changepass(user);

                myCall.enqueue(new Callback<Apiresponse_changepass>() {
                    @Override
                    public void onResponse(Call<Apiresponse_changepass> call, Response<Apiresponse_changepass> response) {
                        hideProgressDialog();
                        if (response.body() != null && response.body().getStatus().equalsIgnoreCase("success") && response.body().getMessage().equalsIgnoreCase("Password changed successfully")) {

                            if(edt_newpass.getText().toString().equalsIgnoreCase(edt_confirmpass.getText().toString())){

                                RounderCornerDialog(activity_changepass.this, "Success", response.body().getMessage());

                            }

                            else {

                                RounderCornerDialog(activity_changepass.this, "Sorry", "Password doesn't match");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<Apiresponse_changepass> call, Throwable t) {
                        hideProgressDialog();
                        // alertDialog.RounderCornerDialog(Login_Activity.this, "Sorry", t.getMessage());
                    }
                });


            }

            else {

                alertDialog.RounderCornerDialog(activity_changepass.this, "Alert", "Please check your connectivity");

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

    public void RounderCornerDialog(Context context, String title, String Message)
    {
        final androidx.appcompat.app.AlertDialog alertDialog =  new MaterialAlertDialogBuilder(context,R.style.MyRounded_MaterialComponents_MaterialAlertDialog)  // for fragment you can use getActivity() instead of this
                .setView(R.layout.commonalertlayout) // custom layout is here
                .show();

        TextView txtletsgo = alertDialog.findViewById(R.id.txtcontinue);
        TextView txttitle = alertDialog.findViewById(R.id.txtpopup_title);
        TextView txtmessage = alertDialog.findViewById(R.id.txtpopup_message);
        ImageView imgalert = alertDialog.findViewById(R.id.imgalert);

        //txttitle.setText(title);

        String titleuppercase=title.substring(0,1).toUpperCase()+title.substring(1).toLowerCase();
        txttitle.setText(titleuppercase);

        //txtmessage.setText(Message);

        String messageuppercase=Message.substring(0,1).toUpperCase()+Message.substring(1).toLowerCase();
        txtmessage.setText(messageuppercase);


        if(title.equalsIgnoreCase("Success"))
        {
            //imgalert.setImageResource(R.drawable.tick);
            imgalert.setImageResource(R.drawable.success);
        }
        else if(title.equalsIgnoreCase("Sorry"))
        {
            txtletsgo.setText("Close");
            imgalert.setVisibility(View.GONE);
        }
        else if(title.equalsIgnoreCase("Alert")){
            txtletsgo.setText("Close");
            imgalert.setImageResource(R.drawable.failed);
        }
        else
        {
            txtletsgo.setText("Close");
            imgalert.setImageResource(R.drawable.failed);
            //txtletsgo.setText("let's get signed up");
        }

      //  alertDialog.setCanceledOnTouchOutside(false);
       // alertDialog.show();

        txtletsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // alertDialog.dismiss();
                Intent intent = new Intent(activity_changepass.this, Login_activity.class);
                finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });
    }

    private boolean isValid(EditText edt)
    {
        if(!edt.getText().toString().isEmpty())
            return true;
        else
            return false;
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

}