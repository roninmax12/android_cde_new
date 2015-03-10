package com.rudik_maksim.cde_material.modules.interfaces;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Created by maksimrudik on 26.02.15.
 */
public interface IParser {
    public void parse() throws IOException, ParserConfigurationException, XPathExpressionException;
}
