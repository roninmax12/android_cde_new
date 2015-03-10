package com.rudik_maksim.cde_material.modules.items;

import android.util.Log;

/**
 * Created by maksimrudik on 04.03.15.
 */
public class SettingItem {
    private String mTitle;
    private String mSubTitle;
    private boolean mChecked;
    private int mId;

    String TAG = "SettingItem";


    public SettingItem(String title, String subTitle, boolean checked, int id) {
        mTitle = title;
        mSubTitle = subTitle;
        mChecked = checked;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
