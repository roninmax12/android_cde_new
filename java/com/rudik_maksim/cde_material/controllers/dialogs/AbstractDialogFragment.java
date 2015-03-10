package com.rudik_maksim.cde_material.controllers.dialogs;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.rudik_maksim.cde_material.R;

/**
 * Created by maksimrudik on 07.03.15.
 */
public abstract class AbstractDialogFragment extends DialogFragment{
    protected abstract int setLayout();
    protected abstract String setTitle();

    public boolean needTitleArea(){
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Dialog dialog = super.getDialog();

        if (needTitleArea()) {
            dialog.setTitle(setTitle());

            // Set title divider color
            int titleId = getResources().getIdentifier("title", "id", "android");
            int titleDividerId = getResources().getIdentifier("titleDivider", "id", "android");

            View titleDivider = dialog.findViewById(titleDividerId);
            View titleView = dialog.findViewById(titleId);

            if (titleView != null)
                titleView.setBackgroundColor(getResources().getColor(R.color.main_blue));

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                if (titleDivider != null)
                    titleDivider.setBackgroundColor(getResources().getColor(R.color.main_blue));
            } else {
                if (titleView != null) {
                    titleView.setPadding(50, 50, 50, 50);
                }
            }
        }else{
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        return inflater.inflate(setLayout(), container, false);
    }
}
