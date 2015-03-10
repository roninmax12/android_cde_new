package com.rudik_maksim.cde_material.modules;

import android.content.Context;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class Global {
    public static boolean sAuthorized = false;
    public static ApplicationPreferences.AuthorizationPreferences sAuthorizationPreferences = null;
    public static ApplicationPreferences.SettingsPreferences sSettingsPreferences = null;
    public static Context sApplicationContext = null;

    public static class Configuration{
        //Indexes of NavigationDrawer items
        public static final int NAV_POINTS = 0;
        public static final int NAV_PROTOCOL = 1;
        public static final int NAV_RATING = 2;
        public static final int NAV_RECORD_CDE = 3;
        public static final int NAV_SCHEDULE = 4;
        public static final int NAV_SESSION_SCHEDULE = 5;
        public static final int NAV_ATTESTATION_SCHEDULE = 6;
        public static final int NAV_SETTINGS = 7;
        public static final int NAV_EXIT = 8;
    }
}
