package com.rudik_maksim.cde_material.controllers.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.adapters.SessionAdapter;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.Schedule;
import com.rudik_maksim.cde_material.modules.ScheduleDay;
import com.rudik_maksim.cde_material.modules.interfaces.IFragment;
import com.rudik_maksim.cde_material.modules.interfaces.INetworkListener;
import com.rudik_maksim.cde_material.modules.items.ScheduleItem;

import java.util.ArrayList;

/**
 * Created by maksimrudik on 10.03.15.
 */
public class ScheduleFragment extends AbstractPagerFragment implements IFragment, INetworkListener {
    private static String TAG = "ScheduleFragment";

    private static boolean sAnimate = false;
    private static boolean sLoading = false;

    private Schedule sSchedule = Schedule.get();
    private PagerScheduleAdapter mPagerScheduleAdapter;

    public static ScheduleFragment newInstance(int sectionNumber) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPagerScheduleAdapter = new PagerScheduleAdapter(getChildFragmentManager());

        if (Global.sAuthorized) {
            if (sSchedule != null) {
                if (sSchedule.getSize() > 0)
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
            query.send(query.ACTION_SCHEDULE);
        }
    }

    @Override
    public void setData(){
        sProgressBar.setVisibility(View.INVISIBLE);

        sSchedule = Schedule.get();
        sPager.setAdapter(mPagerScheduleAdapter);
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
     * PagerScheduleAdapter
     */

    public class PagerScheduleAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {getString(R.string.even_week), getString(R.string.oven_week)};

        public PagerScheduleAdapter(FragmentManager fm) {
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
            return PagerScheduleFragment.newInstance(position);
        }
    }

    /**
     * PagerScheduleFragment
     */

    public static class PagerScheduleFragment extends AbstractSubInflaterPagerFragment {
        private static final int PAGE_EVEN = 0;
        private static final int PAGE_OVEN = 1;

        private Schedule mSchedule = Schedule.get();

        @Override
        public boolean onAnimate() {
            boolean animate = sAnimate;

            if (sAnimate)
                sAnimate = false;

            return animate;
        }

        public static PagerScheduleFragment newInstance(int position) {
            PagerScheduleFragment f = new PagerScheduleFragment();
            Bundle b = new Bundle();
            b.putInt(ARG_POSITION, position);
            f.setArguments(b);
            return f;
        }

        @Override
        public void setData() {
            if (mSchedule != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    sTabs.setVisibility(View.VISIBLE);

                ArrayList<ScheduleDay> scheduleDays = new ArrayList<ScheduleDay>();

                switch (sPosition) {
                    case PAGE_EVEN:
                        scheduleDays.addAll(mSchedule.getEvenSchedule());
                        break;
                    case PAGE_OVEN:
                        scheduleDays.addAll(mSchedule.getOvenSchedule());
                        break;
                }

                inflateViews(scheduleDays);
            }
        }

        public void inflateViews(ArrayList<ScheduleDay> scheduleDays) {
            sLinearLayoutContainer.removeAllViews();

            for (ScheduleDay day: scheduleDays) {
                View item_day = sLayoutInflater.inflate(R.layout.item_schedule_title, sLinearLayoutContainer, false);

                TextView textViewDay = (TextView) item_day.findViewById(R.id.schedule_item_textview_day);

                textViewDay.setText(day.getDay());

                sLinearLayoutContainer.addView(item_day);

                for (ScheduleItem item: day.getAllScheduleItems()) {
                    View item_content = sLayoutInflater.inflate(R.layout.item_schedule_content, sLinearLayoutContainer, false);

                    TextView textViewSubject = (TextView) item_content.findViewById(R.id.textView_subject);
                    TextView textViewTime = (TextView) item_content.findViewById(R.id.textView_time);
                    TextView textViewRoom = (TextView) item_content.findViewById(R.id.textView_room);
                    TextView textViewTeacher = (TextView) item_content.findViewById(R.id.textView_teacher);

                    textViewSubject.setText(item.getSubject());
                    textViewTime.setText(item.getTime());
                    textViewRoom.setText(item.getRoom());
                    textViewTeacher.setText(item.getTeacher());

                    sLinearLayoutContainer.addView(item_content);
                }
            }
        }

    }


}
