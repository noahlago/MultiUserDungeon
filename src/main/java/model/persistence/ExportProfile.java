package model.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;

/**
 * This class allows for user profiles to be exported from the current game session, one at a time. 
 * Supports exporting profiles to CSV, XML, or JSON files, in a format that is compatible with the import subsytem. 
 * This ensures that user profiles which are exported from a game session can then be imported back into the same game session, or into any other session run by this system. 
 * @author Noah Lago ndl3389@rit.edu
 */
public class ExportProfile {
    /**The instance of ProfileCSVFileDAO that us being used by the curreng game session to manage all user profiles. */
    private ProfileCSVFileDAO profileManager;

    public ExportProfile(ProfileCSVFileDAO profileManager){
        this.profileManager = profileManager;
    }

    /**
     * This method is responsible for exporting singular user profiles to their own csv file, with the specified file name. 
     * @param filename the specified name of the file (to be created of overwritten) where the profile will be stored
     * @param username specifies which profile to export from the current game session
     * @throws IOException if there is an issue opening or writing to the file
     */
    public void exportCSV(String filename, String username) throws IOException{
        File exportFile = makeFile(filename); //creates a file with the specified filename (if the filename is not being used)
        FileWriter writer = new FileWriter(exportFile);
        User user = profileManager.getUser(username); //gets the specified user from the ProfileCSVFileDAO

        writer.append(username + "," + user.getPassword() + "," + user.getGamesPlayed() + "," + user.getLivesLost() + "," + user.getMonstersKilled() + "," + user.getTotalGold() + "," + user.getItemsFound() + "\n");

        writer.flush();
        writer.close();
    }
    
    /**
     * Writes the profile stats of the specified used profile from this game session to a new xml file, in a format that matches the expectations of this system's import options. 
     * @param filename specified name for the new file to be created
     * @param username specifies which user profile is to be exported from the game session
     * @throws IOException if there is an issue creating/opening the specified file 
     * @throws ParserConfigurationException when there is a serious error while creating/configuring the parser
     * @throws SAXException if the xml parser encounters an error
     * @throws TransformerFactoryConfigurationError if there is an error with the configuration/setup of the transformer factory
     * @throws TransformerException if there is an error with the transformer instance, or while it tries to transform the elements to an xml file
     */
    public void exportXML(String filename, String username) throws IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException{
        File exportFile = makeFile(filename); //creates a file with the specified filename (if the filename is not being used)
        User user = profileManager.getUser(username); //gets the specified user from the ProfileCSVFileDAO

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); //creates a new document builder using the javax.xml.parsers library
        Document doc = builder.newDocument();
        Element userElement = doc.createElement("profile"); //The root element for the user profile to be exported
        
        //adds the profile's username to the root element
        Element usernameElement = doc.createElement("username");
        usernameElement.appendChild(doc.createTextNode(user.getUsername()));
        userElement.appendChild(usernameElement);

        //adds the profile's password to the root element
        Element passwordElement = doc.createElement("password");
        passwordElement.appendChild(doc.createTextNode(user.getPassword()));
        userElement.appendChild(passwordElement);

        //adds the profile's gamesPlayed stat to the root element
        Element gamesPlayed = doc.createElement("gamesPlayed");
        gamesPlayed.appendChild(doc.createTextNode("" + user.getGamesPlayed()));
        userElement.appendChild(gamesPlayed);

        //adds the profile's livesLost stat to the root element
        Element livesLost = doc.createElement("livesLost");
        livesLost.appendChild(doc.createTextNode("" + user.getLivesLost()));
        userElement.appendChild(livesLost);

        //adds the profile's monstersKilled stat to the root element
        Element monstersKilled = doc.createElement("monstersKilled");
        monstersKilled.appendChild(doc.createTextNode("" + user.getMonstersKilled()));
        userElement.appendChild(monstersKilled);

        //adds the profile's totalGold stat to the root element
        Element totalGold = doc.createElement("totalGold");
        totalGold.appendChild(doc.createTextNode("" + user.getTotalGold()));
        userElement.appendChild(totalGold);

        //adds the profile's itemsFound stat to the root element
        Element itemsFound = doc.createElement("itemsFound");
        itemsFound.appendChild(doc.createTextNode("" + user.getItemsFound()));
        userElement.appendChild(itemsFound);

        doc.appendChild(userElement); //adds the root element (and therefore all of its child elements) to the document

        Transformer transformer = TransformerFactory.newInstance().newTransformer(); //creates a new transformer from the javax.xml.parsers library

        //sets up the structor of the xml output generated by the transformer
        transformer.setOutputProperty(OutputKeys.METHOD, "xml"); 
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(exportFile))); //uses the transformer to write the elements of the document to the export file
    }

    /**
     * This method is responsible for writing a singular user profile's stats to a new json file. 
     * @param filename specified filename to create and export the profile to
     * @param username specified username of the profile to export
     * @throws IOException if there is an issue creating/opening the file
     */
    public void exportJSON(String filename, String username) throws IOException{
        File exportFile = makeFile(filename); //creates a file with the specified filename (if the filename is not being used)
        ObjectMapper objectMapper = new ObjectMapper();
        User user = profileManager.getUser(username); //gets the specified user from the ProfileCSVFileDAO
        objectMapper.writeValue(exportFile, user); //Uses Jackson's ObjectMapper to write the user instance as a json object to the json file
    }

    /**
     * Helper method to create a new file with the specified filename, and throw an error if the specified filename is already being user by another file. 
     * This avoids overriding any pre-existing files or exported user profiles. 
     * @param filename name of the file to be created
     * @return the File instance that was created
     * @throws IOException if a file of the specified filename already exists
     */
    private File makeFile(String filename) throws IOException{
        File exportFile = new File(filename);
        if(exportFile.createNewFile()){ //creates a new file with the specified name
            System.out.println("Fle: '" + filename + "' successfully created.");
        }else{
            System.out.println("File: '" + filename + "' already exists. ");
            throw new IOException("Export file coult not be created. Filename already in use. "); //throws an exception if the filename is already in use
        }

        return exportFile; //returns the newly created file if there were no issues
    }
}
