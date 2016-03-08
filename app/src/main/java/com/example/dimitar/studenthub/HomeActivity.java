package com.example.dimitar.studenthub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ForumFragment.OnClickSubjectItemListener,
        CategoriesBySubjectFragment.OnClickCategoryItemListener,
        ThreadsByCategoryFragment.OnClickThreadItemListener,
        LibraryFragment.OnClickNewCourseButtonListener
{
    FragmentManager fragmentManager;
    Fragment categoriesBySubjectFragment;
    Fragment threadsByCategoryFragment;
    Fragment singeThreadFragment;



    // Set addReverseTransaction to true if you want to go back to previous fragment on back press
    private void ChangeFragment(Fragment fragment, boolean addReverseTransaction)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_container, fragment);
        if (addReverseTransaction)
        {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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
        fragmentManager = getSupportFragmentManager();
        ForumModel.Initialize();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Fragment homeFragment;
        homeFragment = new HomeFragment();
        ChangeFragment(homeFragment, false);
    }



    @Override
    public void onClickSubjectItem(ParseObject subject)
    {
        categoriesBySubjectFragment = CategoriesBySubjectFragment.newInstance(subject);
        ChangeFragment(categoriesBySubjectFragment, true);
    }

    @Override
    public void onClickCategoryItem(ParseObject category)
    {
        threadsByCategoryFragment = ThreadsByCategoryFragment.newInstance(category);
        ChangeFragment(threadsByCategoryFragment, true);
    }

    @Override
    public void onClickThreadItem(ParseObject thread)
    {
        singeThreadFragment = SingleThreadFragment.newInstance(thread);
        ChangeFragment(singeThreadFragment, true);
    }

    @Override
    public void onClickNewCourseButton()
    {
        Fragment newLessonFragment;
        newLessonFragment = new MakeNewLessonFragment();
        ChangeFragment(newLessonFragment, true);
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
            Fragment homeFragment;
            homeFragment = new HomeFragment();
            ChangeFragment(homeFragment, true);
        } else if (id == R.id.nav_profile) {
            Fragment profileFragment;
            profileFragment = new ProfileFragment();
            ChangeFragment(profileFragment, true);
        } else if (id == R.id.nav_forum) {
            Fragment forumFragment;
            forumFragment = new ForumFragment();
            ChangeFragment(forumFragment, true);
        } else if (id == R.id.nav_library) {
            Fragment libraryFragment;
            libraryFragment = new LibraryFragment();
            ChangeFragment(libraryFragment, true);
        } else if (id == R.id.nav_message) {
            Intent intentMessage = new Intent("com.example.dimitar.studenthub.MessageActivity");
            startActivity(intentMessage);
        } else if (id == R.id.nav_tasks) {
            Intent intentTasks = new Intent("com.example.dimitar.studenthub.TasksActivity");
            startActivity(intentTasks);
        }
        else if (id == R.id.nav_diary) {
            Fragment diaryFragment;
            diaryFragment = new DiaryFragment();
            ChangeFragment(diaryFragment, true);
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
            ParseUser.getCurrentUser().logOut();
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
