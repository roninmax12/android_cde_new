package com.rudik_maksim.cde_material.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.items.SubjectDetailsItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class SubjectDetailsAdapter extends ArrayAdapter<SubjectDetailsItem> {
    ArrayList<SubjectDetailsItem> mSubjectDetailsItems;
    Activity mActivity;

    public SubjectDetailsAdapter(Activity activity, ArrayList<SubjectDetailsItem> subjectDetailsItems){
        super(activity, 0, subjectDetailsItems);
        mSubjectDetailsItems = subjectDetailsItems;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mSubjectDetailsItems.size();
    }

    @Override
    public SubjectDetailsItem getItem(int position) {
        return mSubjectDetailsItems.get(position);
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_subject_details, null);
        }

        SubjectDetailsItem item = getItem(position);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textview_title);
        textViewTitle.setText(item.getTitle());

        TextView textViewThreshold = (TextView) convertView.findViewById(R.id.textview_threshold);
        textViewThreshold.setText(item.getLimitValue());

        String rating = String.format(mActivity.getApplicationContext().getString(R.string.x_from_y),
                item.getUserValue(), item.getMaxValue());

        TextView textViewPoints = (TextView) convertView.findViewById(R.id.textview_points);
        textViewPoints.setText(rating);

        return convertView;
    }
}
