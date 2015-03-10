package com.rudik_maksim.cde_material.modules.items;

import com.rudik_maksim.cde_material.modules.SubjectDetails;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class PointItem {
    private String mTitle;
    private String mContol;
    private String mPoint;

    SubjectDetails mSubjectDetails;

    public PointItem() {

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContol() {
        return mContol;
    }

    public void setContol(String contol) {
        mContol = contol;
    }

    public String getPoint() {
        return mPoint;
    }

    public void setPoint(String point) {
        mPoint = point;
    }

    public SubjectDetails getSubjectDetails() {
        return mSubjectDetails;
    }

    public void setSubjectDetails(SubjectDetails subjectDetails) {
        mSubjectDetails = subjectDetails;
    }
}
