package model;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.EmptyTile;
import model.Tiles.ExitTile;
import model.Tiles.ObstacleTile;
import model.Tiles.TrapTile;

public class Interact implements Visitor{

    @Override
    public void visitCharacterTile(CharacterTile cTile) {
        // TODO Interaction between playable character and character on tile
        throw new UnsupportedOperationException("Unimplemented method 'visitCharacterTile'");
    }

    @Override
    public void visitChestTile(ChestTile cTile) {
        // TODO Interaction between character and chest with items
        throw new UnsupportedOperationException("Unimplemented method 'visitChestTile'");
    }

    @Override
    public void visitTrapTile(TrapTile tTile) {
        // TODO Interactoin between character and Trap 
        throw new UnsupportedOperationException("Unimplemented method 'visitTrapTile'");
    }

    @Override
    public void visitEmptyTile(EmptyTile eTile) {
        // TODO Playable character should move to the empty tile
        throw new UnsupportedOperationException("Unimplemented method 'visitEmptyTile'");
    }

    @Override
    public void visitExitTile(ExitTile eTile) {
        // TODO Character should leave the room 
        throw new UnsupportedOperationException("Unimplemented method 'visitExitTile'");
    }

    @Override
    public void visitObstacleTile(ObstacleTile oTile) {
        // TODO Obstacle cannot be moved by character
        throw new UnsupportedOperationException("Unimplemented method 'visitObstacleTile'");
    }
    
}
