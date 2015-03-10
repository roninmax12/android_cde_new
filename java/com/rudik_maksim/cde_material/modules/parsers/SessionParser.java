package com.rudik_maksim.cde_material.modules.parsers;

import android.util.Log;

import com.rudik_maksim.cde_material.modules.DeSystem;
import com.rudik_maksim.cde_material.modules.Global;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.Session;
import com.rudik_maksim.cde_material.modules.User;
import com.rudik_maksim.cde_material.modules.interfaces.IParser;
import com.rudik_maksim.cde_material.modules.items.SessionItem;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by maksimrudik on 10.03.15.
 */
public class SessionParser implements IParser{
    String TAG = "SessionParser";

    @SuppressWarnings("deprecation")
    @Override
    public void parse() throws IOException, ParserConfigurationException, XPathExpressionException {
        Log.d(TAG, "start");
        URL url = DeSystem.getSessionUrl(User.get());
        TagNode tagNode = new HtmlCleaner().clean(url, Network.Encoding_Utf8);
        Log.d(TAG, "tagNode created");
        org.w3c.dom.Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();
        Log.d(TAG, "newXPath");

        NodeList subjects = (NodeList) xpath.evaluate("//table[@class='rasp_tabl']//tbody//td[@class=\"lesson\"]//dl//dd",doc, XPathConstants.NODESET);

        Session mSession = Session.get();

        for (int i = 0; i < subjects.getLength(); i++){
            Log.d(TAG, "iteration: " + i);
            String subject_name = subjects.item(i).getTextContent().trim();

            SessionItem examItem = new SessionItem();
            SessionItem consultationItem = new SessionItem();

            String date = (String) xpath.evaluate("//div["+ (i + 1) +"]/table[@class='rasp_tabl']//tbody//tr//th//span//text()", doc, XPathConstants.STRING);
            String time = (String) xpath.evaluate("//div["+ (i + 1) +"]/table[@class='rasp_tabl']//tbody//tr//td[@class=\"time\"]//span//text()", doc, XPathConstants.STRING);
            String room = (String) xpath.evaluate("//div["+ (i + 1) +"]/table[@class='rasp_tabl']//tbody//tr//td[@class=\"room\"]//span//text()", doc, XPathConstants.STRING);
            String teacher = (String) xpath.evaluate("//div["+ (i + 1) +"]/table[@class='rasp_tabl']//tbody//tr//td[@class=\"lesson\"]//dl//dt//b//text()", doc, XPathConstants.STRING);
            String cons_str = (String) xpath.evaluate("//div["+ (i + 1) +"]/table[@class='rasp_tabl']//tbody//tr//td[@class=\"lesson\"]//dl//dt[2]//text()", doc, XPathConstants.STRING);

            if (subject_name.length() < 2) subject_name = "-";
            if (date.length() < 2) date = "-";
            if (time.length() < 2) time = "-";
            if (room.length() < 2) room = "-";
            if (teacher.length() < 2) teacher = "-";

            String cons_date = "-", cons_time = "-", cons_place = "-";

            if (cons_str.length() > 2){
                String[] arr = cons_str.split(" ");

                boolean cons_date_exist = false;
                boolean cons_time_exist = false;
                boolean cons_place_exist = false;

                for (int j = 0; j < arr.length; j++){
                    if (arr[j].contains("Консультация")){
                        cons_date_exist = true;
                        continue;
                    }

                    if (cons_date_exist){
                        cons_date = arr[j];
                        cons_date_exist = false;
                        continue;
                    }

                    if (arr[j].contains("в")){
                        cons_time_exist = true;
                        continue;
                    }

                    if (cons_time_exist){
                        cons_time = arr[j];
                        cons_time_exist = false;
                        continue;
                    }

                    if (arr[j].contains("Место")){
                        cons_place_exist = true;
                        continue;
                    }

                    if (cons_place_exist){
                        cons_place = arr[j];

                        if (arr.length - j > 1)
                            cons_place += " " + arr[j+1];

                        cons_place_exist = false;
                    }
                }
            }

            examItem.setTitle(subject_name);
            examItem.setDate(date);
            examItem.setRoom(room);
            examItem.setTime(time);
            examItem.setTeacher(teacher);

            consultationItem.setTitle(subject_name);
            consultationItem.setDate(cons_date);
            consultationItem.setRoom(cons_place);
            consultationItem.setTime(cons_time);
            consultationItem.setTeacher(teacher);

            mSession.putExam(examItem);
            mSession.putConsultation(consultationItem);
        }

        Log.d(TAG, "finish");
    }
}
