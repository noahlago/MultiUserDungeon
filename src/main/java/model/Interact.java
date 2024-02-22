package model;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.ConcreteTile;
import model.Tiles.EmptyTile;
import model.Tiles.ExitTile;
import model.Tiles.ObstacleTile;
import model.Tiles.TrapTile;

public class Interact implements Visitor{

    private Room currentRoom;
    private Character player;

    public Interact(Room room, Character player){
        this.currentRoom = room;
        this.player = player;
    }

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
        //throw new UnsupportedOperationException("Unimplemented method 'visitEmptyTile'");
        
        System.out.println("MOVE HERE PLEASE");
        int row = eTile.getRow();
        int col = eTile.getCol();

        System.out.println(row);
        System.out.println(col);

        int[] loco = player.getLocation();
        ConcreteTile[][] tiles = currentRoom.getTiles();
        tiles[loco[1]][loco[0]] = new EmptyTile(loco[1],loco[0]);
        tiles[row][col] = new CharacterTile(player);
        currentRoom.updateTiles(tiles);

    }

    @Override
    public void visitExitTile(ExitTile eTile) {
        // TODO Character should leave the room 
        throw new UnsupportedOperationException("Unimplemented method 'visitExitTile'");
    }

    @Override
    public void visitObstacleTile(ObstacleTile oTile) {
        //display name of object blocking user's path
        String name = oTile.getName();
        System.out.println("A " + name + " blocks your path");

    }
    
}
