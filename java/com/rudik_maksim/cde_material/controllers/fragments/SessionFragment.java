package com.rudik_maksim.cde_material.controllers.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
public class SessionFragment extends AbstractPagerFragment implements IFragment, INetworkListener {
    private static String TAG = "SessionFragment";

    private static boolean sAnimate = false;
    private static boolean sLoading = false;

    private Session sSession = Session.get();
    private PagerSessionAdapter mPagerSessionAdapter;

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

        mPagerSessionAdapter = new PagerSessionAdapter(getChildFragmentManager());

        if (Global.sAuthorized) {
            if (sSession != null) {
                if (sSession.getSize() > 0)
                    setData();
                else {
                    if (!sLoading)
                        getData();
                    else
                        setData();
                }
            }else {
                if (!sLoading)
                    getData();
            }
        }
    }

    @Override
    public void getData() {
        if (Global.sAuthorized){
            sLoading = true;
            sAnimate = true;

            Network.Query query = new Network.Query(this);
            query.send(query.ACTION_SESSION);
        }
    }

    @Override
    public void setData(){
        sProgressBar.setVisibility(View.INVISIBLE);

        sSession = Session.get();
        sPager.setAdapter(mPagerSessionAdapter);
        sTabs.setViewPager(sPager);
    }

    @Override
    public void onStartQuery() {
        sProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishQuery() {
        setData();
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
            return PagerSessionFragment.newInstance(position);
        }
    }

    /**
     * PagerSessionFragment
     */

    public static class PagerSessionFragment extends AbstractSubListPagerFragment {
        private static final int PAGE_EXAMS = 0;
        private static final int PAGE_CONSULTATIONS = 1;

        private Session mSession = Session.get();

        @Override
        public boolean onAnimate() {
            boolean animate = sAnimate;

            if (sAnimate)
                sAnimate = false;

            return animate;
        }

        public static PagerSessionFragment newInstance(int position) {
            PagerSessionFragment f = new PagerSessionFragment();
            Bundle b = new Bundle();
            b.putInt(ARG_POSITION, position);
            f.setArguments(b);
            return f;
        }

        @Override
        public void setData() {
            if (mSession != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    sTabs.setVisibility(View.VISIBLE);

                SessionAdapter sessionAdapter = null;

                try{
                    switch (sPosition) {
                        case PAGE_EXAMS:
                            sessionAdapter = new SessionAdapter(getActivity(), mSession.getExamsList());
                            break;
                        case PAGE_CONSULTATIONS:
                            sessionAdapter = new SessionAdapter(getActivity(), mSession.getConsultationsList());
                            break;
                    }

                    if (sessionAdapter != null && sessionAdapter.getCount() != 0)
                        sListView.setAdapter(sessionAdapter);
                    else {
                        sTextEmpty.setText(getString(R.string.no_data));
                        sTextEmpty.setVisibility(View.VISIBLE);
                    }

                }catch (NullPointerException unimportant) {
                    unimportant.printStackTrace();
                }
            }
        }
    }

}
