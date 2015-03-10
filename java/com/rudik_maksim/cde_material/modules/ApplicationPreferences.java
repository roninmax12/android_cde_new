package com.rudik_maksim.cde_material.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.rudik_maksim.cde_material.modules.items.SettingItem;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class ApplicationPreferences {
    public final static String TAG = "ApplicationPreferences";

    public static class AuthorizationPreferences {
        Context mContext;
        SharedPreferences mSharedPreferences;
        String mAuthorizationPreferencesName;
        String TAG_LOGIN = "login";
        String TAG_PASSWORD = "password";

        public AuthorizationPreferences(Context context){
            mContext = context;
            mAuthorizationPreferencesName = "authorization";
            mSharedPreferences = mContext.getSharedPreferences(mAuthorizationPreferencesName, Context.MODE_PRIVATE);
        }

        public void put(final String login, final String password){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();

                    editor.putString(TAG_LOGIN, login);
                    editor.putString(TAG_PASSWORD, password);

                    editor.commit();
                }
            }).start();
        }

        public boolean exists(){
            String login = mSharedPreferences.getString(TAG_LOGIN, "");
            String password = mSharedPreferences.getString(TAG_PASSWORD, "");

            if (login.equals("") || password.equals(""))
                return false;

            return true;
        }

        public String getLogin(){
            return mSharedPreferences.getString(TAG_LOGIN, "");
        }

        public String getPassword(){
            return mSharedPreferences.getString(TAG_PASSWORD, "");
        }

        public void clear(){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }

    public static class SettingsPreferences{
        Context mContext;
        SharedPreferences mSharedPreferences;
        SharedPreferences.Editor mEditor;
        String mSettingsPreferencesName;

        String TAG_PROTOCOL_EXPAND = "protocol_expand";
        String TAG_NOTIFICATION = "notification";
        String TAG_CURRENT_SEMESTER_SHOW = "current_semester";

        public static final int ID_PROTOCOL = 0;
        public static final int ID_NOTIFICATION = 1;
        public static final int ID_SEMESTER = 2;

        public SettingsPreferences(Context context, String login){
            mContext = context;
            mSettingsPreferencesName = login + "_settings";
            mSharedPreferences = mContext.getSharedPreferences(mSettingsPreferencesName, Context.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }

        public void putProtocolExpand(boolean expand){
            Log.d(TAG, "putProtocolExpand");
            mEditor.remove(TAG_PROTOCOL_EXPAND);
            mEditor.putBoolean(TAG_PROTOCOL_EXPAND, expand).apply();
        }

        public void putNotification(boolean notify){
            Log.d(TAG, "putNotification");
            mEditor.remove(TAG_NOTIFICATION);
            mEditor.putBoolean(TAG_NOTIFICATION, notify).apply();
        }

        public void putSemesterShowMode(boolean onlyCurrent){
            Log.d(TAG, "putSemester");
            mEditor.remove(TAG_CURRENT_SEMESTER_SHOW);
            mEditor.putBoolean(TAG_CURRENT_SEMESTER_SHOW, onlyCurrent).apply();
        }

        public void save(final SettingItem item){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    switch (item.getId()){
                        case ID_PROTOCOL:
                            putProtocolExpand(item.isChecked());
                            break;
                        case ID_NOTIFICATION:
                            putNotification(item.isChecked());
                            break;
                        case ID_SEMESTER:
                            putSemesterShowMode(item.isChecked());
                            break;
                    }
                }
            }).start();
        }

        public boolean getProtocolExpand(){
            return mSharedPreferences.getBoolean(TAG_PROTOCOL_EXPAND, true);
        }

        public boolean getNotification(){
            return mSharedPreferences.getBoolean(TAG_NOTIFICATION, true);
        }

        public boolean getSemesterShowMode(){
            return mSharedPreferences.getBoolean(TAG_CURRENT_SEMESTER_SHOW, true);
        }

        public void inverseAndSave(SettingItem item){
            item.setChecked(inverse(item.isChecked()));
            save(item);
        }

        private boolean inverse(boolean current){
            return !current;
        }

        public void clear(){
            mEditor.clear();
            mEditor.apply();
        }
    }
}
