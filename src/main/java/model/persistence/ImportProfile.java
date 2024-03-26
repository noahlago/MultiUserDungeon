package model.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

public class ImportProfile {
    ProfileCSVFileDAO profileManager;

    public ImportProfile(ProfileCSVFileDAO profileManager){
        this.profileManager = profileManager;
    }

    public void importCSV(String filename) throws FileNotFoundException{
        File importFile = new File(filename);
        Scanner scanner = new Scanner(importFile);
        String userInfo = scanner.nextLine();
        String[] stats = userInfo.split(",");
        User user = new User(stats[0], stats[1], Integer.parseInt(stats[2]), Integer.parseInt(stats[3]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[6]));
        profileManager.newUser(user);

        scanner.close();
    }

    public void importXML(String filename) throws SAXException, IOException, ParserConfigurationException{
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(filename);
        Element element = doc.getDocumentElement();

        String username = getValue(element, "username");
        String password = getValue(element, "password");
        int gamesPlayed = Integer.parseInt(getValue(element, "gamesPlayed"));
        int livesLost = Integer.parseInt(getValue(element, "livesLost"));
        int monstersKilled = Integer.parseInt(getValue(element, "monstersKilled"));
        int totalGold = Integer.parseInt(getValue(element, "totalGold"));
        int itemsFound = Integer.parseInt(getValue(element, "itemsFound"));

        User user = new User(username, password, gamesPlayed, livesLost, monstersKilled, totalGold, itemsFound);
        profileManager.newUser(user);
    }

    public void importJSON(String filename) throws StreamReadException, DatabindException, IOException{
        File importFile = new File(filename);
        ObjectMapper objectMapper = new ObjectMapper(); 
        User user = objectMapper.readValue(importFile, User.class);
        profileManager.newUser(user);
    }

    private String getValue(Element element, String tag){
        String value = "";
        NodeList list = element.getElementsByTagName(tag);
        value = list.item(0).getFirstChild().getNodeValue();
        return value;
    }
}
