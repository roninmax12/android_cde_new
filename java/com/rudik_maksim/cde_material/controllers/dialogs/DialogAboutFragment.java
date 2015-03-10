package com.rudik_maksim.cde_material.controllers.dialogs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rudik_maksim.cde_material.R;

/**
 * Created by maksimrudik on 07.03.15.
 */
public class DialogAboutFragment extends AbstractDialogFragment {
    public static final String TAG_ABOUT = "DialogAboutFragment";

    @Override
    protected int setLayout() {
        return R.layout.dialog_about;
    }

    @Override
    protected String setTitle() {
        return String.format(getString(R.string.version_plus_number), getString(R.string.app_version));
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        LinearLayout linLayoutDevPage = (LinearLayout) v.findViewById(R.id.dialog_about_linLayout_developerPage);
        LinearLayout linLayoutAppPage = (LinearLayout) v.findViewById(R.id.dialog_about_linLayout_applicationPage);
        LinearLayout linLayoutAppGooglePage = (LinearLayout) v.findViewById(R.id.dialog_about_linLayout_applicationGooglePage);

        linLayoutAppGooglePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String appPackageName = getActivity().getPackageName();
                String appPackageName = "com.rudik_maksim.cde";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                dismiss();
            }
        });

        linLayoutAppPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.app_site)));
                startActivity(i);
                dismiss();
            }
        });

        linLayoutDevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.dev_site)));
                startActivity(i);
                dismiss();
            }
        });
    }
}
