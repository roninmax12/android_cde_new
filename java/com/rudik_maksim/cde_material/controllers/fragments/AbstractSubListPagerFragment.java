package com.rudik_maksim.cde_material.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;

/**
 * Created by maksimrudik on 15.03.15.
 */
public abstract class AbstractSubListPagerFragment extends Fragment{
    public static final String ARG_POSITION = "position";
    public static int sPosition;

    public static ListView sListView;
    public static TextView sTextEmpty;
    public static Animation sAnimationTop;
    public static FrameLayout sFrameLayout;

    public abstract boolean onAnimate();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview, container, false);

        sAnimationTop = AnimationUtils.loadAnimation(getActivity(), R.anim.up_top);

        sTextEmpty = (TextView) v.findViewById(R.id.textView);
        sListView = (ListView) v.findViewById(R.id.listView);
        sFrameLayout = (FrameLayout) v.findViewById(R.id.frameLayout);

        if (onAnimate()){
            sFrameLayout.setAnimation(sAnimationTop);
        }

        setData();

        return v;
    }

    public void setData() {
        // This method should been overridden
    }
}
