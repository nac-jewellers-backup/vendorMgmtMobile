package com.adminapp.nac_admin.Forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class activity_enterotp extends AppCompatActivity {

    ProgressDialog progressdialog;
    AlertDialog alertDialog;
    Networkconnectivity conn;

    EditText edt_OTP;
    TextView txt_subOTP;

    String validateOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterotp);

        progressdialog=new ProgressDialog(activity_enterotp.this);
        alertDialog=new AlertDialog();
        conn=new Networkconnectivity(this);

        edt_OTP=findViewById(R.id.validateOTP);
        txt_subOTP=findViewById(R.id.btn_submitotp);

        Intent intent=getIntent();
        validateOTP=intent.getExtras().getString("validateOTP");

        txt_subOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid(edt_OTP)) {

                    if (edt_OTP.getText().toString().equalsIgnoreCase(validateOTP)) {

                        Intent intent = new Intent(activity_enterotp.this, activity_changepass.class);
                        finish();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else {

                        alertDialog.RounderCornerDialog(activity_enterotp.this, "Alert", "Invalid OTP");
                    }

                }
                else {

                    alertDialog.RounderCornerDialog(activity_enterotp.this, "Alert", "Please fill out the field");

                }
            }
        });
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