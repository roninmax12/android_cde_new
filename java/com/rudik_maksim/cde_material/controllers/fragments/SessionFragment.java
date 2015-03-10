package com.rudik_maksim.cde_material.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.adapters.SessionAdapter;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.Session;
import com.rudik_maksim.cde_material.modules.interfaces.IFragment;
import com.rudik_maksim.cde_material.modules.interfaces.INetworkListener;

/**
 * Created by maksimrudik on 10.03.15.
 */
public class SessionFragment extends AbstractPagerFragment {
    private static Session sSession = Session.get();
    private static boolean sLoading = false;
    private static String TAG = "SessionFragment";

    public static SessionFragment newInstance(int sectionNumber) {
        SessionFragment fragment = new SessionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PagerSessionAdapter adapter = new PagerSessionAdapter(getChildFragmentManager());
        sPager.setAdapter(adapter);
        sTabs.setViewPager(sPager);
    }


    /**
     * PagerSessionAdapter
     */

    public class PagerSessionAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {getString(R.string.exams), getString(R.string.consultations)};

        public PagerSessionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return PagerSessionFragment.newInstance(position, getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    /**
     * PagerSessionFragment
     */

    public static class PagerSessionFragment extends AbstractListFragment implements IFragment, INetworkListener{
        private int mPosition = 0;

        public static PagerSessionFragment newInstance(int position, int section_number) {
            PagerSessionFragment f = new PagerSessionFragment();
            Bundle b = new Bundle();
            b.putInt(ARG_POSITION, position);
            b.putInt(ARG_SECTION_NUMBER, section_number);
            f.setArguments(b);
            return f;
        }

        @Override
        protected boolean retainInstance() {
            return false;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mPosition = getArguments().getInt(ARG_POSITION);
            Log.d(TAG, "updated mPosition is: " + mPosition);
        }

        @Override
        public void onStart() {
            super.onStart();

            if (Global.sAuthorized){
                if (sSession != null){
                    if (sSession.getSize() > 0)
                        setData();
                    else {
                        if (!sLoading)
                            getData();
                    }
                }else{
                    if (!sLoading)
                        getData();
                }
            }
        }

        @Override
        public void getData() {
            if (Global.sAuthorized){
                sLoading = true;
                Network.Query query = new Network.Query(this);
                query.send(query.ACTION_SESSION);
            }
        }

        @Override
        public void setData() {
            if (sSession != null){
                SessionAdapter sessionAdapter = null;

                try{
                    switch (mPosition){
                        case 0:
                            sessionAdapter = new SessionAdapter(getActivity(), sSession.getExamsList());
                            break;
                        case 1:
                            sessionAdapter = new SessionAdapter(getActivity(), sSession.getConsultationsList());
                            break;
                    }

                    if (sessionAdapter != null)
                        sListView.setAdapter(sessionAdapter);
                }catch (NullPointerException unimportant){
                    unimportant.printStackTrace();
                }
            }
        }

        @Override
        public void onStartQuery() {
            sProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFinishQuery() {
            sProgressBar.setVisibility(View.INVISIBLE);
            sSession = Session.get();
            sFrameLayout.startAnimation(sAnimationTop);
            setData();
        }
    }
}
