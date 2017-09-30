package com.garudabrowser.www.apps;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebIconDatabase;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView wv;
    public static Toolbar toolbar;

    public static ProgressBar progressBar;

    private List<Link> historyStack;

    private ArrayAdapter<Link> dialogArrayAdapter;

    ImageView btnStop;
    ImageView btnReload;
    ImageView btnBack;
    ImageView btnHome;
    ImageView btnFoward;
    public static FloatingActionButton fab;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack(); //method goback() dieksekusi untuk kembali pada halaman sebelumnya
            return true;
        }
        // Jika tidak ada history (Halaman yang sebelumnya dibuka)
        // maka akan keluar dari activity
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
////        Utils.onActivityCreateSetTheme(this);
//        Configuration config = getBaseContext().getResources().getConfiguration();
//        Toast.makeText(this, ""+config, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        historyStack = new LinkedList<Link>();
        setSupportActionBar(toolbar);
        btnStop = (ImageView)findViewById(R.id.btnStop);
         btnReload = (ImageView)findViewById(R.id.btnReload);
         btnBack = (ImageView)findViewById(R.id.btnBack);
         btnFoward = (ImageView)findViewById(R.id.btnFoward);
         btnHome = (ImageView)findViewById(R.id.btnHome);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        cekPermission();
        cekTema();
        setupwebView();
        setupNavDrawer();
        setupFooter();
        setupAds();
        fab.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_search).actionBar().color(Color.WHITE));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Search");
                alert.setMessage("Please Insert Url Or Keyword for Search in Google");

                TableLayout.LayoutParams params = new TableLayout.LayoutParams();
                params.setMargins(R.dimen._15sdp, R.dimen._5sdp, R.dimen._15sdp, R.dimen._5sdp);
// Set an EditText view to get user input
                final EditText input = new EditText(MainActivity.this);
                input.setLayoutParams(params);
                alert.setView(input);

                alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        String valuee;
                        if (value.contains(".")){
                            valuee = "http://" + value;
                        }else{
                            valuee = "https://www.google.com/search?q=" + value;
                        }

                        dialog.dismiss();
                        wv.loadUrl(valuee);
                    }
                });

                alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });
    }

    private void setupAds() {
        MobileAds.initialize(MainActivity.this, "ca-app-pub-9043865407906531~1965886972");

        AdView adView = (AdView) findViewById(R.id.myBannerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void cekPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

// Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        11);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void setupwebView() {
        wv=(WebView)findViewById(R.id.webview);
        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).getPath());

        // javascript and zoom
        wv.getSettings().setJavaScriptEnabled(true);
