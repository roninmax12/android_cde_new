package com.rudik_maksim.cde_material.controllers.fragments;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;
import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.PointsActivity;

/**
 * Created by maksimrudik on 10.03.15.
 */
public abstract class AbstractPagerFragment extends Fragment{
    public static PagerSlidingTabStrip sTabs;
    public static ViewPager sPager;
    public static ProgressBar sProgressBar;
    public static LayoutInflater sLayoutInflater;

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        sLayoutInflater = getActivity().getLayoutInflater();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((PointsActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container, false);

        sTabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        sPager = (ViewPager) v.findViewById(R.id.pager);
        sProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            sProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_blue), PorterDuff.Mode.SRC_IN);

        return v;
    }

}
