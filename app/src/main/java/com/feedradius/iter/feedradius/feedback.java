package com.feedradius.iter.feedradius;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class feedback extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String DEFAULT = "N/A";
    public  static TextView data;
    TextView name1;
    TextView post1;
    View hView;

    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        /////////////////////////////////////
        data = (TextView) findViewById(R.id.fetcheddata);
        fetchData process = new fetchData();
        process.execute();
        ////////////////////////////////////////
        SharedPreferences sharedPreferences1 = getSharedPreferences("MyData",MODE_PRIVATE);
        String name = sharedPreferences1.getString("name",DEFAULT);
        String post = sharedPreferences1.getString("post",DEFAULT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(),compose.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationViewHeader = navigationView.getHeaderView(0);
        ((TextView) navigationViewHeader.findViewById(R.id.name1)).setText(name);
        ((TextView) navigationViewHeader.findViewById(R.id.post1)).setText(post);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.feedbacks) {

        }  else if (id == R.id.profile) {
            Intent intent = new Intent(getApplication(),profile.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(getApplication(),aboutus.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.log_out) {
            Intent intent = new Intent(getApplication(),homeActivity.class);
            startActivity(intent);
            SharedPreferences sharedPreferences = getSharedPreferences("MyData",MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("eid",DEFAULT);
            editor.putString("pass",DEFAULT);
            editor.putString("name",DEFAULT);
            editor.putString("email",DEFAULT);
            editor.putString("phno",DEFAULT);
            editor.putString("nof",DEFAULT);
            editor.commit();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
