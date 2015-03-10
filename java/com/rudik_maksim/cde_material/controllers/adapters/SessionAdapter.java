package com.rudik_maksim.cde_material.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.items.RatingItem;
import com.rudik_maksim.cde_material.modules.items.SessionItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class SessionAdapter extends ArrayAdapter<SessionItem> {
    ArrayList<SessionItem> mSessionItems;
    Activity mActivity;

    public SessionAdapter(Activity activity, ArrayList<SessionItem> sessionItems){
        super(activity, 0, sessionItems);
        mSessionItems = sessionItems;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mSessionItems.size();
    }

    @Override
    public SessionItem getItem(int position) {
        return mSessionItems.get(position);
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_session, null);
        }

        SessionItem sessionItem = getItem(position);

        TextView subjectTextView = (TextView) convertView.findViewById(R.id.textview_session_subject);
        subjectTextView.setText(sessionItem.getTitle());

        TextView dateTextView = (TextView) convertView.findViewById(R.id.textview_session_date);
        dateTextView.setText(sessionItem.getDate());

        TextView timeTextView = (TextView) convertView.findViewById(R.id.textview_session_time);
        timeTextView.setText(sessionItem.getTime());

        TextView roomTextView = (TextView) convertView.findViewById(R.id.textview_session_room);
        roomTextView.setText(sessionItem.getRoom());

        TextView teacherTextView = (TextView) convertView.findViewById(R.id.textview_session_teacher);
        teacherTextView.setText(sessionItem.getTeacher());

        return convertView;
    }
}
