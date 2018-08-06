package com.mickaelbrenoit.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class NavigationDrawerActivity extends AppCompatActivity {

    private static final String TAG = "NavigationDrawerActivit";

    @Nullable
    @BindView(R.id.drawerLayoutNav)
    DrawerLayout drawerLayoutNav;
    @Nullable
    @BindView(R.id.frameLayoutNav)
    FrameLayout frameLayoutNav;
    @Nullable
    @BindView(R.id.navigationViewNav)
    NavigationView navigationViewNav;
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_navigation_drawer);
        ButterKnife.bind(this);

        drawerLayoutNav = findViewById(R.id.drawerLayoutNav);
        frameLayoutNav = findViewById(R.id.frameLayoutNav);
        navigationViewNav = findViewById(R.id.navigationViewNav);
        toolbar = findViewById(R.id.toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutNav, 0, 0);
        drawerLayoutNav.setDrawerListener(actionBarDrawerToggle);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (frameLayoutNav != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, frameLayoutNav, false);
            frameLayoutNav.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (frameLayoutNav != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayoutNav.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (frameLayoutNav != null) {
            frameLayoutNav.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_english:
                Log.d(TAG, "changeLanguageToEnglish: in");
                Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_french:
                Log.d(TAG, "changeLanguageToFrench: in");
                Toast.makeText(getApplicationContext(), "French", Toast.LENGTH_LONG).show();
            break;

            case R.id.nav_clear_database:
                Log.d(TAG, "clearDatabase: in");
                Toast.makeText(getApplicationContext(), "Clear database", Toast.LENGTH_LONG).show();
            break;

            case R.id.nav_logout:
                Log.d(TAG, "logout: in");
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            break;
        }
    }
//    @Optional
//    @OnClick(R.id.nav_english)
//    public void changeLanguageToEnglish(View v) {
//        Log.d(TAG, "changeLanguageToEnglish: in");;
//        Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_LONG).show();
//    }
//
//    @Optional
//    @OnClick(R.id.nav_french)
//    public void changeLanguageToFrench(View v) {
//        Log.d(TAG, "changeLanguageToFrench: in");
//        Toast.makeText(getApplicationContext(), "French", Toast.LENGTH_LONG).show();
//    }
//
//    @Optional
//    @OnClick(R.id.nav_clear_database)
//    public void clearDatabase(View v) {
//        Log.d(TAG, "clearDatabase: in");
//        Toast.makeText(getApplicationContext(), "Clear database", Toast.LENGTH_LONG).show();
//    }
//
//    @Optional
//    @OnClick(R.id.nav_logout)
//    public void logout(View v) {
//        Log.d(TAG, "logout: in");
//        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
//    }
}
