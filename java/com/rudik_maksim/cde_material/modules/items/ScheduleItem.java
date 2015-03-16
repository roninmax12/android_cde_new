package com.rudik_maksim.cde_material.modules.items;

/**
 * Created by maksimrudik on 15.03.15.
 */
public class ScheduleItem {
    private String mSubject;
    private String mSubjectStatus; // it means "Лек" or "Прак"
    private String mTime;
    private String mRoom;
    private String mPlace;
    private String mTeacher;
    private String mGroup; // use for teacher's schedule

    private Week mWeek;

    public ScheduleItem() {

    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getSubjectStatus() {
        return mSubjectStatus;
    }

    public void setSubjectStatus(String subjectStatus) {
        mSubjectStatus = subjectStatus;
    }

    public String getRightStatus() {
        if (mSubjectStatus.equals("Прак")) return "Практика";
        if (mSubjectStatus.equals("Лек"))  return "Лекция";
        if (mSubjectStatus.equals("Лаб"))  return "Лабораторная работа";

        return mSubjectStatus;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String teacher) {
        mTeacher = teacher;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public Week getWeek() {
        return mWeek;
    }

    public void setWeek(Week week) {
        mWeek = week;
    }

    public static enum Week {EVEN, OVEN, ALL}
}
