package com.rudik_maksim.cde_material.modules.items;

/**
 * Created by maksimrudik on 28.02.15.
 */
public class SubjectDetailsItem {
    private String mTitle;
    private String mMaxValue;
    private String mLimitValue;
    private String mUserValue;

    public SubjectDetailsItem(){

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(String maxValue) {
        mMaxValue = maxValue;
    }

    public String getLimitValue() {
        return mLimitValue;
    }

    public void setLimitValue(String limitValue) {
        mLimitValue = limitValue;
    }

    public String getUserValue() {
        return mUserValue;
    }

    public void setUserValue(String userValue) {
        mUserValue = userValue;
    }
}
