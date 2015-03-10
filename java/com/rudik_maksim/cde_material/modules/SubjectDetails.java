package com.rudik_maksim.cde_material.modules;

import com.rudik_maksim.cde_material.modules.items.SubjectDetailsItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 28.02.15.
 */
public class SubjectDetails {
    ArrayList<SubjectDetailsItem> mSubjectDetailsItems;

    public SubjectDetails(){
        mSubjectDetailsItems = new ArrayList<SubjectDetailsItem>();
    }

    public ArrayList<SubjectDetailsItem> getSubjectDetailsItems() {
        return mSubjectDetailsItems;
    }

    public void put(SubjectDetailsItem subjectDetailsItem){
        mSubjectDetailsItems.add(subjectDetailsItem);
    }

    public int getListSize(){
        return mSubjectDetailsItems.size();
    }
}
