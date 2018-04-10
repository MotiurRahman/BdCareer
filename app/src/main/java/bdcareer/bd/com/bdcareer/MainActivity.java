package bdcareer.bd.com.bdcareer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar proBar;
    private WebView bdcareer;
    public static String FACEBOOK_URL = "https://www.facebook.com/bdcareers/";
    public static String FACEBOOK_PAGE_ID = "683673531760837";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Check internet

        if (isNetworkConnected()) {

        } else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }

        // Webview
        bdcareer = (WebView) findViewById(R.id.web1);
        WebSettings webSettings = bdcareer.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Improve wevView performance

        bdcareer.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        bdcareer.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        bdcareer.getSettings().setAppCacheEnabled(true);
        bdcareer.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        bdcareer.setInitialScale(1);
        bdcareer.getSettings().setDisplayZoomControls(false);
        bdcareer.getSettings().setBuiltInZoomControls(true);
        // chakrirkhobor.setVerticalScrollBarEnabled(false);
        bdcareer.setHorizontalScrollBarEnabled(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);

        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);


        bdcareer.loadUrl("https://bd-career.org/");
        bdcareer.setWebViewClient(new mywebClient());

        proBar = (ProgressBar) findViewById(R.id.progressBar1);


        //Floting action button

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNetworkConnected()) {

                    Intent i = new Intent(Intent.ACTION_VIEW);

                    i.setData(Uri.parse("market://details?id=motiur_bdresult.bd.com.bdresult"));
                    startActivity(i);

                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
        MobileAds.initialize(this, "ca-app-pub-1090282204928094~4758004250");


        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    //For webview progress bar loading

    public class mywebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            proBar.setVisibility(View.GONE);
            //setTitle(view.getTitle());

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
            proBar.setVisibility(View.VISIBLE);
            //setTitle("Loading.....");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    //End webview progress bar


    //WebView back button

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_BACK:
//                    if (bdcareer.canGoBack()) {
//                        bdcareer.goBack();
//                    }
//                    return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //end WebView back button

    //For internet connection

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    //End internet connection


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (bdcareer.canGoBack()) {
            bdcareer.goBack();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //For menu view

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

        if (id == R.id.action_facebook) {

            if (isNetworkConnected()) {
                Intent facebookIntent = openFacebook(this);
                startActivity(facebookIntent);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


        }

        if (id == R.id.action_ratings) {

            if (isNetworkConnected()) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setData(Uri.parse("market://details?id=bdcareer.bd.com.bdcareer"));
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


        }


        if (id == R.id.update) {

            if (isNetworkConnected()) {
                Intent devAccount = new Intent(Intent.ACTION_VIEW);
                devAccount.setData(Uri.parse("market://details?id=bdcareer.bd.com.bdcareer"));
                startActivity(devAccount);
            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }


        }

        if (id == R.id.action_close) {

           finish();
            System.exit(0);


        }

        if (id == R.id.home) {

            if (isNetworkConnected()) {
                bdcareer.loadUrl("https://bd-career.org/");

            } else {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    //End menu view


    //Open facebook page

    public static Intent openFacebook(Context context) {


        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
            if (activated) {
                if ((versionCode >= 3002850)) {

                    return new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://facewebmodal/f?href=" + FACEBOOK_URL));

                } else {
                    return new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://page/" + FACEBOOK_PAGE_ID));

                }
            } else {
                return new Intent(Intent.ACTION_VIEW,
                        Uri.parse(FACEBOOK_URL));
            }
        } catch (PackageManager.NameNotFoundException e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(FACEBOOK_URL));
        }
    }

    //End opne facebook page

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hotJObs) {
            // Handle the camera action
            bdcareer.loadUrl("https://bd-career.org/category/hot-jobs/");
        } else if (id == R.id.govtJobs) {
            bdcareer.loadUrl("https://bd-career.org/category/government-jobs-circular/");

        } else if (id == R.id.bankJObs) {
            bdcareer.loadUrl("https://bd-career.org/category/bank-jobs/");

        } else if (id == R.id.ngo) {
            bdcareer.loadUrl("https://bd-career.org/category/ngodevelopment/");

        } else if (id == R.id.fullTimeJob) {
            bdcareer.loadUrl("https://bd-career.org/category/full-time-jobs/");

        } else if (id == R.id.medicalJob) {
            bdcareer.loadUrl("https://bd-career.org/category/medicalpharma/");

        } else if (id == R.id.defenceJob) {
            bdcareer.loadUrl("https://bd-career.org/category/defense-jobs/");

        } else if (id == R.id.ntrcaNotice) {
            bdcareer.loadUrl("https://bd-career.org/category/ntrca-teletalk/");

        } else if (id == R.id.jobResult) {
            bdcareer.loadUrl("https://bd-career.org/category/job-results/");

        } else if (id == R.id.jobNotice) {
            bdcareer.loadUrl("https://bd-career.org/category/job-notice/");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
