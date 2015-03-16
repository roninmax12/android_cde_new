package com.rudik_maksim.cde_material.controllers.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.adapters.SettingsAdapter;
import com.rudik_maksim.cde_material.controllers.dialogs.DialogAboutFragment;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.items.SettingItem;
import com.rudik_maksim.cde_material.modules.Settings;

/**
 * Created by maksimrudik on 04.03.15.
 */
public class SettingsFragment extends AbstractListFragment {
    SettingsAdapter mSettingsAdapter;

    String TAG = "SettingsFragment";

    @Override
    protected boolean retainInstance() {
        return true;
    }

    public static SettingsFragment newInstance(int sectionNumber) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Global.sSettingsPreferences != null) {
            mSettingsAdapter = new SettingsAdapter(getActivity(), Settings.getSettings(getActivity()).getSettingsItems());
            sListView.setAdapter(mSettingsAdapter);
        }else{
            sTextEmpty.setVisibility(View.VISIBLE);
            sTextEmpty.setText(getString(R.string.wait_load_data));
        }

        sListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingItem settingItem = (SettingItem) sListView.getAdapter().getItem(position);
                Global.sSettingsPreferences.inverseAndSave(settingItem);

                sListView.setAdapter(mSettingsAdapter);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                new DialogAboutFragment().show(getFragmentManager(), DialogAboutFragment.TAG_ABOUT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
