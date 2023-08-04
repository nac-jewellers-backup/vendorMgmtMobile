package com.adminapp.nac_admin.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adminapp.nac_admin.R;
import com.adminapp.nac_admin.enquiry.activity_enquiry;
import com.adminapp.nac_admin.order.activity_order;
import com.adminapp.nac_admin.payment.activity_payment;
import com.adminapp.nac_admin.vendor.activity_vendor;

public class Home_screen extends AppCompatActivity {

    GridView grid_menu;
    Gridview_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        grid_menu=findViewById(R.id.gridview);
        adapter=new Gridview_Adapter(Home_screen.this);

        grid_menu.setAdapter(adapter);


        grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (position){

                    case 1:
                          Intent intent=new Intent(Home_screen.this, activity_enquiry.class);
                          startActivity(intent);
                          break;

                    case 2:
                        Intent intent_ven=new Intent(Home_screen.this, activity_vendor.class);
                        startActivity(intent_ven);
                        break;

                    case 3:
                        Intent intent_order=new Intent(Home_screen.this, activity_order.class);
                        startActivity(intent_order);
                        break;

                    case 4:
                        Intent intent_payment=new Intent(Home_screen.this, activity_payment.class);
                        startActivity(intent_payment);
                        break;
                }
            }
        });

    }
}