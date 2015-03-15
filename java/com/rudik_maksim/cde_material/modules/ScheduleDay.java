package com.rudik_maksim.cde_material.modules;

import com.rudik_maksim.cde_material.modules.items.ScheduleItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 15.03.15.
 */
public class ScheduleDay {
    private String mDay;
    private ArrayList<ScheduleItem> mScheduleItems;

    public ScheduleDay() {
        mScheduleItems = new ArrayList<ScheduleItem>();
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public int size() {
        return mScheduleItems.size();
    }

    public ArrayList<ScheduleItem> getAllScheduleItems() {
        return mScheduleItems;
    }

    public void setScheduleItems(ArrayList<ScheduleItem> scheduleItems) {
        mScheduleItems = scheduleItems;
    }

    public ArrayList<ScheduleItem> getEvenScheduleItems() {
        ArrayList<ScheduleItem> evenItems = new ArrayList<ScheduleItem>();

        for (ScheduleItem item: mScheduleItems) {
            if (item.getWeek() == ScheduleItem.Week.EVEN || item.getWeek() == ScheduleItem.Week.ALL) {
                evenItems.add(item);
            }
        }

        return evenItems;
    }

    public ArrayList<ScheduleItem> getOvenScheduleItems() {
        ArrayList<ScheduleItem> ovenItems = new ArrayList<ScheduleItem>();

        for (ScheduleItem item: mScheduleItems) {
            if (item.getWeek() == ScheduleItem.Week.OVEN || item.getWeek() == ScheduleItem.Week.ALL) {
                ovenItems.add(item);
            }
        }

        return ovenItems;
    }

    public void put(ScheduleItem scheduleItem) {
        mScheduleItems.add(scheduleItem);
    }
}
