package model.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.MUD;

public class GameFileDAO implements GameDAO{
    private HashMap<String,MUD> savedGames;
    private String filename; 
    private ObjectMapper objectMapper;

    public GameFileDAO(ObjectMapper objectMapper){
        this.filename = "saves.json";
        this.savedGames = new HashMap<>();
        this.objectMapper = new ObjectMapper();

        try{
            load();
        }catch(IOException ioe){
            System.out.println("Save file not found.");
        }
    }

    public void save() throws IOException{
        MUD[] gameArray = getGameArray();
        objectMapper.writeValue(new File(filename), gameArray);
    }

    private void load() throws IOException{
        MUD[] gameArray = objectMapper.readValue(new File(filename), MUD[].class);
        savedGames = new HashMap<>();
        for(MUD game : gameArray){
            savedGames.put(game.getName(), game);
        }
    }

    private MUD[] getGameArray(){
        MUD[] gameArray = new MUD[this.savedGames.size()];
        int index = 0;
        for(MUD save : this.savedGames.values()){
            gameArray[index] = save;
            index++;
        }
        return gameArray;
    }

    public HashMap<String,MUD> getGames() throws IOException {
        load();
        return this.savedGames;
    }

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

    @Override
    public boolean deleteSaveGame(MUD saveGame) throws IOException {
        if(!this.savedGames.containsKey(saveGame.getName())){
            return false;
        }else{
            this.savedGames.remove(saveGame.getName());
            save();
            return true;
        }
    }

    @Override
    public Collection<MUD> getAllGames() throws IOException {
        load();
        Collection<MUD> allGames = this.savedGames.values();
        return allGames;
    }

}
