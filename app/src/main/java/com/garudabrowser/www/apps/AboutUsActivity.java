package com.garudabrowser.www.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pixplicity.easyprefs.library.Prefs;

public class AboutUsActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        toolbar.setBackgroundColor(getResources().getColor(R.color.red));
        cekToolbar();
    }

    private void cekToolbar() {
        String warna = Prefs.getString("warna","");
        if (warna.equalsIgnoreCase("biru")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.biru));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_DEFAULT);
        }else if (warna.equalsIgnoreCase("red")){
            toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_red);
        }else if (warna.equalsIgnoreCase("green")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_green);
        }else if (warna.equalsIgnoreCase("cyan")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.cyan));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_cyan);
        }else if (warna.equalsIgnoreCase("pink")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_pink);
        }else if (warna.equalsIgnoreCase("purple")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_purple);
        }else if (warna.equalsIgnoreCase("orange")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_orange);
        }else if (warna.equalsIgnoreCase("teal")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.teal));
//            Utils.changeToTheme(AboutUsActivity.this, Utils.THEME_teal);
        }
    }
}
