package com.rudik_maksim.cde_material.modules;

import com.rudik_maksim.cde_material.modules.items.SessionItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 10.03.15.
 */
public class Session {
    private ArrayList<SessionItem> mExamsList;
    private ArrayList<SessionItem> mConsultationsList;

    private static Session sSession = null;

    private Session(){
        mExamsList = new ArrayList<SessionItem>();
        mConsultationsList = new ArrayList<SessionItem>();
    }

    public static Session get(){
        if (sSession == null){
            sSession = new Session();
        }

        return sSession;
    }

    public int getSize(){
        return mExamsList.size();
    }

    public ArrayList<SessionItem> getExamsList() {
        return mExamsList;
    }

    public ArrayList<SessionItem> getConsultationsList() {
        return mConsultationsList;
    }

    public void putExam(SessionItem sessionItem){
        mExamsList.add(sessionItem);
    }

    public void putConsultation(SessionItem sessionItem){
        mConsultationsList.add(sessionItem);
    }

    public void destruct(){
        sSession = null;
        mExamsList.clear();
        mConsultationsList.clear();
    }
}
