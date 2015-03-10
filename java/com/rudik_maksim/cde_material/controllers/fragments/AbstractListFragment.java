package com.rudik_maksim.cde_material.controllers.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.PointsActivity;

/**
 * Created by maksimrudik on 10.03.15.
 */
public abstract class AbstractListFragment extends Fragment{
    public static ListView sListView;
    public static TextView sTextEmpty;
    public static ProgressBar sProgressBar;
    public static Animation sAnimationTop;
    public static FrameLayout sFrameLayout;

    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String ARG_POSITION = "position";

    protected abstract boolean retainInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (retainInstance()) {
            setRetainInstance(true);
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((PointsActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview, container, false);

        sAnimationTop = AnimationUtils.loadAnimation(getActivity(), R.anim.up_top);

        sTextEmpty = (TextView) v.findViewById(R.id.textView);
        sProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        sListView = (ListView) v.findViewById(R.id.listView);
        sFrameLayout = (FrameLayout) v.findViewById(R.id.frameLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            sProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_blue), PorterDuff.Mode.SRC_IN);

        return v;
    }

    public static void clearListView(){
        try{
            sListView.setAdapter(null);
        }catch (Exception unimportant){}
    }
}
