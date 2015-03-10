package com.rudik_maksim.cde_material.modules;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maksimrudik on 28.02.15.
 */
public class DeSystem {
    private static String TAG = "DeSystem";

    private static final String HOST_DE = "https://de.ifmo.ru/";
    private static final String HOST_IFMO = "http://www.ifmo.ru/";

    private static final String EREGISTER = "api/private/eregister";
    private static final String RATING = "servlet/distributedCDE?Rule=REP_EXECUTE_PRINT&REP_ID=1441";
    private static final String RECORD = "--schedule/index.php";
    private static final String SESSION = "ru/exam/0/";

    public static String getEregisterUrl(){
        return HOST_DE + EREGISTER;
    }

    public static URL getRatingUrl() throws MalformedURLException{
        return new URL(HOST_DE + RATING);
    }

    public static String getRecordCdeUrl(String login, String password){
        return HOST_DE + RECORD + "?login=" + login + "&passwd=" + password + "&role=%D1%F2%F3%E4%E5%ED%F2";
    }

    public static URL getSessionUrl(User user) throws MalformedURLException{
        String group = user.getCurrentGroup();

        if (user.isIhbtStudent())
            group = "i" + user.getCurrentGroup().substring(1);

        return new URL(HOST_IFMO + SESSION + group + "/raspisanie_sessii_" + group + ".htm");
    }
}
