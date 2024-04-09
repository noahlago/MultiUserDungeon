package model.persistence;

import java.io.IOException;
import java.util.Collection;

import model.MUD;

/**
 * This interface defines behavior to handle the management of several persistent instances of the MUD game. 
 * The games instances are stored in a file, and are read to an instance of a GameDAO implementation, where they are stored in a dynamic Collection. 
 * At any point when the dynamic Collection of games is updated, the save file is updated to reflect the same changes. 
 * @author Noah Lago ndl3389@rit.edu
 */
public interface GameDAO {
    public Collection<MUD> getAllGames() throws IOException;

    public boolean updateSaveGame(MUD saveGame) throws IOException;

    public boolean newSaveGame(MUD saveGame) throws IOException;

    public boolean deleteSaveGame(String saveGame) throws IOException;
}
