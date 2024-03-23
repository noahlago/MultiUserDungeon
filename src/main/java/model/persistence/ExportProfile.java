package model.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    
    public void exportXML(String filename, String username) throws IOException{
        //File exportFile = makeFile(filename);
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
