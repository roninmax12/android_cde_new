package com.rudik_maksim.cde_material.controllers.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.items.PointItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class PointsAdapter extends ArrayAdapter<PointItem> {
    ArrayList<PointItem> mPointItems;
    Activity mActivity;

    public PointsAdapter(Activity activity, ArrayList<PointItem> pointItems){
        super(activity, 0, pointItems);
        mPointItems = pointItems;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mPointItems.size();
    }

    @Override
    public PointItem getItem(int position) {
        return mPointItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_points, null);
        }

        PointItem pointItem = getItem(position);

        TextView subjectTextView = (TextView) convertView.findViewById(R.id.textViewSubject);
        subjectTextView.setText(pointItem.getTitle());

        TextView controlTextView = (TextView) convertView.findViewById(R.id.textViewControl);
        controlTextView.setText(pointItem.getContol());

        TextView pointTextView = (TextView) convertView.findViewById(R.id.textViewPoint);
        pointTextView.setText(pointItem.getPoint());

        String p = pointTextView.getText().toString();

        if (!p.equals("")){
            p = p.replace(',', '.');

            if ( (int) Float.parseFloat(p) >= 60){
                subjectTextView.setTextColor(mActivity.getApplicationContext().getResources().getColor(R.color.subject_green_textColor));
                pointTextView.setTextColor(mActivity.getApplicationContext().getResources().getColor(R.color.subject_green_textColor));
            }else{
                subjectTextView.setTextColor(Color.BLACK);
                controlTextView.setTextColor(Color.BLACK);
            }
        }else{
            subjectTextView.setTextColor(Color.BLACK);
            controlTextView.setTextColor(Color.BLACK);
        }

        controlTextView.setTextColor(mActivity.getApplicationContext().getResources().getColor(R.color.textview_sub_color));

        return convertView;
    }
}
