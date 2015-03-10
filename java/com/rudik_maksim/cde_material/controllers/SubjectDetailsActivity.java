package com.rudik_maksim.cde_material.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.adapters.SubjectDetailsAdapter;
import com.rudik_maksim.cde_material.modules.items.PointItem;
import com.rudik_maksim.cde_material.modules.SubjectDetails;

/**
 * Created by maksimrudik on 02.03.15.
 */
public class SubjectDetailsActivity extends Activity {
    ListView mListView;
    TextView mTextView;
    public static SubjectDetails sSubjectDetails;
    public static PointItem sPointItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_listview);

        mListView = (ListView) findViewById(R.id.listView);
        mTextView = (TextView) findViewById(R.id.textView);

        try{
            getActionBar().setTitle(sPointItem.getTitle());
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException unimportant){
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(getString(R.string.do_not_rush));
            return;
        }

        if (sSubjectDetails != null){
            getActionBar().setSubtitle(String.format(getString(R.string.final_rating), sPointItem.getPoint()));
            SubjectDetailsAdapter subjectDetailsAdapter = new SubjectDetailsAdapter(this, sSubjectDetails.getSubjectDetailsItems());
            mListView.setAdapter(subjectDetailsAdapter);
        }else{
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(getString(R.string.no_data));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sSubjectDetails = null;
        sPointItem = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.subject_details_info, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
