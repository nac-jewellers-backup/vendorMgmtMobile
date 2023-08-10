package com.adminapp.nac_admin.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adminapp.nac_admin.Admin.activity_admin;
import com.adminapp.nac_admin.R;
import com.adminapp.nac_admin.enquiry.activity_enquiry;
import com.adminapp.nac_admin.order.activity_order;
import com.adminapp.nac_admin.payment.activity_payment;
import com.adminapp.nac_admin.vendor.activity_vendor;
import com.google.android.material.navigation.NavigationView;

public class Home_screen extends AppCompatActivity {

    GridView grid_menu;
    Gridview_Adapter adapter;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView Nav_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_lay);
        Nav_drawer=findViewById(R.id.nav_drawer);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open,R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* grid_menu=findViewById(R.id.gridview);
        adapter=new Gridview_Adapter(Home_screen.this);

        grid_menu.setAdapter(adapter);

        grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0:
                          Intent intent=new Intent(Home_screen.this, activity_enquiry.class);
                          startActivity(intent);
                          break;

                    case 1:
                        Intent intent_ven=new Intent(Home_screen.this, activity_vendor.class);
                        startActivity(intent_ven);
                        break;

                    case 2:
                        Intent intent_order=new Intent(Home_screen.this, activity_order.class);
                        startActivity(intent_order);
                        break;

                    case 3:
                        Intent intent_payment=new Intent(Home_screen.this, activity_payment.class);
                        startActivity(intent_payment);
                        break;

                    case 4:
                        Intent intent_admin=new Intent(Home_screen.this, activity_admin.class);
                        startActivity(intent_admin);
                        break;
                }
            }
        });*/

        Nav_drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.admin:

                        Intent intent=new Intent(Home_screen.this,activity_admin.class);
                        startActivity(intent);
                        break;

                  /*  case R.id.home_activity:
                        return true;

                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), activity_task.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.logout:
                        MyAlertDialog alertDialog = new MyAlertDialog();
                        alertDialog.ShowDialog(homescreen_activity.this,"Are you sure do you want to logout?");*/
                }

                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}