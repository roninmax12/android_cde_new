package com.rudik_maksim.cde_material.modules;

import com.rudik_maksim.cde_material.modules.items.PointItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by maksimrudik on 28.02.15.
 */
public class PointsSemester {
    ArrayList<PointItem> mPointItems;
    String mSemester;

    public PointsSemester(){
        mPointItems = new ArrayList<PointItem>();
    }

    public void setSemester(String semester) {
        mSemester = semester;
    }

    public String getSemester() {
        return mSemester;
    }

    public ArrayList<PointItem> getPointItems() {
        Collections.sort(mPointItems, new TitlesComparator());
        return mPointItems;
    }

    public void put(PointItem pointItem){
        mPointItems.add(pointItem);
    }

    public int getListSize(){
        return mPointItems.size();
    }

    private class TitlesComparator implements Comparator<PointItem>{
        @Override
        public int compare(PointItem lhs, PointItem rhs) {
            return lhs.getTitle().compareTo(rhs.getTitle());
        }
    }
}
