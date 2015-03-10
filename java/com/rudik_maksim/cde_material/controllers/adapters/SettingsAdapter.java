package com.rudik_maksim.cde_material.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.items.SettingItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class SettingsAdapter extends ArrayAdapter<SettingItem> {
    String TAG = "SettingsAdapter";

    ArrayList<SettingItem> mSettingsItems;
    Activity mActivity;

    public SettingsAdapter(Activity activity, ArrayList<SettingItem> settingsItems){
        super(activity, 0, settingsItems);
        mSettingsItems = settingsItems;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mSettingsItems.size();
    }

    @Override
    public SettingItem getItem(int position) {
        return mSettingsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.listitem_settings, null);
        }

        SettingItem item = getItem(position);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textview_settings_title);
        textViewTitle.setText(item.getTitle());

        TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.textview_settings_subtitle);
        textViewSubTitle.setText(item.getSubTitle());

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_settings);
        checkBox.setChecked(item.isChecked());

        checkBox.setOnCheckedChangeListener(mOnCheckedChangeListener);
        checkBox.setTag(item);

        return convertView;
    }

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            Global.sSettingsPreferences.inverseAndSave((SettingItem)buttonView.getTag());
            notifyDataSetChanged();
        }
    };
}
