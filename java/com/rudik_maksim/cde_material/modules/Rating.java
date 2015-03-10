package com.rudik_maksim.cde_material.modules;

import com.rudik_maksim.cde_material.modules.items.RatingItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by maksimrudik on 06.03.15.
 */
public class Rating {
    private ArrayList<RatingItem> mRatingItems;
    private static Rating mRating;

    private Rating(){
        mRatingItems = new ArrayList<RatingItem>();
    }

    public static Rating get(){
        if (mRating == null){
            mRating = new Rating();
        }

        return mRating;
    }

    public ArrayList<RatingItem> getRatingItems(){
        Collections.sort(mRatingItems, new CourseComparator());
        return mRatingItems;
    }

    public void put(RatingItem ratingItem){
        mRatingItems.add(ratingItem);
    }

    public int getSize(){
        return mRatingItems.size();
    }

    public void destruct(){
        mRatingItems.clear();
        mRating = null;
    }

    private class CourseComparator implements Comparator<RatingItem> {
        @Override
        public int compare(RatingItem lhs, RatingItem rhs) {
            return lhs.getCourse().compareTo(rhs.getCourse());
        }
    }
}
