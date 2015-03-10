package com.rudik_maksim.cde_material.controllers.activities;

import android.support.v4.app.FragmentActivity;

/**
 * Created by maksimrudik on 26.02.15.
 */
public abstract class AbstractActivity extends FragmentActivity{
    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
        super.onDestroy();
        System.runFinalizersOnExit(true);
        System.exit(0);
    }
}
