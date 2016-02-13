package com.example.dimitar.studenthub;

import android.content.Intent;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ChangeThemes.onActivityCreateSetTheme(this);
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

        } else if (id == R.id.nav_profile) {
                Intent intentProfile = new Intent("com.example.dimitar.studenthub.ProfileActivity");
                startActivity(intentProfile);
        } else if (id == R.id.nav_forum) {
            Intent intentForum = new Intent("com.example.dimitar.studenthub.ForumActivity");
            startActivity(intentForum);
        } else if (id == R.id.nav_library) {
            Intent intentLibrary = new Intent("com.example.dimitar.studenthub.LibraryActivity");
            startActivity(intentLibrary);
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
            Intent intentSettings = new Intent("com.example.dimitar.studenthub.SettingsActivity");
            startActivity(intentSettings);
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
