package com.example.dimitar.studenthub;

import android.app.Activity;
import android.content.Intent;

public class ChangeThemes {
    private static int selectedTheme;

    public final static int THEME_BLUE = 0;
    public final static int THEME_RED = 1;

    public static void changeToTheme(Activity activity, int theme)
    {

        activity.finish();
        //Problemyt sus zadavaneto na temata idva ot noviqt intent
        activity.startActivity(new Intent(activity, activity.getClass()));
        selectedTheme = theme;
    }

    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (selectedTheme)
        {
            case THEME_BLUE:
                activity.setTheme(R.style.BlueTheme);
                break;
            case THEME_RED:
                activity.setTheme(R.style.RedTheme);
                break;
        }
    }


}
