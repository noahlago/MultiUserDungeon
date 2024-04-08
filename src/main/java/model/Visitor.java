package model;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.EmptyTile;
import model.Tiles.EntranceTile;
import model.Tiles.ExitTile;
import model.Tiles.MerchantTile;
import model.Tiles.ObstacleTile;
import model.Tiles.ShrineTile;
import model.Tiles.TrapTile;

public interface Visitor {
    
    //Interactions with character on tile
    public void visitCharacterTile(CharacterTile cTile);
    
    //interactions for charaters and chests on the map
    public void visitChestTile(ChestTile cTile);

    //interactions with a trap
    public void visitTrapTile(TrapTile tTile);

    //Interactions with an empty tile (move to it)
    public void visitEmptyTile(EmptyTile eTile);

    //interactions with an exit tile (leave room)
    public void visitExitTile(ExitTile eTile);

    //Interactions with an obstacle tile (can't move)
    public void visitObstacleTile(ObstacleTile oTile);

    //Interactions with a merchant
    public void visitMerchantTile(MerchantTile mTile);

    //interactions with an entrance (brings you to the previous exit)
    public void visitEntranceTile(EntranceTile eTile);

    //interactions with a shrine for respawning
    public void visitShrineTile();

    //Interactions with an shrine tile
    public void visitShrineTile(ShrineTile sTile);
    
}
