package com.rudik_maksim.cde_material.modules.items;

/**
 * Created by maksimrudik on 10.03.15.
 */
public class SessionItem {
    private String mTitle;
    private String mDate;
    private String mTime;
    private String mRoom;
    private String mTeacher;

    public SessionItem(){

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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

    public String getTeacher() {
        return mTeacher;
    }

    public void setTeacher(String teacher) {
        mTeacher = teacher;
    }
}
