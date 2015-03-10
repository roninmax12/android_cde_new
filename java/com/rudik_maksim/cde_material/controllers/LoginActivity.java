package com.rudik_maksim.cde_material.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rudik_maksim.cde_material.R;
import com.rudik_maksim.cde_material.controllers.activities.AbstractActivity;
import com.rudik_maksim.cde_material.modules.ApplicationPreferences;
import com.rudik_maksim.cde_material.modules.Authorization;
import com.rudik_maksim.cde_material.modules.Global;

import java.io.IOException;

public class LoginActivity extends AbstractActivity {
    TextView mLoginTextView;
    TextView mPasswordTextView;
    Button mSigninButton;
    Context mContext;

    public final static String INTENT_TAG_LOGIN = "com.rudik_maksim.cde_material.controllers.loginactivity.login";
    public final static String INTENT_TAG_PASSWORD = "com.rudik_maksim.cde_material.controllers.loginactivity.password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginTextView = (TextView) findViewById(R.id.editTextLogin);
        mPasswordTextView = (TextView) findViewById(R.id.editTextPassword);

        String login = getIntent().getStringExtra(INTENT_TAG_LOGIN);
        String password = getIntent().getStringExtra(INTENT_TAG_PASSWORD);

        mLoginTextView.setText(login);
        mPasswordTextView.setText(password);

        if (Global.sAuthorizationPreferences == null)
            Global.sAuthorizationPreferences = new ApplicationPreferences.AuthorizationPreferences(getApplicationContext());

        mContext = this;

        mSigninButton = (Button) findViewById(R.id.btnConnect);
        mSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginTextView.getText().toString();
                String password = mPasswordTextView.getText().toString();

                if (login.length() < 1 || password.length() < 1){
                    Toast.makeText(mContext, getString(R.string.incorrect_login_on_password), Toast.LENGTH_SHORT).show();
                    return;
                }

                Authorization authorization = new Authorization(login, password);
                new AsyncTaskAuthorization().execute(authorization);
            }
        });
    }

    class AsyncTaskAuthorization extends AsyncTask<Authorization, Void, Void>{
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
            if (Global.sAuthorized){
                setResult(RESULT_OK);
                finish();
            }else{
                Toast.makeText(mContext, getString(R.string.authorization_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
