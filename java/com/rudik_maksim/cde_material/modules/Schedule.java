package com.rudik_maksim.cde_material.modules;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 15.03.15.
 */
public class Schedule {
    private static Schedule sSchedule;
    private ArrayList<ScheduleDay> mScheduleDays;

    private Schedule() {
        mScheduleDays = new ArrayList<ScheduleDay>();
    }

    public static Schedule get() {
        if (sSchedule == null) {
            sSchedule = new Schedule();
        }

        return sSchedule;
    }

    public ArrayList<ScheduleDay> getScheduleDays() {
        return mScheduleDays;
    }

    public ArrayList<ScheduleDay> getEvenSchedule() {
        ArrayList<ScheduleDay> evenSchedule = new ArrayList<ScheduleDay>();

        for (ScheduleDay day: mScheduleDays) {
            ScheduleDay evenDay = new ScheduleDay();

            evenDay.setDay(day.getDay());
            evenDay.setScheduleItems(day.getEvenScheduleItems());

            if (evenDay.size() > 0)
                evenSchedule.add(evenDay);
        }

        return evenSchedule;
    }

    public ArrayList<ScheduleDay> getOvenSchedule() {
        ArrayList<ScheduleDay> ovenSchedule = new ArrayList<ScheduleDay>();

        for (ScheduleDay day: mScheduleDays) {
            ScheduleDay ovenDay = new ScheduleDay();

            ovenDay.setDay(day.getDay());
            ovenDay.setScheduleItems(day.getOvenScheduleItems());

            if (ovenDay.size() > 0)
                ovenSchedule.add(ovenDay);
        }

        return ovenSchedule;
    }

    public int getSize() {
        return mScheduleDays.size();
    }

    public void put(ScheduleDay scheduleDay) {
        mScheduleDays.add(scheduleDay);
    }

    public void destruct() {
        mScheduleDays.clear();
        sSchedule = null;
    }
}