//        wv.getSettings().setBuiltInZoomControls(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
        {
            wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        }
        else
        {
            //IMPORTANT!! this method is no longer available since Android 4.3
            //so the code doesn't compile anymore
//            webview.getSettings().setPluginsEnabled(true);
        }

        // downloads
        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long l) {
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                DownloadManager DM = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                String cookies = CookieManager.getInstance().getCookie(url);
//                request.setMimeType(mimeType);
//                request.addRequestHeader("cookies", cookies);
//                request.addRequestHeader("uerAgent", userAgent);
//                String data_file = URLUtil.guessFileName(url, contentDisposition, mimeType);
//                request.setTitle(data_file);
//                request.setAllowedOverRoaming(false);
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI|DownloadManager.Request.NETWORK_MOBILE);
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.allowScanningByMediaScanner();
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + "/GARUDABROWSER/Downloads", data_file);
//                if (DM != null) {
//                    DM.enqueue(request);
//                }
//                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        wv.setWebViewClient(new CustomWebViewClient());

        wv.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                progressBar.setProgress(0);
                FrameLayout progressBarLayout = (FrameLayout) findViewById(R.id.progressBarLayout);
                progressBarLayout.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if (progress == 100)
                {
                    progressBarLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                MainActivity.this.setTitle(getString(R.string.app_name));
                for(Link link : historyStack)
                {
                    if (link.getUrl().equals(MainActivity.this.wv.getUrl()))
                    {
                        link.setTitle(title);
                    }
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon)
            {
                view.getUrl();
                boolean b = false;
                ListIterator<Link> listIterator = historyStack.listIterator();
                while (!b && listIterator.hasNext())
                {
                    Link link = listIterator.next();
                    if (link.getUrl().equals(view.getUrl()))
                    {
                        link.setFavicon(icon);
                        b = true;
                    }
                }
            }

        });

        //http://stackoverflow.com/questions/2083909/android-webview-refusing-user-input
        wv.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus())
                        {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }

        });


        // Welcome page loaded from assets directory
        if (Locale.getDefault().getLanguage().equals("es"))
        {
            wv.loadUrl("https://www.google.com");
        }
        else
        {
            wv.loadUrl("https://www.google.com");
        }

        wv.requestFocus();
    }

    private void setupNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        MenuItem nav_input_beritaItem = menu.findItem(R.id.nav_home);
        nav_input_beritaItem .setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_home).actionBar());
        MenuItem nav_logoutItem = menu.findItem(R.id.nav_history);
        nav_logoutItem.setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_download).actionBar());
        MenuItem nav_aboutus = menu.findItem(R.id.nav_aboutus);
        nav_aboutus.setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_users).actionBar());
        MenuItem nav_contactus = menu.findItem(R.id.nav_contactus);
        nav_contactus.setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_whatsapp).actionBar());
        MenuItem nav_theme = menu.findItem(R.id.nav_themesetting);
        nav_theme.setIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_cog).actionBar());
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_screenshoot) {
            takeScreenshot();
            return true;
        }else if (id == R.id.action_share) {
            share();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cekTema() {
        String warna = Prefs.getString("warna","");
        if (warna.equalsIgnoreCase("biru")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.biru));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.biru), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_DEFAULT);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.biru)));
        }else if (warna.equalsIgnoreCase("red")){
            toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_red);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_red_dark)));
        }else if (warna.equalsIgnoreCase("green")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.green), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_green);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        }else if (warna.equalsIgnoreCase("cyan")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.cyan));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.cyan), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_cyan);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.cyan)));
        }else if (warna.equalsIgnoreCase("pink")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.pink));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.pink), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_pink);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
        }else if (warna.equalsIgnoreCase("purple")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.purple));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.purple), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_purple);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
        }else if (warna.equalsIgnoreCase("orange")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.orange));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_orange);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        }else if (warna.equalsIgnoreCase("teal")){
            toolbar.setBackgroundColor(getResources().getColor(R.color.teal));
            progressBar.setIndeterminate(true);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(this, R.color.teal), PorterDuff.Mode.MULTIPLY );
//            Utils.changeToTheme(MainActivity.this, Utils.THEME_teal);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
        }
    }

    private void setupFooter() {

        btnStop.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_search).sizeRes(R.dimen._25sdp).actionBar());
        btnReload.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_refresh).sizeRes(R.dimen._25sdp).actionBar());
        btnBack.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_fast_backward).sizeRes(R.dimen._25sdp).actionBar());
        btnFoward.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_fast_forward).sizeRes(R.dimen._25sdp).actionBar());
        btnHome.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_home).sizeRes(R.dimen._25sdp).actionBar());

    }

    private void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = wv.getUrl();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Garuda Browser");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/GARUDABROWSER/Screenshots/";

            File mainDir = new File(mPath);

            //If File is not present create directory
            if (!mainDir.exists()) {
                if (mainDir.mkdir())
                    Log.e("Create Directory", "Main Directory Created : " + mainDir);
            }
            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
