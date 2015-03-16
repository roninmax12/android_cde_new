package com.rudik_maksim.cde_material.controllers.fragments;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.SubjectDetailsActivity;
import com.rudik_maksim.cde_material.controllers.adapters.PointsAdapter;
import com.rudik_maksim.cde_material.controllers.dialogs.DialogChooseYearFragment;
import com.rudik_maksim.cde_material.modules.ApplicationPreferences;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.interfaces.INetworkListener;
import com.rudik_maksim.cde_material.modules.items.PointItem;
import com.rudik_maksim.cde_material.modules.Points;
import com.rudik_maksim.cde_material.modules.SubjectDetails;
import com.rudik_maksim.cde_material.modules.User;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class PointsFragment extends AbstractAuthorizationFragment implements INetworkListener{
    Points mPoints = Points.get();
    User mUser = User.get();

    String TAG = "PointsFragment";

    public static PointsFragment newInstance(int sectionNumber) {
        PointsFragment fragment = new PointsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PointItem pointItem = (PointItem)mListView.getAdapter().getItem(position);
                SubjectDetails subjectDetails = pointItem.getSubjectDetails();

                SubjectDetailsActivity.sPointItem = pointItem;
                SubjectDetailsActivity.sSubjectDetails = subjectDetails;

                Intent i = new Intent(getActivity(), SubjectDetailsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void getData(){
        if (Global.sAuthorized){
            mUser = User.get();
            Network.Query query = new Network.Query(this);
            query.send(query.ACTION_POINTS);
        }
    }

    @Override
    public void setData(){
        if (mPoints != null){
            // Инициализируем работу с настройками
            if (Global.sSettingsPreferences == null){
                Global.sSettingsPreferences = new ApplicationPreferences.SettingsPreferences(Global.sApplicationContext, mUser.getLogin());
            }

            try{
                PointsAdapter pointsAdapter = new PointsAdapter(getActivity(), mPoints.getNeededItems(mUser));
                mListView.setAdapter(pointsAdapter);
            }catch (NullPointerException unimportant){
                unimportant.printStackTrace();
            }
        }
    }

    @Override
    public void onStartQuery() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishQuery() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mPoints = Points.get();
        mFrameLayout.startAnimation(mAnimationTop);
        setData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_points, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_choose_year:
                if (mPoints != null){
                    new DialogChooseYearFragment().show(getFragmentManager(), DialogChooseYearFragment.TAG_CHOOSE_YEAR);
                }else{
                    Toast.makeText(getActivity(), getString(R.string.wait_load_data), Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
