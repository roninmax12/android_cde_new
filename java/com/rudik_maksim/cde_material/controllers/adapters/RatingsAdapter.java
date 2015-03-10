package com.rudik_maksim.cde_material.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.items.RatingItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class RatingsAdapter extends ArrayAdapter<RatingItem> {
    ArrayList<RatingItem> mRatingItems;
    Activity mActivity;

    public RatingsAdapter(Activity activity, ArrayList<RatingItem> ratingItems){
        super(activity, 0, ratingItems);
        mRatingItems = ratingItems;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mRatingItems.size();
    }

    @Override
    public RatingItem getItem(int position) {
        return mRatingItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_ratings, null);
        }

        RatingItem ratingItem = getItem(position);

        TextView facultyTextView = (TextView) convertView.findViewById(R.id.textview_ratings_faculty);
        facultyTextView.setText(ratingItem.getFaculty());

        TextView courseTextView = (TextView) convertView.findViewById(R.id.textview_ratings_course);
        courseTextView.setText(ratingItem.getCourse());

        TextView positionTextView = (TextView) convertView.findViewById(R.id.textview_ratings_position);
        positionTextView.setText(ratingItem.getPosition());

        return convertView;
    }
}
