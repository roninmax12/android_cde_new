package com.rudik_maksim.cde_material.modules.parsers;

import android.util.Log;

import com.rudik_maksim.cde_material.modules.DeSystem;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.items.PointItem;
import com.rudik_maksim.cde_material.modules.Points;
import com.rudik_maksim.cde_material.modules.PointsSemester;
import com.rudik_maksim.cde_material.modules.SubjectDetails;
import com.rudik_maksim.cde_material.modules.items.SubjectDetailsItem;
import com.rudik_maksim.cde_material.modules.User;
import com.rudik_maksim.cde_material.modules.interfaces.IParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by maksimrudik on 26.02.15.
 */
public class PointParser implements IParser{
    String TAG = "PointParser";
    private final String JSON_YEARS = "years";
    private final String JSON_YEARS_GROUP = "group";
    private final String JSON_YEARS_STUDYYEAR = "studyyear";
    private final String JSON_YEARS_SUBJECTS = "subjects";
    private final String JSON_YEARS_SUBJECTS_NAME = "name";
    private final String JSON_YEARS_SUBJECTS_SEMESTER = "semester";
    private final String JSON_YEARS_SUBJECTS_MARKS = "marks";
    private final String JSON_YEARS_SUBJECTS_MARKS_MARK = "mark";
    private final String JSON_YEARS_SUBJECTS_MARKS_MARKDATE = "markdate";
    private final String JSON_YEARS_SUBJECTS_MARKS_WORKTYPE = "worktype";
    private final String JSON_YEARS_SUBJECTS_POINTS = "points";
    private final String JSON_YEARS_SUBJECTS_POINTS_VARIABLE = "variable";
    private final String JSON_YEARS_SUBJECTS_POINTS_MAX = "max";
    private final String JSON_YEARS_SUBJECTS_POINTS_LIMIT = "limit";
    private final String JSON_YEARS_SUBJECTS_POINTS_VALUE = "value";

    Points points = Points.get();
    User user = User.get();

    @Override
    public void parse() throws IOException, ParserConfigurationException, XPathExpressionException {
        String eregisterJson = Network.sendGet(DeSystem.getEregisterUrl());

        Log.d(TAG, eregisterJson); // It works

        try{
            JSONObject obj = (JSONObject) new JSONParser().parse(eregisterJson);

            JSONArray years = (JSONArray) obj.get(JSON_YEARS);

            for (int i = 0; i < years.size(); i++){
                JSONObject yearObject = (JSONObject) years.get(i);

                String group = yearObject.get(JSON_YEARS_GROUP).toString();
                String studyyear = yearObject.get(JSON_YEARS_STUDYYEAR).toString();

                if (i == years.size() - 1) {
                    user.setCurrentGroup(group);
                    points.setLastYear(studyyear);
                }

                JSONArray subjects = (JSONArray) yearObject.get(JSON_YEARS_SUBJECTS);

                PointsSemester pointsSemester_1 = new PointsSemester();
                PointsSemester pointsSemester_2 = new PointsSemester();

                int firstSemester = 0;

                for (int j = 0; j < subjects.size(); j++){
                    JSONObject subjectObject = (JSONObject) subjects.get(j);
                    PointItem pointItem = new PointItem();

                    String title = subjectObject.get(JSON_YEARS_SUBJECTS_NAME).toString();
                    int semester = Integer.parseInt(subjectObject.get(JSON_YEARS_SUBJECTS_SEMESTER).toString());

                    if (firstSemester == 0)
                        firstSemester = semester;

                    // Если значение semester == firstSemester => Работаем с pointsSemester_1, иначе c pointsSemester_2

                    String control = "";

                    JSONArray marks = (JSONArray) subjectObject.get(JSON_YEARS_SUBJECTS_MARKS);

                    for (int m = 0; m < marks.size(); m++){
                        JSONObject markObject = (JSONObject) marks.get(m);

                        if (control.equals(""))
                            control = markObject.get(JSON_YEARS_SUBJECTS_MARKS_WORKTYPE).toString();
                        else
                            control += ", " + markObject.get(JSON_YEARS_SUBJECTS_MARKS_WORKTYPE).toString();
                    }

                    pointItem.setTitle(title);
                    pointItem.setContol(control);

                    JSONArray details = (JSONArray) subjectObject.get(JSON_YEARS_SUBJECTS_POINTS);
                    SubjectDetails subjectDetails = new SubjectDetails();

                    if (details != null){
                        for (int d = 0; d < details.size(); d++){
                            JSONObject detailObject = (JSONObject) details.get(d);
                            SubjectDetailsItem subjectDetailsItem = new SubjectDetailsItem();

                            subjectDetailsItem.setTitle(detailObject.get(JSON_YEARS_SUBJECTS_POINTS_VARIABLE).toString());
                            subjectDetailsItem.setMaxValue(detailObject.get(JSON_YEARS_SUBJECTS_POINTS_MAX).toString());
                            subjectDetailsItem.setLimitValue(detailObject.get(JSON_YEARS_SUBJECTS_POINTS_LIMIT).toString());
                            subjectDetailsItem.setUserValue(detailObject.get(JSON_YEARS_SUBJECTS_POINTS_VALUE).toString());

                            if (d == 0)
                                pointItem.setPoint(detailObject.get(JSON_YEARS_SUBJECTS_POINTS_VALUE).toString());

                            subjectDetails.put(subjectDetailsItem);

                            pointItem.setSubjectDetails(subjectDetails);
                        }
                    }

                    if (firstSemester == semester){
                        // Работаем с pointSemester_1
                        pointsSemester_1.put(pointItem);
                        pointsSemester_1.setSemester(Integer.toString(firstSemester));
                    }else{
                        // Работаем с pointSemester_2
                        pointsSemester_2.put(pointItem);
                        pointsSemester_2.setSemester(Integer.toString(semester));
                    }
                } // END SUBJECTS LOOP

                if (points != null){
                    ArrayList<PointsSemester> pointsSemestersForYear = new ArrayList<PointsSemester>();

                    pointsSemestersForYear.add(pointsSemester_1);
                    pointsSemestersForYear.add(pointsSemester_2);
                    points.put(studyyear, pointsSemestersForYear);
                }

            } // END YEARS LOOP
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, e.toString());
            points = null;
        }

        //test();
    }

    void test(){
        ArrayList<PointsSemester> pointsSemestersForYear = points.getListPoint("2013/2014");

        PointsSemester pointsSemester_1 = pointsSemestersForYear.get(1);

        Log.d(TAG, "semester: " + pointsSemester_1.getSemester());

        ArrayList<PointItem> pointItems = pointsSemester_1.getPointItems();

        for (int i = 0; i < pointItems.size(); i++){
            Log.d(TAG, pointItems.get(i).getTitle() + " : " + pointItems.get(i).getPoint());
        }

        Log.d(TAG, user.getCurrentGroup());
    }
}
