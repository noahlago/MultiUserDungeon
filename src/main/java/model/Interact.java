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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitCharacterTile'");
    }

    @Override
    public void visitChestTile(ChestTile cTile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitChestTile'");
    }

    @Override
    public void visitTrapTile(TrapTile tTile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitTrapTile'");
    }

    @Override
    public void visitEmptyTile(EmptyTile eTile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitEmptyTile'");
    }

    @Override
    public void visitExitTile(ExitTile eTile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitExitTile'");
    }

    @Override
    public void visitObstacleTile(ObstacleTile oTile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visitObstacleTile'");
    }
    
}
