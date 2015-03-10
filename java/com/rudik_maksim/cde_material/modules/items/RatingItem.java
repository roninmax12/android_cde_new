package com.rudik_maksim.cde_material.modules.items;

/**
 * Created by maksimrudik on 06.03.15.
 */
public class RatingItem {
    private String mFaculty;
    private String mCourse;
    private String mPosition;

    public RatingItem(){

    }

    public String getFaculty() {
        return mFaculty;
    }

    public void setFaculty(String faculty) {
        mFaculty = faculty;
    }

    public String getCourse() {
        return mCourse;
    }

    public void setCourse(String course) {
        mCourse = course;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }
}
