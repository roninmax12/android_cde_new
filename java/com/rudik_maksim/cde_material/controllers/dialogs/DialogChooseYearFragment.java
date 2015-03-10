package com.rudik_maksim.cde_material.controllers.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rudik_maksim.cde_material.controllers.adapters.DialogYearsAdapter;
import com.rudik_maksim.cde_material.controllers.fragments.PointsFragment;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.Points;

/**
 * Created by Максим on 24.09.2014.
 */
public class DialogChooseYearFragment extends AbstractDialogFragment {
    public static final String TAG_CHOOSE_YEAR = "DialogChooseYearFragment";
    ListView mListView;
    Points mPoints = Points.get();

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_year;
    }

    @Override
    protected String setTitle() {
        return getString(R.string.choose_year_study);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView )getView().findViewById(R.id.dialog_choose_year_listview);

        DialogYearsAdapter adapter = new DialogYearsAdapter(getActivity(), mPoints.getAllYears(), Points.get().getShownYear());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Points.get().setShownYear(mListView.getAdapter().getItem(position).toString());
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, PointsFragment.newInstance(1))
                        .commit();

                dismiss();
            }
        });
    }
}
