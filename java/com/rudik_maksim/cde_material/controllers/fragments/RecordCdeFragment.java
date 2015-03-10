package com.rudik_maksim.cde_material.controllers.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.modules.DeSystem;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.User;

/**
 * Created by maksimrudik on 07.03.15.
 */
public class RecordCdeFragment extends AbstractListFragment {
    FrameLayout mFrameLayout;
    WebView mWebView;
    TextView mTextView;
    ProgressBar mProgressBar;
    Animation mAnimationTop;

    final int MAX_PROGRESS = 100;

    String TAG = "RecordCdeFragment";

    @Override
    protected boolean retainInstance() {
        return true;
    }

    public static RecordCdeFragment newInstance(int sectionNumber) {
        RecordCdeFragment fragment = new RecordCdeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTextView.setVisibility(View.INVISIBLE);
        mWebView.destroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, container, false);

        mAnimationTop = AnimationUtils.loadAnimation(getActivity(), R.anim.up_top);
        mFrameLayout = (FrameLayout) v.findViewById(R.id.frameLayout);
        mWebView = (WebView) v.findViewById(R.id.webView);
        mTextView = (TextView) v.findViewById(R.id.textView);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mProgressBar.setMax(MAX_PROGRESS);

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();

        if (Global.sAuthorized){
            getData();
        }
    }

    public void getData(){
        if (Global.sAuthorized){
            mTextView.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.INVISIBLE);

            loadUrl();
        }
    }

    int checked_count = 0;

    @SuppressLint("SetJavascriptEnabled")
    public void loadUrl(){
        User user = User.get();

        if (user != null){
            mWebView.clearHistory();
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new RecordCdeWebClient());
            mWebView.setWebChromeClient(new RecordCdeWebChromeClient());

            mWebView.loadUrl(DeSystem.getRecordCdeUrl(user.getLogin(), user.getPassword()));
        }else{
            mTextView.setText(getString(R.string.no_data));
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    public class RecordCdeWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            try{
                view.loadUrl(url);
            }catch (Exception unimportant){}

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (++checked_count == 2) {
                // 2 - т.к. при ПЕРВОМ запуске этого WevView страница загружается только на ВТОРОМ срабатывании данного метода.
                mFrameLayout.startAnimation(mAnimationTop);

                mWebView.setVisibility(View.VISIBLE);
                checked_count = 0; // was be changed to zero, because this is working on android 4.4.2
                // 1 - т.к. при ПОВТОРНОМ запуске этого WevView страница загружается на ПЕРВОМ (одна операция инкрементации) срабатывании данного метода.
            }
        }
    }

    public class RecordCdeWebChromeClient extends WebChromeClient{
        boolean isFirstQuery = true; // т.к. у нас идет 2 запроса (1ый - на авторизацию)
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if (newProgress == MAX_PROGRESS){
                if (isFirstQuery)
                    isFirstQuery = false;
                else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mTextView.setVisibility(View.INVISIBLE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }else{
                if (newProgress >= 15) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);

                    mTextView.setText(getString(R.string.please_wait));
                    mTextView.setVisibility(View.VISIBLE);

                    mWebView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
