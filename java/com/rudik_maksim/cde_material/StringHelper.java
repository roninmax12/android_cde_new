package com.rudik_maksim.cde_material;

import android.content.Context;

/**
 * Created by maksimrudik on 24.02.15.
 */
public class StringHelper {
    public static String getDayForIndex(Context context, int index){
        String day = "";

        switch (index){
            case 0: day = context.getString(R.string.monday); break;
            case 1: day = context.getString(R.string.tuesday); break;
            case 2: day = context.getString(R.string.wednesday); break;
            case 3: day = context.getString(R.string.thursday); break;
            case 4: day = context.getString(R.string.friday); break;
            case 5: day = context.getString(R.string.saturday); break;
        }

        return day;
    }

    public static String getRightFormatTime(Context context, String time){
        //9:30-10:50 => 09:30\n10:50
        //8:00-9:20 = > 08:00\n09:20
        String formattedTime = "";

        if (time.equals(context.getString(R.string.day)))
            return time;

        String[] parts = time.split("-");

        for (int i = 0; i < parts.length; i++){
            if (parts[i].length() < 5)
                parts[i] = "0" + parts[i];
        }

        formattedTime = parts[0] + "\n" + parts[1];

        return formattedTime;
    }

    public static String getRightFormatPlace(Context context, String place){
        String formattedPlace = "";

        if (place.equals("Крон")) formattedPlace = context.getString(R.string.ifmo_kron);
        if (place.equals("Грив")) formattedPlace = context.getString(R.string.ifmo_griv);

        return formattedPlace;
    }
}
