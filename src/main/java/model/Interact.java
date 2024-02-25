package model;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.ConcreteTile;
import model.Tiles.EmptyTile;
import model.Tiles.ExitTile;
import model.Tiles.ObstacleTile;
import model.Tiles.TrapTile;

import java.util.ArrayList;

import model.Character;

@SuppressWarnings("unused")
public class Interact implements Visitor{

    private MUD game;
    private Room currentRoom;
    private Character player;

    public Interact(MUD game, Room room, Character player){
        this.game = game;
        this.currentRoom = room;
        this.player = player;
    }

    @Override
    public void visitCharacterTile(CharacterTile cTile) {
        // TODO Interaction between playable character and character on tile, need to know turn for who takes damage??
        
        //PLayer hits NPC
        Character npc = cTile.getCharacter();
        npc.takeDamage(player.getAttack());

        //npc dies and tile becomes empty
    }

    @Override
    public void visitChestTile(ChestTile cTile) {
        // TODO Interaction between character and chest with items
        //throw new UnsupportedOperationException("Unimplemented method 'visitChestTile'");
    
        ArrayList<Item> items = cTile.getChest().getItems();
        
        int x = 0;
        for(Item i : items){
            System.out.println((x+1) + ": " + i.getName());
            x++;
        }

        //Prompt user for which # item they want

        //TODO Call PTUI to get user input? then we know which items the user wants
        
    }

    @Override
    public void visitTrapTile(TrapTile tTile) {
        // TODO Interaction between character and Trap, trap needs to be disarmed or else
        
        //if the trap is armed the user takes damage
        if(tTile.isArmed()){
            if(tTile.getName().equals("Spike Trap")){
                player.takeDamage(25);
            }
            else{
                player.takeDamage(15);
            }
        }
    }

    @Override
    public void visitEmptyTile(EmptyTile eTile) {
        //get the row and col of the tile being interacted with
        int row = eTile.getRow();
        int col = eTile.getCol();
        System.out.println("TILE: " + row + " " + col);

        //get the players location
        int[] loco = player.getLocation();

        //copy of the current room
        ConcreteTile[][] tiles = currentRoom.getTiles();
        //set the players old location to an empty tile
        tiles[loco[1]][loco[0]] = new EmptyTile(loco[1],loco[0]);
        
        //set the interacted with tiles location to the players new location
        tiles[row][col] = new CharacterTile(player);

        //update players location
        player.updateLocation(row, col);

        //update the tiles array to reflect the changes
        currentRoom.updateTiles(tiles);

    }

    @Override
    public void visitExitTile(ExitTile eTile) {
        //moves map to the next room in sequence
        game.nextRoom();
    }

    @Override
    public void visitObstacleTile(ObstacleTile oTile) {
        //display name of object blocking user's path
        String name = oTile.getName();
        System.out.println("A " + name + " blocks your path");

    }
    
}