//            File imageFile = new File(mPath);
//            if (!imageFile.exists()) {
//                imageFile.mkdir();
//            }
//            FileOutputStream outputStream = new FileOutputStream(imageFile);
//            int quality = 100;
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//            outputStream.flush();
//            outputStream.close();


            MediaScannerConnection.scanFile(this,
                    new String[]{mainDir.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
            File saveFile = ScreenUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenUtils.store(bitmap, now + "GARUDABROWSER" + ".jpg", mainDir);//save the screenshot to selected path
            openScreenshot(file);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            wv.loadUrl("https://www.google.com");
        } else if (id == R.id.nav_aboutus) {
            Intent i = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_contactus) {
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=+6285536587828"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            startActivity(intent);

        } else if (id == R.id.nav_themesetting) {
            Intent i = new Intent(MainActivity.this, ThemeSettingActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_history) {
            showDialog(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    class CustomWebViewClient extends WebViewClient
    {
        // the current WebView will handle the url
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest url)
        {

            return super.shouldOverrideUrlLoading(view, url);
        }

        // history and navigation buttons
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            if (checkConnectivity())
            {
                //resets favicon
                // shows the current url
//                MainActivity.this.urlEditText.setText(url);

                //only one occurrence
                boolean b = false;
                ListIterator<Link> listIterator = historyStack.listIterator();
                while (listIterator.hasNext() && !b)
                {
                    if (listIterator.next().getUrl().equals(url))
                    {
                        b = true;
                        listIterator.remove();
                    }
                }
                Link link = new Link(url, favicon);
                historyStack.add(0, link);

                btnStop.setEnabled(true);
                updateButtons();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            btnStop.setEnabled(false);
            updateButtons();
        }

        // handles unrecoverable errors
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(description).setPositiveButton((R.string.ok), null).setTitle("onReceivedError");
            builder.show();
        }

    }

    class CustomDownloadListener implements DownloadListener {
        public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(getString(R.string.download));
            builder.setMessage(getString(R.string.question));
            builder.setCancelable(false).setPositiveButton((R.string.ok), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    new DownloadAsyncTask().execute(url);
                }

            }).setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.cancel();
                }
            });

            builder.create().show();

        }

    }

    private class DownloadAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg0)
        {
            String result = "";
            String urlString = arg0[0];

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try
                {

                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    inputStream = connection.getInputStream();

                    String fileName = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/webviewdemo";
                    File directory = new File(fileName);
                    File file = new File(directory, urlString.substring(urlString.lastIndexOf("/")));
                    directory.mkdirs();

                    // commons-io, I miss you :(
                    fileOutputStream = new FileOutputStream(file);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;

                    while (inputStream.available() > 0 && (len = inputStream.read(buffer)) != -1)
                    {
                        byteArrayOutputStream.write(buffer, 0, len);
                    }

                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    fileOutputStream.flush();

                    result = getString(R.string.result) + file.getAbsolutePath();
                }
                catch (Exception ex)
                {
                    Log.e(MainActivity.class.toString(), ex.getMessage(), ex);
                    result = ex.getClass().getSimpleName() + " " + ex.getMessage();
                }
                finally
                {
                    if (inputStream != null)
                    {
                        try
                        {
                            inputStream.close();
                        }
                        catch (IOException ex)
                        {
                            Log.e(MainActivity.class.toString(), ex.getMessage(), ex);
                            result = ex.getClass().getSimpleName() + " " + ex.getMessage();
                        }
                    }
                    if (fileOutputStream != null)
                    {
                        try
                        {
                            fileOutputStream.close();
                        }
                        catch (IOException ex)
                        {
                            Log.e(MainActivity.class.toString(), ex.getMessage(), ex);
                            result = ex.getClass().getSimpleName() + " " + ex.getMessage();
                        }
                    }
                }
            }
            else
            {
                result = getString(R.string.nosd);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(result).setPositiveButton((R.string.ok), null).setTitle(getString(R.string.download));
            builder.show();

        }

    }

    /**
     * Checks networking status.
     */
    private boolean checkConnectivity() {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable()))
        {
            enabled = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage(getString(R.string.noconnection));
            builder.setCancelable(false);
            builder.setNeutralButton(R.string.ok, null);
            builder.setTitle(getString(R.string.error));
            builder.create().show();
        }
        return enabled;
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.history));
        builder.setPositiveButton(getString(R.string.clear), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                historyStack.clear();
            }
        });

        builder.setNegativeButton(R.string.close, null);

        dialogArrayAdapter = new ArrayAdapter<Link>(this, R.layout.history, historyStack)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                //holder pattern
                LinkHolder holder = null;
                if (convertView == null)
                {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.history, null);
                    holder = new LinkHolder();
                    holder.setUrl((TextView) convertView.findViewById(R.id.textView1));
                    holder.setImageView((ImageView) convertView.findViewById(R.id.favicon));

                    convertView.setTag(holder);
                }
                else
                {
                    holder = (LinkHolder) convertView.getTag();
                }

                Link link = historyStack.get(position);
                //show title when available
                if (link.getTitle() != null)
                {
                    holder.getUrl().setText(link.getTitle());
                }
                else
                {
                    holder.getUrl().setText(link.getUrl());
                }
                Bitmap favicon = link.getFavicon();
                if (favicon == null)
                {
                    holder.getImageView().setImageDrawable(super.getContext().getResources().getDrawable(R.mipmap.ic_launcher));
                }
                else
                {
                    holder.getImageView().setImageBitmap(favicon);
                }

                return convertView;
            }
        };

        builder.setAdapter(dialogArrayAdapter, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                wv.loadUrl(historyStack.get(item).getUrl());
                btnStop.setEnabled(true);
            }

        });

        return builder.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        dialogArrayAdapter.notifyDataSetChanged();
        super.onPrepareDialog(id, dialog);
    }

    private void updateButtons(){

        if (wv.canGoBack())
        {
            btnBack.setEnabled(true);
        }
        else
        {
            btnBack.setEnabled(false);
        }

        if (wv.canGoForward())
        {
            btnFoward.setEnabled(true);
        }
        else
        {
            btnFoward.setEnabled(false);
        }
    }
}