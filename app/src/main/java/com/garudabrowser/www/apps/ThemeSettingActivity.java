package com.garudabrowser.www.apps;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pixplicity.easyprefs.library.Prefs;

public class ThemeSettingActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_theme_setting);

        setupView();
    }

    private void setupView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Theme Setting");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnred = (Button)findViewById(R.id.btnred);
        Button btnblue = (Button)findViewById(R.id.btnblue);
        Button btngreen = (Button)findViewById(R.id.btngreen);
        Button btnpink = (Button)findViewById(R.id.btnpink);
        Button btnteal = (Button)findViewById(R.id.btnteal);
        Button btncyan = (Button)findViewById(R.id.btncyan);
        Button btnpurple = (Button)findViewById(R.id.btnpurple);
        Button btnorange = (Button)findViewById(R.id.btnorange);

        btnblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "biru");
                toolbar.setBackgroundColor(getResources().getColor(R.color.biru));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.biru));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_DEFAULT);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.biru));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.biru)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.biru), PorterDuff.Mode.MULTIPLY );
            }
        });

        btnred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "red");
                toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_red);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.red));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_red_dark)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY );
            }
        });

        btngreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "green");
                toolbar.setBackgroundColor(getResources().getColor(R.color.green));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.green));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_green);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.green));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green), PorterDuff.Mode.MULTIPLY );
            }
        });

        btncyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "cyan");
                toolbar.setBackgroundColor(getResources().getColor(R.color.cyan));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.cyan));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_cyan);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.cyan));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cyan)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.cyan), PorterDuff.Mode.MULTIPLY );
            }
        });

        btnpink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "pink");
                toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_pink);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.pink), PorterDuff.Mode.MULTIPLY );
            }
        });

        btnpurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "purple");
                toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_purple);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.purple), PorterDuff.Mode.MULTIPLY );
            }
        });

        btnorange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "orange");
                toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_orange);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.orange), PorterDuff.Mode.MULTIPLY );
            }
        });

        btnteal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString("warna", "teal");
                toolbar.setBackgroundColor(getResources().getColor(R.color.teal));
                MainActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.teal));
//                Utils.changeToTheme(ThemeSettingActivity.this, Utils.THEME_teal);
//                AboutActivity.toolbar.setBackgroundColor(getResources().getColor(R.color.teal));
                MainActivity.fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
                MainActivity.progressBar.setIndeterminate(true);
                MainActivity.progressBar.getIndeterminateDrawable()
                        .setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.teal), PorterDuff.Mode.MULTIPLY );
            }
        });

        cekToolbar();
    }

    private void cekToolbar() {
        String warna = Prefs.getString("warna","");
        if (warna.equalsIgnoreCase("biru")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.biru));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_DEFAULT);

        }else if (warna.equalsIgnoreCase("red")){
            toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_red);

        }else if (warna.equalsIgnoreCase("green")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_green);

        }else if (warna.equalsIgnoreCase("cyan")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.cyan));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_cyan);

        }else if (warna.equalsIgnoreCase("pink")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_pink);

        }else if (warna.equalsIgnoreCase("purple")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_purple);

        }else if (warna.equalsIgnoreCase("orange")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_orange);

        }else if (warna.equalsIgnoreCase("teal")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.teal));
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_teal);

        }
    }


}
