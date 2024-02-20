package model.persistence;

import java.io.IOException;
import java.util.Collection;

import model.MUD;

public interface GameDAO {
    public Collection<MUD> getAllGames() throws IOException;

    public boolean updateSaveGame(MUD saveGame) throws IOException;

    public boolean newSaveGame(MUD saveGame) throws IOException;

    public boolean deleteSaveGame(MUD saveGame) throws IOException;
}
