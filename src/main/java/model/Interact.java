package model;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.ConcreteTile;
import model.Tiles.EmptyTile;
import model.Tiles.EntranceTile;
import model.Tiles.ExitTile;
import model.Tiles.MerchantTile;
import model.Tiles.ObstacleTile;
import model.Tiles.TrapTile;
import view.PTUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
       //Random variable for if trap is disarmed
        Random rand = new Random();

        //An already discovered trap is interacted with
        if(tTile.isArmed() && tTile.getDiscovered() == true){
            System.out.println("You try to disarm the trap");

            int chance = rand.nextInt(2);
            if(chance == 1){
                //user has disarmed the trap
                System.out.println("You disarmed the trap!");

                tTile.disarm();
            }

            else{
                //user takes damage
                System.out.println("You took damage from the trap!");

                player.takeDamage(tTile.getDamage());
            }

        }

        //if the trap is armed and not found the user takes damage
        if(tTile.isArmed() && tTile.getDiscovered() == false){
            //user takes damage
            player.takeDamage(tTile.getDamage());

            //the trap is discovered and unarmed
            tTile.discover();
            tTile.disarm();

            //print the damage the user took
            System.out.println("You took damage from a " + tTile.getName());
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
        tiles[row][col] = new CharacterTile(player, row ,col);

        //update players location
        player.updateLocation(row, col);

        //update the tiles array to reflect the changes
        currentRoom.updateTiles(tiles);

    }

    @Override
    public void visitExitTile(ExitTile eTile) {
        //moves map to the next room in sequence
        if(currentRoom.getIsGoal()){
            game.winGame();
        }
        else{
            game.nextRoom();
            player.updateLocation(0, 0);
            System.out.println("You've entered the next room!");
        }
        

    }

    @Override
    public void visitObstacleTile(ObstacleTile oTile) {
        //display name of object blocking user's path
        String name = oTile.getName();
        System.out.println("A " + name + " blocks your path");

    }

    @Override
    public void visitMerchantTile(MerchantTile mTile) {
        List<Item> goods = mTile.getGoods();

        if(currentRoom.isSafe()){
            System.out.println("The room is safe and you talk with the merchant");
            int num = 0;
            for(Item i: goods){
                System.out.println(num + ": " + i.getName()+ ", $" + i.getGoldValue());
                num++;
            }

            char selection = PTUI.visitMerchant();

            switch(selection){
                case 's':
                    // sell item to merchant
                    Inventory inv = game.getInventory();
                    ArrayList<Item> items = inv.items();
                    
                    int itemNum = 1;
                    for(Item item : items){
                        System.out.println(itemNum + ": " + item + ", $" + item.getGoldValue() / 2);
                        itemNum++;
                    }

                    System.out.println("Select item number: ");
                    itemNum = PTUI.chooseItem() -1;

                    if(itemNum < 0 || itemNum > items.size()){
                        System.out.println("Invalid item #. Try again.");
                    }else{
                        Item item = items.get(itemNum);
                        game.sellItemToMerchant(item);
                        System.out.println("You sold " + item.getName() + " for $" + item.getGoldValue() /2);
                    }

                case 'b':
                    // buy item from merchant
                    System.out.println("Select item number: ");
                    itemNum = PTUI.chooseItem() -1;

                    if(itemNum < 0 || itemNum > goods.size()){
                        System.out.println("Invalid item #. Try again.");
                        break;
                    }

                    Item item = mTile.getGood(itemNum);

                    if(player.getGoldAmount() >= item.getGoldValue()){
                        player.addItem(item);
                        mTile.removeItem(itemNum);
                        player.decreaseGold(item.getGoldValue());
                        System.out.println("You bought " + item.getName() + " for " + item.getGoldValue() + " gold.");
                    }
                    else{
                        System.out.println("You do not have enough gold for that");
                    }

                case 'e':
                    // exit
                    break;
            }
        }
        else{
            System.out.println("The room is not safe! kill all the monsters if you want to talk!");
        }
        

    }

    @Override
    public void visitEntranceTile(EntranceTile eTile) {
        if(currentRoom.getIsStart()){
            System.out.println("You cannot exit the dungeon");
        }
        else{
            game.prevRoom();
        }
    }

    @Override
    public void visitShrineTile() {
        // TODO Save user position so they respawn at the shrine
        throw new UnsupportedOperationException("Unimplemented method 'visitShrineTile'");
    }
    
}
