package model.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.MUD;

/**
 * This class allows for save files of attempts at the Multi-User Dungeon to be saved to a .json file. 
 * The games are stored in a HashMap, for easier access while the program is running, which is updated whenever a change is made. 
 * The file containing the saved games is also updated upon any changes. 
 * @author Noah Lago (ndl3389@rit.edu)
 */
public class GameFileDAO implements GameDAO{
    /**The file where game info is stored. */
    private HashMap<String,MUD> savedGames;
    /**Dynamically updated HashMap of all saved games frome the file.  */
    private String filename; 
    /**Writes to and reads from the given filename. */
    private ObjectMapper objectMapper;

    /**
     * Instantiates this class when the Multi-User Dungeon is run.
     * @param objectMapper used to read to and write from the saves.json file. 
     */
    public GameFileDAO(){
        this.filename = "data/saves.json";
        this.savedGames = new HashMap<>();
        this.objectMapper = new ObjectMapper();

        try{
            load(); //Loads all previously saved game files.
        }catch(IOException ioe){
            System.out.println("Save file not loaded.");
        }
    }

    /**
     * Rewrites all save files to the designated .json file
     * @throws IOException if the file is not found
     */
    public void save() throws IOException{
        MUD[] gameArray = getGameArray();
        objectMapper.writeValue(new File(filename), gameArray);
    }

    /**
     * Loads all previously saved game files from the designated .json file
     * @throws IOException if the file is not found
     */
    private void load() throws IOException{
        File saveFile = new File(filename);
        if(saveFile.length() == 0){
            System.out.println("No previously saved game files.");
        }else{
            MUD[] gameArray = objectMapper.readValue(new File(filename), MUD[].class);
            savedGames = new HashMap<>();
            for(MUD game : gameArray){
                savedGames.put(game.getName(), game);
            }
        }
    }

    /**
     * For internal use only
     * @return an array containing all of the games saved to the file previously
     */
    private MUD[] getGameArray(){
        MUD[] gameArray = new MUD[this.savedGames.size()];
        int index = 0;
        for(MUD save : this.savedGames.values()){
            gameArray[index] = save;
            index++;
        }
        return gameArray;
    }

    /**
     * Used to access all games, mainly for testing
     * @return the HashMap of all games related to their String names
     * @throws IOException if the file is not found
     */
    public HashMap<String,MUD> getGames() throws IOException {
        load();
        return this.savedGames;
    }

    /**
     * Updates the game saved under the same name as the given name. 
     * To be used when a user makes progress on a previously saved game. 
     * @throws IOException if the file is not found
     */
    @Override
    public boolean updateSaveGame(MUD saveGame) throws IOException {
        if(this.savedGames.containsKey(saveGame.getName())){
            this.savedGames.put(saveGame.getName(),saveGame);
            save();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Adds a new saved game to the HashMap of saved games, and rewrites the HashMap to the file with stored game files. 
     * @throws IOException if the file is not found. 
     */
    @Override
    public boolean newSaveGame(MUD saveGame) throws IOException {
        if(savedGames.containsKey(saveGame.getName())){
            return false;
        }else{
            this.savedGames.put(saveGame.getName(), saveGame);
            save();
            return true;
        }
    }

    /**
     * Removes the designated game save from the file of saved games, and updates the HashMap currently in use. 
     * @throws IOException if the file is not found.
     */
    @Override
    public boolean deleteSaveGame(String saveGame) throws IOException {
        if(!this.savedGames.containsKey(saveGame)){
            return false;
        }else{
            this.savedGames.remove(saveGame);
            save();
            return true;
        }
    }

    /**
     * Returns a collection of all of the games saved to the save file. 
     * Allows the user to choose which game they wish to load when they play the game. 
     * @throws IOException if the file is not found. 
     */
    @Override
    public Collection<MUD> getAllGames() throws IOException {
        load();
        Collection<MUD> allGames = this.savedGames.values();
        return allGames;
    }

}
