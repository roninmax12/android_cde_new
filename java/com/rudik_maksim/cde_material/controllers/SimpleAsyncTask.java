package com.rudik_maksim.cde_material.controllers;

import android.os.AsyncTask;

import com.rudik_maksim.cde_material.modules.interfaces.INetworkListener;
import com.rudik_maksim.cde_material.modules.interfaces.IParser;

/**
 * Created by maksimrudik on 10.03.15.
 */
public class SimpleAsyncTask<T extends IParser> extends AsyncTask<Void, Void, Void> {
    private boolean access = true;
    private T object = null;
    private INetworkListener mNetworkListener;

    public SimpleAsyncTask(T obj, INetworkListener networkListener){
        object = obj;
        mNetworkListener = networkListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mNetworkListener.onStartQuery();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try{
            object.parse();
        }catch (Exception e){
            access = false;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (access)
            mNetworkListener.onFinishQuery();
    }
}
