package model.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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

    public void importXML(String filename){
        //TODO
    }

    public void importJSON(String filename) throws StreamReadException, DatabindException, IOException{
        File importFile = new File(filename);
        ObjectMapper objectMapper = new ObjectMapper(); 
        User user = objectMapper.readValue(importFile, User.class);
        profileManager.newUser(user);
    }
}
