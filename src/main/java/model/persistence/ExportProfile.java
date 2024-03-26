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

public class ExportProfile {
    private ProfileCSVFileDAO profileManager;

    public ExportProfile(ProfileCSVFileDAO profileManager){
        this.profileManager = profileManager;
    }

    public void exportCSV(String filename, String username) throws IOException{
        File exportFile = makeFile(filename);
        FileWriter writer = new FileWriter(exportFile);
        User user = profileManager.getUser(username);

        writer.append(username + "," + user.getPassword() + "," + user.getGamesPlayed() + "," + user.getLivesLost() + "," + user.getMonstersKilled() + "," + user.getTotalGold() + "," + user.getItemsFound() + "\n");

        writer.flush();
        writer.close();
    }
    
    public void exportXML(String filename, String username) throws IOException, ParserConfigurationException, SAXException, TransformerFactoryConfigurationError, TransformerException{
        File exportFile = makeFile(filename);
        User user = profileManager.getUser(username);

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();
        Element userElement = doc.createElement("profile");
        
        Element usernameElement = doc.createElement("username");
        usernameElement.appendChild(doc.createTextNode(user.getUsername()));
        userElement.appendChild(usernameElement);

        Element passwordElement = doc.createElement("password");
        passwordElement.appendChild(doc.createTextNode(user.getPassword()));
        userElement.appendChild(passwordElement);

        Element gamesPlayed = doc.createElement("gamesPlayed");
        gamesPlayed.appendChild(doc.createTextNode("" + user.getGamesPlayed()));
        userElement.appendChild(gamesPlayed);

        Element livesLost = doc.createElement("livesLost");
        livesLost.appendChild(doc.createTextNode("" + user.getGamesPlayed()));
        userElement.appendChild(livesLost);

        Element monstersKilled = doc.createElement("monstersKilled");
        monstersKilled.appendChild(doc.createTextNode("" + user.getGamesPlayed()));
        userElement.appendChild(monstersKilled);

        Element totalGold = doc.createElement("totalGold");
        totalGold.appendChild(doc.createTextNode("" + user.getGamesPlayed()));
        userElement.appendChild(totalGold);

        Element itemsFound = doc.createElement("itemsFound");
        itemsFound.appendChild(doc.createTextNode("" + user.getGamesPlayed()));
        userElement.appendChild(itemsFound);

        doc.appendChild(userElement);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(exportFile)));
    }

    public void exportJSON(String filename, String username) throws IOException{
        File exportFile = makeFile(filename);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = profileManager.getUser(username);
        objectMapper.writeValue(exportFile, user);
    }

    private File makeFile(String filename) throws IOException{
        File exportFile = new File(filename);
        if(exportFile.createNewFile()){
            System.out.println("Fle: '" + filename + "' successfully created.");
        }else{
            System.out.println("File: '" + filename + "' already exists. ");
            throw new IOException("Export file coult not be created. Filename already in use. ");
        }

        return exportFile;
    }
}
