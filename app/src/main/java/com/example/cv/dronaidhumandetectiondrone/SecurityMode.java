package com.example.cv.dronaidhumandetectiondrone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SecurityMode extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mDatabase;
    private TextView date;
    private TextView time;
    private TextView longitude;
    private TextView latitude;
    private ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_mode);

        date = (TextView)findViewById(R.id.date1);
        time = (TextView)findViewById(R.id.time1);
        longitude = (TextView)findViewById(R.id.longitude1);
        latitude = (TextView)findViewById(R.id.latitude1);
        img = (ImageView) findViewById(R.id.img);


        mDatabase = FirebaseDatabase.getInstance().getReference("system");

        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mfirebaseDatabase.getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    notification uInfo = new notification();
                    uInfo.setDate(ds.child("noti").getValue(notification.class).getDate());
                    uInfo.setTime(ds.child("noti").getValue(notification.class).getTime());
                    uInfo.setLongitude(ds.child("noti").getValue(notification.class).getLongitude());
                    uInfo.setLatitude(ds.child("noti").getValue(notification.class).getLatitude());
                    uInfo.setMImage(ds.child("noti").getValue(notification.class).getMImage());
                    date.setText(uInfo.getDate());
                    time.setText(uInfo.getTime());
                    longitude.setText(uInfo.getLongitude());
                    latitude.setText(uInfo.getLatitude());
                    String image = uInfo.getMImage();
                    String base64Image = image.split(",")[1];
                    String add = "//Z";
                    base64Image = base64Image+add;

                    byte[] decodeString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodeo = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                    img.setImageBitmap(decodeo);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i=new Intent(SecurityMode.this,homeepage.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(SecurityMode.this,login.class);
            startActivity(i);

        }
        else if (id == R.id.nav_exit) {
            finishAffinity();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
