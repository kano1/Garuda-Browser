package com.garudabrowser.www.apps;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.Locale;

public class PilihBahasaActivity extends AppCompatActivity {
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_bahasa);

        if (Prefs.getBoolean("Bahasa", false)){
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = Prefs.getString("LANG", "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        Button btnIndo = (Button)findViewById(R.id.btnIndo);
        Button btnInggris = (Button)findViewById(R.id.btnInggris);

        btnIndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putBoolean("Bahasa", true);
                Prefs.putString("LANG", "in");
                setLangRecreate("in");
            }
        });

        btnInggris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putBoolean("Bahasa", true);
                Prefs.putString("LANG", "en");
                setLangRecreate("en");
            }
        });

    }

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
}
