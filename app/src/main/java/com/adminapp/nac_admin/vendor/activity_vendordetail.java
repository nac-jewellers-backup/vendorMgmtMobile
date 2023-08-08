package com.adminapp.nac_admin.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.adminapp.nac_admin.R;

public class activity_vendordetail extends AppCompatActivity {

    TextView txt_email,txt_contactno,txt_conatctper,txt_PAN,txt_GST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendordetail);

        getSupportActionBar().setTitle("Vendor details");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txt_email=findViewById(R.id.vendor_email);
        txt_contactno=findViewById(R.id.vendor_contactno);
        txt_conatctper=findViewById(R.id.vendor_contactperson);
        txt_PAN=findViewById(R.id.vendor_PAN);
        txt_GST=findViewById(R.id.vendor_GST);

        Intent intent=getIntent();
       txt_email.setText("Email"+" : "+intent.getExtras().getString("email"));
       txt_contactno.setText("Contact Number"+" : "+intent.getExtras().getString("contact_number"));
       txt_conatctper.setText("Contact Person"+" : "+intent.getExtras().getString("contact_person")); ;
       txt_PAN.setText("PAN"+" : "+intent.getExtras().getString("PAN"));
       txt_GST.setText("GST"+" : "+intent.getExtras().getString("GST"));


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