package com.rudik_maksim.cde_material.modules;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by maksimrudik on 04.03.15.
 */
public class Study {
    public static String getCurrentSemester(int courseNumber){
        String[] semesters = new String[]{ Integer.toString(courseNumber * 2 - 1), Integer.toString(courseNumber * 2)};

        if (isAutumnSemester())
            return semesters[0];
        else
            return semesters[1];
    }

    /**
     * return false is spring semester, otherwise (if autumn) will return true
     */

    public static boolean isAutumnSemester(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int month = calendar.get(Calendar.MONTH); // January == 0; December == 11

        if (month >= 1 && month <= 7)
            return false;
        else
            return true;
    }
}
