package com.rudik_maksim.cde_material.modules;

import android.os.AsyncTask;

import com.rudik_maksim.cde_material.controllers.SimpleAsyncTask;
import com.rudik_maksim.cde_material.modules.interfaces.INetworkListener;
import com.rudik_maksim.cde_material.modules.parsers.PointParser;
import com.rudik_maksim.cde_material.modules.parsers.RatingParser;
import com.rudik_maksim.cde_material.modules.parsers.ScheduleParser;
import com.rudik_maksim.cde_material.modules.parsers.SessionParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class Network {
    public static final String Encoding_Cp1251 = "cp1251";
    public static final String Encoding_Utf8 = "utf8";

    public static String sendPost(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String sendGet(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class Query{
        INetworkListener mNetworkListener;
        public final int ACTION_POINTS = 0;
        public final int ACTION_RATING = 2;
        public final int ACTION_SCHEDULE = 3;
        public final int ACTION_SESSION = 4;

        public Query(INetworkListener networkListener){
            mNetworkListener = networkListener;
        }

        public void send(int action){
            switch (action){
                case ACTION_POINTS:
                    new SimpleAsyncTask<PointParser>(new PointParser(), mNetworkListener).execute();
                    break;
                case ACTION_RATING:
                    new SimpleAsyncTask<RatingParser>(new RatingParser(), mNetworkListener).execute();
                    break;
                case ACTION_SCHEDULE:
                    new SimpleAsyncTask<ScheduleParser>(new ScheduleParser(), mNetworkListener).execute();
                    break;
                case ACTION_SESSION:
                    new SimpleAsyncTask<SessionParser>(new SessionParser(), mNetworkListener).execute();
                    break;
            }
        }
    }
}
