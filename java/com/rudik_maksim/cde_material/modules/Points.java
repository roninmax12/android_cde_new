package com.rudik_maksim.cde_material.modules;

import android.util.Log;

import com.rudik_maksim.cde_material.modules.items.PointItem;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by maksimrudik on 28.02.15.
 */
public class Points {
    String TAG = "Points";

    private static Points sPoints;

    private TreeMap<String, ArrayList<PointsSemester>> mAllPoints;
    private String mLastYear;
    private String mShownYear = "";

    private Points(){
        mAllPoints = new TreeMap<String, ArrayList<PointsSemester>>();
    }

    public static Points get(){
        if (sPoints == null){
            sPoints = new Points();
        }

        return sPoints;
    }

    public void put(String year, ArrayList<PointsSemester> pointsSemesters){
        mAllPoints.put(year, pointsSemesters);
    }

    public int getMapSize(){
        return mAllPoints.size();
    }

    public ArrayList<PointsSemester> getListPoint(String year){
        if (!mAllPoints.containsKey(year))
            return null;

        return mAllPoints.get(year);
    }

    public String getLastYear() {
        return mLastYear;
    }

    public void setLastYear(String lastYear) {
        mLastYear = lastYear;
    }

    public ArrayList<String> getAllYears(){
        ArrayList<String> years = new ArrayList<String>();

        for (String key: mAllPoints.keySet()){
            years.add(key);
        }

        return years;
    }

    public void destruct(){
        sPoints = null;
        mAllPoints.clear();
        mLastYear = null;
    }

    public String getShownYear(){
        if (mShownYear.length() > 4)
            return mShownYear;
        else
            return getLastYear();
    }

    public void setShownYear(String shownYear){
        mShownYear = shownYear;
    }

    public ArrayList<PointItem> getNeededItems(User mUser){
        ArrayList<PointItem> result = new ArrayList<PointItem>();

        ArrayList<PointsSemester> pointsSemesters = getListPoint(getShownYear());

        if (Global.sSettingsPreferences.getSemesterShowMode()){
            for (int i = 0; i < pointsSemesters.size(); i++){
                String semester = pointsSemesters.get(i).getSemester();

                Log.d(TAG, "getSemester(): " + semester);
                Log.d(TAG, "getCurrentSemester(): " + Study.getCurrentSemester(mUser.getCourseNumber()));
                Log.d(TAG, "getCourseNumber(): " + mUser.getCourseNumber());

                if (getShownYear().equals(getLastYear())){
                    // Выбираем нужный семестер
                    if (semester.equals(Study.getCurrentSemester(mUser.getCourseNumber()))){
                        result.addAll(pointsSemesters.get(i).getPointItems());
                        break;
                    }
                }else{
                    // Возвращаем данные за год
                    result.addAll(pointsSemesters.get(0).getPointItems());
                    result.addAll(pointsSemesters.get(1).getPointItems());
                    break;
                }
            }
        }else{
            result.addAll(pointsSemesters.get(0).getPointItems());
            result.addAll(pointsSemesters.get(1).getPointItems());
        }

        return result;
    }
}
