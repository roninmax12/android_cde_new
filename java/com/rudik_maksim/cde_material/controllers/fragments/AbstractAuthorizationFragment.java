package com.rudik_maksim.cde_material.controllers.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.LoginActivity;
import com.rudik_maksim.cde_material.controllers.PointsActivity;
import com.rudik_maksim.cde_material.modules.Authorization;
import com.rudik_maksim.cde_material.modules.Connection;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Points;
import com.rudik_maksim.cde_material.modules.interfaces.IFragment;

import java.io.IOException;

/**
 * Created by maksimrudik on 05.03.15.
 */
public abstract class AbstractAuthorizationFragment extends Fragment implements IFragment{
    public static ListView mListView;
    public static TextView mTextEmpty;
    public static ProgressBar mProgressBar;
    public static Animation mAnimationTop;
    public static FrameLayout mFrameLayout;

    public static final String ARG_SECTION_NUMBER = "section_number";

    Points mPoints = Points.get();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        if (Global.sApplicationContext == null)
            Global.sApplicationContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listview, container, false);

        mAnimationTop = AnimationUtils.loadAnimation(getActivity(), R.anim.up_top);

        mTextEmpty = (TextView) v.findViewById(R.id.textView);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mListView = (ListView) v.findViewById(R.id.listView);
        mFrameLayout = (FrameLayout) v.findViewById(R.id.frameLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_blue), PorterDuff.Mode.SRC_IN);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((PointsActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mPoints != null){
            if (mPoints.getMapSize() > 0){
                setData();
            }else{
                if (!Global.sAuthorized)
                    authorizationProcess();
                else
                    getData();
            }
        }else{
            if (!Global.sAuthorized)
                authorizationProcess();
            else
                getData();
        }
    }


    public static void clearListView(){
        try{
            mListView.setAdapter(null);
        }catch (Exception unimportant){}
    }

    void authorizationProcess(){
        if (Global.sAuthorizationPreferences.exists()){
            String login = Global.sAuthorizationPreferences.getLogin();
            String password = Global.sAuthorizationPreferences.getPassword();

            if (!Connection.isOnline(getActivity().getApplicationContext())) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                i.putExtra(LoginActivity.INTENT_TAG_LOGIN, login);
                i.putExtra(LoginActivity.INTENT_TAG_PASSWORD, password);
                startActivityForResult(i, PointsActivity.REQUEST_AUTHORIZATION);
            } else {
                // Необходимо сделать запрос на авторизацию
                if (login.length() < 1 || password.length() < 1){
                    Toast.makeText(getActivity(), getString(R.string.incorrect_login_on_password), Toast.LENGTH_SHORT).show();
                }

                Authorization authorization = new Authorization(login, password);
                new AsyncTaskAuthorization().execute(authorization);
            }
        }else{
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(i, PointsActivity.REQUEST_AUTHORIZATION);
        }
    }

    class AsyncTaskAuthorization extends AsyncTask<Authorization, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Authorization... params) {
            for (Authorization authorization: params){
                try{
                    Global.sAuthorized = authorization.authorize();
                }catch (IOException e){
                    Global.sAuthorized = false;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            getData();
        }
    }

    public void getData(){

    }

    public void setData(){

    }
}
