package com.rudik_maksim.cde_material.controllers;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.rudik_maksim.cde_material.controllers.fragments.NavigationDrawerFragment;
import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.activities.AbstractActivity;
import com.rudik_maksim.cde_material.controllers.fragments.PointsFragment;
import com.rudik_maksim.cde_material.controllers.fragments.RatingsFragment;
import com.rudik_maksim.cde_material.controllers.fragments.RecordCdeFragment;
import com.rudik_maksim.cde_material.controllers.fragments.ScheduleFragment;
import com.rudik_maksim.cde_material.controllers.fragments.SessionFragment;
import com.rudik_maksim.cde_material.controllers.fragments.SettingsFragment;
import com.rudik_maksim.cde_material.modules.ApplicationPreferences;
import com.rudik_maksim.cde_material.modules.Authorization;
import com.rudik_maksim.cde_material.modules.Connection;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Points;
import com.rudik_maksim.cde_material.modules.Rating;
import com.rudik_maksim.cde_material.modules.Schedule;
import com.rudik_maksim.cde_material.modules.Session;
import com.rudik_maksim.cde_material.modules.User;
import com.rudik_maksim.cde_material.modules.parsers.PointParser;

import java.io.IOException;


public class PointsActivity extends AbstractActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    public final static int REQUEST_AUTHORIZATION = 1;
    public final static int REQUEST_UNAUTHORIZATION = 2;

    String TAG = "PointsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if (Global.sAuthorizationPreferences == null)
            Global.sAuthorizationPreferences = new ApplicationPreferences.AuthorizationPreferences(getApplicationContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_AUTHORIZATION){
            if (resultCode == RESULT_OK){
                // Авторизация прошла успешно
                getPointsScreen();
                Log.d(TAG, "AUTHORIZATION");
            }
        }

        if (requestCode == REQUEST_UNAUTHORIZATION){
            if (resultCode == RESULT_OK){
                // Изменяем пользователя
                getPointsScreen();
                Log.d(TAG, "UNAUTHORIZATION");
            }
        }
    }

    void getPointsScreen(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null){
            fm.beginTransaction()
                    .add(R.id.container, PointsFragment.newInstance(1))
                    .commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position){
            case Global.Configuration.NAV_POINTS:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PointsFragment.newInstance(position + 1))
                        .commit();
                break;
            case Global.Configuration.NAV_RATING:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RatingsFragment.newInstance(position + 1))
                        .commit();
                break;
            case Global.Configuration.NAV_RECORD_CDE:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RecordCdeFragment.newInstance(position + 1))
                        .commit();
                break;
            case Global.Configuration.NAV_SCHEDULE:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ScheduleFragment.newInstance(position + 1))
                        .commit();
                break;
            case Global.Configuration.NAV_SESSION_SCHEDULE:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SessionFragment.newInstance(position + 1))
                        .commit();
                break;
            case Global.Configuration.NAV_SETTINGS:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance(position + 1))
                        .commit();
                break;
            case Global.Configuration.NAV_EXIT:
                logout();
                break;
        }
    }

    private void logout() {
        Log.d(TAG, "logout");
        Global.sAuthorized = false;

        User.get().destruct();
        Points.get().destruct();
        Rating.get().destruct();
        Session.get().destruct();
        Schedule.get().destruct();

        if (Global.sAuthorizationPreferences != null){
            Global.sAuthorizationPreferences.clear();
            Global.sAuthorizationPreferences = null;
        }

        NavigationDrawerFragment.mCurrentSelectedPosition = 0;
        NavigationDrawerFragment.mUserLearnedDrawer = true;

        PointsFragment.clearListView();
        RatingsFragment.clearListView();

        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, REQUEST_UNAUTHORIZATION);
    }

    public void onSectionAttached(int number) {
        String[] titles = getResources().getStringArray(R.array.navigation_drawer_items);

        mTitle = titles[number - 1];
    }

    @SuppressWarnings("deprecation")
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
