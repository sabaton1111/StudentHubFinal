package com.example.dimitar.studenthub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static RadioButton radio_red;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        radio_red = (RadioButton)findViewById(R.id.radioButtonRedTheme);
        FloatingActionButton save = (FloatingActionButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(radio_red.isChecked())
                {
                    ChangeThemes.changeToTheme(SettingsActivity.this, ChangeThemes.THEME_RED);
                }
                else {ChangeThemes.changeToTheme(SettingsActivity.this, ChangeThemes.THEME_BLUE);}
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intentHome = new Intent("android.intent.action.MAIN");
            startActivity(intentHome);
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_forum) {

        } else if (id == R.id.nav_library) {

        } else if (id == R.id.nav_message) {
            Intent intentMessage = new Intent("com.example.dimitar.studenthub.MessageActivity");
            startActivity(intentMessage);
        } else if (id == R.id.nav_tasks) {
            Intent intentTasks = new Intent("com.example.dimitar.studenthub.TasksActivity");
            startActivity(intentTasks);
        }
        else if (id == R.id.nav_diary) {
            Intent intentDiary = new Intent("com.example.dimitar.studenthub.DiaryActivity");
            startActivity(intentDiary);
        }
        else if (id == R.id.nav_inquiry) {
            Intent intentInquiry = new Intent("com.example.dimitar.studenthub.InquiryActivity");
            startActivity(intentInquiry);
        }
        else if (id == R.id.nav_email) {
            Intent intentEmail = new Intent("com.example.dimitar.studenthub.EmailActivity");
            startActivity(intentEmail);
        }
        else if (id == R.id.nav_settings) {

        }
        else if (id == R.id.nav_help) {
            Intent intentHelp = new Intent("com.example.dimitar.studenthub.HelpActivity");
            startActivity(intentHelp);
        }
        else if (id == R.id.nav_exit) {
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
