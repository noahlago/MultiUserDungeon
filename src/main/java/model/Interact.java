package model;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.ConcreteTile;
import model.Tiles.EmptyTile;
import model.Tiles.ExitTile;
import model.Tiles.ObstacleTile;
import model.Tiles.TrapTile;
import view.PTUI;

import java.util.ArrayList;

import model.Character;

@SuppressWarnings("unused")
public class Interact implements Visitor{

    private MUD game;
    private Room currentRoom;
    private Pc player;

    public Interact(MUD game, Room room, Pc player){
        this.game = game;
        this.currentRoom = room;
        this.player = player;
    }

    @Override
    public void visitCharacterTile(CharacterTile cTile) {
        //Player hits NPC
        Character npc = cTile.getCharacter();
        npc.takeDamage(player.getAttack());
        System.out.println("You hit " + npc.getName() + "\t Health: " + npc.getHealth());

        //if the npc health is reduced to 0 or below they die
        if(npc.getHealth() <=0){

            System.out.println("You killed " + npc.getName());

            int row = cTile.getRow();
            int col = cTile.getCol();

            //get copy of room
            ConcreteTile[][] tiles = currentRoom.getTiles();

            //set the tile to an empty one
            tiles[row][col] = new EmptyTile(row, col);

            //update the tiles array to reflect the changes
            currentRoom.updateTiles(tiles);
        }
        
    }

    @Override
    public void visitChestTile(ChestTile cTile) {
        ArrayList<Item> items = cTile.getChest().getItems();
        
        int x = 0;
        for(Item i : items){
            System.out.println((x+1) + ": " + i.getName());
            x++;
        }

        int itemNum = PTUI.chooseItem();
        if(itemNum < 0 || itemNum > items.size()){
            System.out.println("Invalid item #. Try again. ");
        }else{
            Item acquired = items.get(itemNum - 1);
            cTile.getChest().remove(acquired);
            player.addItem(acquired);
        }
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

        //get the players location
        int[] loco = player.getLocation();

        //copy of the current room
        ConcreteTile[][] tiles = currentRoom.getTiles();
        //set the players old location to an empty tile
        tiles[loco[0]][loco[1]] = new EmptyTile(loco[0],loco[1]);
        
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
