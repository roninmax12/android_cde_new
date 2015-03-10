package com.rudik_maksim.cde_material.modules;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class Authorization {
    String mHost = "https://de.ifmo.ru/";
    String mLogin;
    String mPassword;
    CookieStore mCookieStore;
    CookieManager mCookieManager;

    public Authorization(String login, String password) {
        mLogin = login;
        mPassword = password;
    }

    public boolean authorize() throws IOException {
        URL urlAuthorization = new URL(mHost + "servlet/?Rule=LOGON&LOGIN=" + mLogin + "&PASSWD=" + mPassword);
        HttpURLConnection httpURLConnection;
        InputStream inputStream;

        mCookieManager = new CookieManager();
        mCookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(mCookieManager);

        httpURLConnection = (HttpURLConnection) urlAuthorization.openConnection();

        long len = (long) httpURLConnection.getContentLength();
        if (len != 0) {
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            int c;
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            while (((c = inputStream.read()) != -1)) {
                b.write(c);
            }
            String code = b.toString("Cp1251");
            if (code.contains("Invalid"))
                return false;

            mCookieStore = mCookieManager.getCookieStore();
            List<HttpCookie> cookies = mCookieStore.getCookies();

            inputStream.close();
        }

        User.init(mLogin, mPassword);
        Global.sAuthorizationPreferences.put(mLogin, mPassword);

        return true;
    }
}
