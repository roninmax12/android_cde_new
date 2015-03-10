package com.rudik_maksim.cde_material.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class DialogYearsAdapter extends ArrayAdapter<String> {
    ArrayList<String> mYears;
    Activity mActivity;
    String mCheckedYear;

    public DialogYearsAdapter(Activity activity, ArrayList<String> years, String checkedYear){
        super(activity, 0, years);
        mYears= years;
        mActivity = activity;
        mCheckedYear = checkedYear;
    }

    @Override
    public int getCount() {
        return mYears.size();
    }

    @Override
    public String getItem(int position) {
        return mYears.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.dialog_choose_year_item, null);
        }

        TextView subjectTextView = (TextView) convertView.findViewById(R.id.dialog_choose_year_item_textview);
        subjectTextView.setText(getItem(position));

        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.dialog_choose_year_item_radio);

        if (getItem(position).equals(mCheckedYear))
            radioButton.setChecked(true);
        else
            radioButton.setChecked(false);

        return convertView;
    }
}
