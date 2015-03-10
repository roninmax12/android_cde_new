package com.rudik_maksim.cde_material.modules;

import android.content.Context;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.items.SettingItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 04.03.15.
 */
public class Settings {
    Context mContext;
    ArrayList<SettingItem> mSettingsItems;
    static Settings sSettings;

    private Settings(Context context){
        mContext = context;
        mSettingsItems = new ArrayList<SettingItem>();

        loadSettings();
    }

    public static Settings getSettings(Context context){
        if (sSettings == null){
            sSettings = new Settings(context.getApplicationContext());
        }

        return sSettings;
    }

    public void loadSettings(){
        SettingItem semester = new SettingItem(
                mContext.getString(R.string.settings_title_semester),
                mContext.getString(R.string.settings_subtitle_semester),
                Global.sSettingsPreferences.getSemesterShowMode(),
                ApplicationPreferences.SettingsPreferences.ID_SEMESTER
        );

        SettingItem notification = new SettingItem(
                mContext.getString(R.string.settings_title_notification),
                mContext.getString(R.string.settings_subtitle_notification),
                Global.sSettingsPreferences.getNotification(),
                ApplicationPreferences.SettingsPreferences.ID_NOTIFICATION
        );

        SettingItem expand = new SettingItem(
                mContext.getString(R.string.settings_title_expand),
                mContext.getString(R.string.settings_subtitle_expand),
                Global.sSettingsPreferences.getProtocolExpand(),
                ApplicationPreferences.SettingsPreferences.ID_PROTOCOL
        );

        mSettingsItems.add(semester);
        mSettingsItems.add(notification);
        mSettingsItems.add(expand);
    }

    public ArrayList<SettingItem> getSettingsItems(){
        return mSettingsItems;
    }
}
