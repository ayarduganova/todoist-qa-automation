package com.example.UntitledTestSuite.util;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public final class Settings {
    private static final String FILE_NAME = "src/test/resources/Settings.xml";
    private static Document document;

    private static String baseUrl;
    private static String email;
    private static String password;
    private static String username;

    static {
        try {
            File xmlFile = new File(FILE_NAME);
            if (!xmlFile.exists()) {
                throw new FileNotFoundException("Settings file не найден: " + FILE_NAME);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load settings", e);
        }
    }

    public static String getBaseUrl() {
        if (baseUrl == null) {
            baseUrl = getNodeValue("BaseUrl");
        }
        return baseUrl;
    }

    public static String getLogin() {
        if (email == null) {
            email = getNodeValue("Email");
        }
        return email;
    }

    public static String getPassword() {
        if (password == null) {
            password = getNodeValue("Password");
        }
        return password;
    }

    public static String getUsername() {
        if (username == null) {
            username = getNodeValue("Username");
        }
        return username;
    }

    private static String getNodeValue(String tagName) {
        NodeList nodes = document.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) {
            throw new RuntimeException("Tag not found: " + tagName);
        }
        return nodes.item(0).getTextContent();
    }
}
