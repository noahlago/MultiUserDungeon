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

/**
 * This class allows for user profiles to be imported into the MUD game from external CSV, JSON, or XML files. 
 * Accepts files containing a singular user profile's information, matching the format through which this system exports profiles to the same file formats. 
 * @author Noah Lago ndl3389@rit.edu
 */
public class ImportProfile {
    ProfileCSVFileDAO profileManager;

    public ImportProfile(ProfileCSVFileDAO profileManager){
        this.profileManager = profileManager;
    }

    /**
     * This method is responsible for importing a singular user profile from a csv file containing one user's stats. 
     * The import file is expected to match the format of this system's exported files. 
     * @param filename path of the designated import file
     * @throws FileNotFoundException if the specified file is not found in the expected data folder
     */
    public void importCSV(String filename) throws FileNotFoundException{
        File importFile = new File(filename);
        Scanner scanner = new Scanner(importFile);
        String userInfo = scanner.nextLine();
        String[] stats = userInfo.split(","); //Splits the values of the csv file

        User user = new User(stats[0], stats[1], Integer.parseInt(stats[2]), Integer.parseInt(stats[3]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[6]));
        profileManager.newUser(user); //adds the new user to the ProfileCSVFileDAO instance

        scanner.close();
    }

    /**
     * This method is responsible for importing a singular user profile from an xml file containing one user's stats. 
     * The import file is expected to match the format of this system's exported files. 
     * @param filename path of the designated import file
     * @throws IOException if the specified file is not found in the expected data folder, or cannot be read
     * @throws SAXException if an error occurs with the xml parser
     * @throws ParserConfigurationException if a serious error occurs with the initialization/setup of the parser
     */
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
        profileManager.newUser(user); //adds the new user to the ProfileCSVFileDAO instance
    }

    /**
     * This method is responsible for importing a singular user profile from a json file containing one user's stats. 
     * The import file is expected to match the format of this system's exported files. 
     * @param filename path of the designated import file
     * @throws IOException if the specified file is not found in the expected data folder, or cannot be read
     * @throws StreamReadException if there is an issue reading from the specified file's stream
     * @throws DatabindException if there is an issue with binding json objects to their specified classes 
     */
    public void importJSON(String filename) throws StreamReadException, DatabindException, IOException{
        File importFile = new File(filename);
        ObjectMapper objectMapper = new ObjectMapper(); 
        User user = objectMapper.readValue(importFile, User.class);
        profileManager.newUser(user); //adds the new user to the ProfileCSVFileDAO instance
    }

    /**
     * Helper class to simplify getting elements from an xml file using their tags.
     * @param element the root element of the xml file
     * @param tag the tag of the element to be accessed
     * @return the element connected to the root element with the designated tag
     */
    private String getValue(Element element, String tag){
        String value = "";
        NodeList list = element.getElementsByTagName(tag);
        value = list.item(0).getFirstChild().getNodeValue();
        return value;
    }
}
