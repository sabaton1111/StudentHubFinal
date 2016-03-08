package com.example.dimitar.studenthub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.app.SearchManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ForumFragment.OnClickSubjectItemListener,
        CategoriesBySubjectFragment.OnClickCategoryItemListener,
        ThreadsByCategoryFragment.OnClickThreadItemListener,
        LibraryFragment.OnClickNewCourseButtonListener,
        CategoriesBySubjectFragment.OnClickNewCategoryButtonListener,
        ThreadsByCategoryFragment.OnClickNewThreadButtonListener,
        SingleThreadFragment.OnClickNewPostButtonListener,
        LibraryFragment.OnClickLessonOpenListener,
        HomeFragment.OnClickForumOpenListener,
        HomeFragment.OnClickLessonsOpenListener
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

        ImageButton menuOpen = (ImageButton)findViewById(R.id.action_up_btn);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        menuOpen.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v)
            {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

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
        newLessonFragment = new MakeNewLesson();
        ChangeFragment(newLessonFragment, true);
    }
            @Override
            public void OnClickLessonOpen()
            {
                Fragment lesson;
                lesson = new LessonFragment();
                ChangeFragment(lesson, true);
            }
    @Override
    public void onClickNewCategoryButton()
    {
        Fragment newCategoryFragment;
        newCategoryFragment = new NewCategoryFragment();
        ChangeFragment(newCategoryFragment, true);
    }
            @Override
            public void OnClickForumOpenButton()
            {
                Fragment newFragment;
                newFragment = new ForumFragment();
                ChangeFragment(newFragment, true);
            }
            @Override
            public void OnClickLessonsOpenButton()
            {
                Fragment newLessons;
                newLessons = new LibraryFragment();
                ChangeFragment(newLessons, true);
            }
@Override
public void onClickNewThreadButton()
        {
        Fragment newThreadFragment;
        newThreadFragment = new NewThreadFragment();
        ChangeFragment(newThreadFragment, true);
        }

            @Override
            public void onClickNewPostButton() {
                Fragment newPostFragment;
                newPostFragment = new NewPostFragment();
                ChangeFragment(newPostFragment, true);
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
            Fragment messageFragment;
            messageFragment = new MessageFragment();
            ChangeFragment(messageFragment, true);
        } else if (id == R.id.nav_tasks) {
            Fragment tasksFragment;
            tasksFragment = new TasksFragment();
            ChangeFragment(tasksFragment, true);
        }
        else if (id == R.id.nav_diary) {
            Fragment diaryFragment;
            diaryFragment = new DiaryFragment();
            ChangeFragment(diaryFragment, true);
        }
        else if (id == R.id.nav_inquiry) {
            Fragment inquiryFragment;
            inquiryFragment = new InquiryFragment();
            ChangeFragment(inquiryFragment, true);
        }
        else if (id == R.id.nav_email) {
            Fragment emailFragment;
            emailFragment = new EmailNewFragment();
            ChangeFragment(emailFragment, true);
        }
        else if (id == R.id.nav_settings) {
            Fragment settingsFragment;
            settingsFragment = new SettingsFragment();
            ChangeFragment(settingsFragment, true);
        }
        else if (id == R.id.nav_help) {
            Fragment helpFragment;
            helpFragment = new LessonFragment();
            ChangeFragment(helpFragment, true);
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
