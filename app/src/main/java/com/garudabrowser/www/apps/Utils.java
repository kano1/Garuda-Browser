package com.garudabrowser.www.apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nopal on 19/08/2017.
 */

public class Utils {
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_cyan = 1;
    public final static int THEME_blue = 2;
    public final static int THEME_green = 3;
    public final static int THEME_purple = 4;
    public final static int THEME_orange = 5;
    public final static int THEME_teal = 6;
    public final static int THEME_pink = 7;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(AppCompatActivity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(AppCompatActivity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.AppTheme);
                break;
            case THEME_cyan:
                activity.setTheme(R.style.AppTheme_cyan);
                break;
            case THEME_green:
                activity.setTheme(R.style.AppTheme_green);
                break;
            case THEME_pink:
                activity.setTheme(R.style.AppTheme_pink);
                break;
            case THEME_purple:
                activity.setTheme(R.style.AppTheme_purple);
                break;
            case THEME_orange:
                activity.setTheme(R.style.AppTheme_orange);
                break;
            case THEME_teal:
                activity.setTheme(R.style.AppTheme_teal);
                break;
            case THEME_blue:
                activity.setTheme(R.style.AppTheme_blue);
                break;
        }
    }
}
