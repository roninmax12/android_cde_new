package com.rudik_maksim.cde_material.controllers.fragments;

import android.os.Bundle;
import android.view.View;
import com.rudik_maksim.cde_material.controllers.adapters.RatingsAdapter;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.Rating;
import com.rudik_maksim.cde_material.modules.interfaces.IFragment;
import com.rudik_maksim.cde_material.modules.interfaces.INetworkListener;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class RatingsFragment extends AbstractListFragment implements IFragment, INetworkListener{
    Rating mRating = Rating.get();
    String TAG = "RatingsFragment";

    @Override
    protected boolean retainInstance() {
        return true;
    }

    public static RatingsFragment newInstance(int sectionNumber) {
        RatingsFragment fragment = new RatingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart(){
        super.onStart();

        if (Global.sAuthorized){
            if (mRating != null){
                if (mRating.getSize() > 0)
                    setData();
                else
                    getData();
            }else{
                getData();
            }
        }
    }

    @Override
    public void getData(){
        if (Global.sAuthorized){
            Network.Query query = new Network.Query(this);
            query.send(query.ACTION_RATING);
        }
    }

    @Override
    public void setData(){
        if (mRating != null){
            try{
                RatingsAdapter ratingsAdapter = new RatingsAdapter(getActivity(), mRating.getRatingItems());
                sListView.setAdapter(ratingsAdapter);
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
        mRating = Rating.get();
        sFrameLayout.startAnimation(sAnimationTop);
        setData();
    }
}
