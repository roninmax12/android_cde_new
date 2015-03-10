package com.rudik_maksim.cde_material.modules.parsers;

import com.rudik_maksim.cde_material.modules.DeSystem;
import com.rudik_maksim.cde_material.modules.Network;
import com.rudik_maksim.cde_material.modules.Rating;
import com.rudik_maksim.cde_material.modules.items.RatingItem;
import com.rudik_maksim.cde_material.modules.interfaces.IParser;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
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
public class RatingParser implements IParser{
    @SuppressWarnings("deprecation")
    @Override
    public void parse() throws IOException, ParserConfigurationException, XPathExpressionException{
        TagNode tagNode = new HtmlCleaner().clean(DeSystem.getRatingUrl(), Network.Encoding_Cp1251);
        org.w3c.dom.Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);

        XPath xpath = XPathFactory.newInstance().newXPath();

        ArrayList<String> faculty = new ArrayList<String>();
        ArrayList<String> course = new ArrayList<String>();
        ArrayList<String> position = new ArrayList<String>();

        //FACULTY
        NodeList f = (NodeList) xpath.evaluate("//div[@class='d_text']//table[@class='d_table']//tbody//tr//td[1]//text()",doc, XPathConstants.NODESET);
        for (int i = 0; i < f.getLength(); i++){
            faculty.add(f.item(i).getTextContent().trim());
        }

        //COURSE
        NodeList c = (NodeList) xpath.evaluate("//div[@class='d_text']//table[@class='d_table']//tbody//tr//td[2]//text()",doc, XPathConstants.NODESET);
        for (int i = 0; i < c.getLength(); i++){
            course.add(c.item(i).getTextContent().trim());
        }

        //POSITION
        NodeList p = (NodeList) xpath.evaluate("//div[@class='d_text']//table[@class='d_table']//tbody//tr//td[3]//text()",doc, XPathConstants.NODESET);
        for (int i = 0; i < p.getLength(); i++){
            position.add(p.item(i).getTextContent().trim());
        }

        Rating mRating = Rating.get();
        for (int i = 0; i < faculty.size(); i++){
            RatingItem ratingItem = new RatingItem();
            ratingItem.setFaculty(faculty.get(i));
            ratingItem.setCourse(course.get(i));
            ratingItem.setPosition(position.get(i));

            mRating.put(ratingItem);
        }

        faculty.clear();
        course.clear();
        position.clear();
    }
}
