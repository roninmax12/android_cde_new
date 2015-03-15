package com.rudik_maksim.cde_material.modules.parsers;

import android.util.Log;

import com.rudik_maksim.cde_material.StringHelper;
import com.rudik_maksim.cde_material.modules.DeSystem;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.Rating;
import com.rudik_maksim.cde_material.modules.Schedule;
import com.rudik_maksim.cde_material.modules.ScheduleDay;
import com.rudik_maksim.cde_material.modules.User;
import com.rudik_maksim.cde_material.modules.interfaces.IParser;
import com.rudik_maksim.cde_material.modules.items.RatingItem;
import com.rudik_maksim.cde_material.modules.items.ScheduleItem;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by maksimrudik on 06.03.15.
 */
public class ScheduleParser implements IParser{
    String TAG = "ScheduleParser";

    private final String JSON_DAY = "day_week";
    private final String JSON_WEEK = "week_type";
    private final String JSON_TIME = "time";
    private final String JSON_ROOM = "room";
    private final String JSON_PLACE = "place";
    private final String JSON_SUBJECT = "title_subject";
    private final String JSON_TEACHER = "person_title";
    private final String JSON_STATUS = "status";

    @SuppressWarnings("deprecation")
    @Override
    public void parse() throws IOException, ParserConfigurationException, XPathExpressionException{
        String hash = "%D0%B8";
        String group = "";

        if (User.get().isIhbtStudent()) {
            group = hash + User.get().getCurrentGroup().substring(1);
        } else {
            group = User.get().getCurrentGroup();
        }

        String scheduleJson = Network.sendPost(DeSystem.getScheduleUrl(), DeSystem.setScheduleParams(group));

        Schedule schedule = Schedule.get();

        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(scheduleJson);
            JSONArray jsonArray = (JSONArray) obj;

            ScheduleDay scheduleDay = new ScheduleDay();
            String lastDay = "";

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                ScheduleItem scheduleItem = new ScheduleItem();

                if (i == 0)
                    lastDay = jsonObject.get(JSON_DAY).toString();

                String currentDay = jsonObject.get(JSON_DAY).toString();

                switch (Integer.parseInt(jsonObject.get(JSON_WEEK).toString())) {
                    case 0:
                        scheduleItem.setWeek(ScheduleItem.Week.ALL);
                        break;
                    case 1:
                        scheduleItem.setWeek(ScheduleItem.Week.EVEN);
                        break;
                    case 2:
                        scheduleItem.setWeek(ScheduleItem.Week.OVEN);
                        break;
                }


                scheduleItem.setTime(
                        StringHelper.getRightFormatTime(
                                Global.sApplicationContext,
                                jsonObject.get(JSON_TIME).toString()
                        ));

                scheduleItem.setPlace(
                        StringHelper.getRightFormatPlace(
                                Global.sApplicationContext,
                                jsonObject.get(JSON_PLACE).toString()
                        )
                );

                scheduleItem.setRoom(jsonObject.get(JSON_ROOM).toString());
                scheduleItem.setTeacher(jsonObject.get(JSON_TEACHER).toString());
                scheduleItem.setSubject(jsonObject.get(JSON_SUBJECT).toString());
                scheduleItem.setSubjectStatus(jsonObject.get(JSON_STATUS).toString());

                if (!currentDay.equals(lastDay)) {
                    schedule.put(scheduleDay);
                    scheduleDay = new ScheduleDay();
                    lastDay = currentDay;
                }

                scheduleDay.setDay(
                        StringHelper.getDayForIndex(
                                Global.sApplicationContext,
                                Integer.parseInt(currentDay)
                        )
                );
                scheduleDay.put(scheduleItem);

                if (i == jsonArray.size() - 1) {
                    schedule.put(scheduleDay);
                }
            }

        }catch (Exception ex) {
            Log.d(TAG, "error parse schedule: " + ex.toString());
        };
    }

}
