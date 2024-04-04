package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import model.Tiles.CharacterTile;
import model.Tiles.ConcreteTile;
import model.Tiles.TrapTile;

/**
 * Main implementation of MUD game
 * Defines a map, pc's name, and methods for the game to run
 * 
 * Implementation not finished yet
 * 
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MUD {

    private Interact action;
    @JsonProperty("map")
    public Map map;
    @JsonProperty("name")
    public String name;
    @JsonProperty("player")
    public Pc player;
    @JsonProperty("currentRoom")
    public Room currentRoom;
    @JsonProperty("numTurns")
    public int numTurns;
    @JsonProperty("cycle")
    public Cycle cycle;
    @JsonProperty("gameOver")
    public boolean gameOver;
    @JsonProperty("observer")
    public mudObserver observer;

    /**
     * Instance of a MUD game
     * 
     * @param map  -- map to use
     * @param name -- name of users
     */
    @JsonCreator
    public MUD(@JsonProperty("map") Map map, @JsonProperty("name") String name) {
        this.map = map;
        this.name = name;
        this.player = new Pc(100, 10, name, new Inventory(), 0);
        this.numTurns = 0;
        currentRoom = this.map.getRooms().get(0);
        this.action = new Interact(this, this.currentRoom, this.player);

        this.cycle = new Day();
        this.gameOver = false;
    }
    public void setOnUpdate(mudObserver observer) {
        this.observer = observer;
    }
    private void mudUpdated() {
        if(observer != null) {
            observer.mudUpdated(this);
        }
    }
    private void textUpdated(String string){
        if(observer != null){
            observer.textUpdated(string);
        }
    }

    /**
     * @return map toString
     */
    public Map getMap() {
        return map;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * moves the game to the next room in the sequence
     */
    public void nextRoom(){
        this.currentRoom = this.map.getRooms().get(1);
        this.action = new Interact(this, this.currentRoom, this.player);
    }

    public Character getPlayer() {
        return this.player;
    }

    /**
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * @return character toString
     */
    public Character getPlayerStats() {
        return player;
    }

    /**
     * @return number of turns made so far
     */
    public int getNumTurns() {
        return numTurns;
    }

    /**
     * Prints description of map
     */
    public void printMap() {
        System.out.println(this.map);
    }

    /**
     * Prints description of current room
     */
    public void printCurrentRoom() {
        System.out.println(this.currentRoom);
    }

    /**
     * Increases number of turns
     */
    public void uptickTurns() {
        numTurns += 1;
    }

    /**
     * method used in PTUI to check if player's health is <= 0
     * 
     * @return player's current health
     */
    public double getHealth() {
        return player.getHealth();
    }

    /**
     * NPC takes damage based on player's attack stat
     * 
     * @param npc the NPC to attack
     */
    public void attackNpc(Npc npc) {
        npc.takeDamage(player.getAttack());
    }

    /**
     * Player takes damage
     * 
     * @param amount the amount of damage to take
     */
    public void takeDamage(double amount) {
        textUpdated(player.takeDamage(amount));
    }

    /**
     * Player uses item
     * 
     * @param item item to use
     * @return true if successfully used, false otherwise
     */
    public boolean useItem(Item item) {
        return player.useItem(item);
    }

    /**
     * Equips armor to player
     * 
     * @param armor armor to equip
     * @return true if successfully equipped, false otherwise
     */
    public boolean equipArmor(Item armor) {
        return player.equipArmor(armor);
    }

    /**
     * Equips weapon to player
     * 
     * @param weapon weapon to equip
     * @return true if successfully equipped, false otherwise
     */
    public boolean equipWeapon(Item weapon) {
        return player.equipWeapon(weapon);
    }

    /**
     * Used to modify enemy stats when switching cycle state
     * 
     * @return list of NPCs in the current room
     */
    public Npc[] getNpcs() {
        return getCurrentRoom().getNpcs();
    }

    /**
     * @return current state of cycle
     */
    public Cycle getCycle() {
        return cycle;
    }

    /**
     * Checks the turn count and switches state of the cycle if interval of 10
     * Alerts user when cycle switches from day to night / vice versa
     */
    public void checkCycle() {
        if (numTurns % 10 == 0) {
            cycle.switchState(cycle);
            cycle.modifyDiurnalEnemies(getNpcs());
            cycle.modifyNocturnalEnemies(getNpcs());

            System.out.println("It switched to " + cycle.toString());
            textUpdated("It switched to " + cycle.toString());
        }
    }

    /**
     * Checks if the game is over
     * Tells user if they won or lost if game over
     * 
     * @return true if character health is <= 0 or character on exit tile of goal
     *         room, false otherwise
     */
    public boolean getGameOver() {
        if (getHealth() <= 0) {
            gameOver = true;
            System.out.println("You lost!");
            textUpdated("You Lost!");
        }
        return gameOver;
    }

    public void winGame(){
        gameOver = true;
        System.out.println("You won!");
        textUpdated("You Won!");
    }

    public boolean roomIsGoal(){
        return getCurrentRoom().getIsGoal();
    }

    public ConcreteTile[] getCloseTiles(){
        ConcreteTile[] closeTiles = new ConcreteTile[8];
        int[] playerLocation = player.getLocation();
        int xCoord = playerLocation[0];
        int yCoord = playerLocation[1];
        
        int width = currentRoom.getWidth();
        int height = currentRoom.getHeight();

        
        //making sure all adjacent tiles are in bounds
        if((0< xCoord && xCoord < width-1) && (0 < yCoord && yCoord < height-1)){
            closeTiles[0] =  currentRoom.getTile(xCoord+1, yCoord);     //DOWN
            closeTiles[1] =  currentRoom.getTile(xCoord, yCoord+1);     //RIGHT
            closeTiles[2] =  currentRoom.getTile(xCoord-1, yCoord);     //UP
            closeTiles[3] =  currentRoom.getTile(xCoord, yCoord-1);     //LEFT
            closeTiles[4] = currentRoom.getTile(xCoord-1, yCoord-1);    //TOP LEFT
            closeTiles[5] = currentRoom.getTile(xCoord-1, yCoord+1);    //TOP RIGHT
            closeTiles[6] = currentRoom.getTile(xCoord+1, yCoord+1);    //BOTTOM RIGHT
            closeTiles[7] = currentRoom.getTile(xCoord+1, yCoord-1);    //BOTTOM LEFT

        }
        else{
            //Row = 0, can move down
            if(xCoord == 0){
                
                closeTiles[0] =  currentRoom.getTile(xCoord+1, yCoord);     //DOWN
                
                if(yCoord > 0){
                    closeTiles[7] = currentRoom.getTile(xCoord+1, yCoord-1);    //BOTTOM LEFT
                }
                if(yCoord < height-1){ 
                    closeTiles[6] = currentRoom.getTile(xCoord+1, yCoord+1);    //BOTTOM RIGHT
                }
            }

            //row = max, can move up
            else if(xCoord == width-1){
                closeTiles[2] =  currentRoom.getTile(xCoord-1, yCoord);     //UP

                if(yCoord > 0){
                    closeTiles[4] = currentRoom.getTile(xCoord-1, yCoord-1);    //TOP LEFT
                }
                if(yCoord < height-1){ 
                    closeTiles[5] = currentRoom.getTile(xCoord-1, yCoord+1);    //TOP RIGHT
                }
            }

            //otherwise, move left and right
            else{
                closeTiles[2] =  currentRoom.getTile(xCoord-1, yCoord);     //UP
                closeTiles[0] =  currentRoom.getTile(xCoord+1, yCoord);     //DOWN
            }



            //Col = 0, can only move right
            if(yCoord == 0){  
                closeTiles[1] =  currentRoom.getTile(xCoord, yCoord+1);     //RIGHT

                if(xCoord > 0){
                    closeTiles[5] = currentRoom.getTile(xCoord-1, yCoord+1);    //TOP RIGHT
                }
                if(xCoord < width-1){ 
                    closeTiles[6] = currentRoom.getTile(xCoord+1, yCoord+1);    //BOTTOM RIGHT
                }
            }

            //col = max, can only move left
            else if(yCoord == height-1){   
                closeTiles[3] =  currentRoom.getTile(xCoord, yCoord-1);     //LEFT

                if(xCoord > 0){
                    closeTiles[4] = currentRoom.getTile(xCoord-1, yCoord-1);    //TOP LEFT
                }
                if(xCoord < width-1){ 
                    closeTiles[7] = currentRoom.getTile(xCoord+1, yCoord-1);    //BOTTOM LEFT
                }
            }

            //otherwise, move right and left
            else{
                closeTiles[1] =  currentRoom.getTile(xCoord, yCoord+1);     //RIGHT
                closeTiles[3] =  currentRoom.getTile(xCoord, yCoord-1);     //LEFT
            }    
        }

        for(ConcreteTile tile: closeTiles){
            if(tile instanceof TrapTile){
                //random chance the user discovers the trap
                Random rand = new Random();
                int chance = rand.nextInt(2);

                //if an undiscovered trap
                if(((TrapTile) tile).getDiscovered() == false){
                    
                    //50/50 chance to discover
                    if(chance == 1){
                        ((TrapTile) tile).discover();
                        System.out.println("You discovered a trap!");
                        textUpdated("You Discovered A Trap!");
                    }
                }
                
            }
        }
        return closeTiles;
    }

    public String closeTilesString(){
        ConcreteTile[] close = getCloseTiles();
        String tiles = "";
        if(close[4] != null){
            tiles += close[4];
        }
        if(close[2] != null){
            tiles += close[2];
        }
        if(close[5] != null){
            tiles += close[5];
        }
        tiles += "\n";
        if(close[3] != null){
            tiles += close[3];
        }
        tiles += "[ i ]";
        if(close[1] != null){
            tiles += close[1];
        }
        tiles += "\n";
        if(close[7] != null){
            tiles += close[7];
        }
        if(close[0] != null){
            tiles += close[0];
        }
        if(close[6] != null){
            tiles += close[6];
        }
        tiles += "\n";
        return tiles;
    }

    public ArrayList<CharacterTile> getCharacterTiles() {
        ConcreteTile[] tiles = getCloseTiles();
        ArrayList<CharacterTile> characters = new ArrayList<>();
        for (ConcreteTile tile : tiles) {
            if (tile instanceof CharacterTile) {
                characters.add((CharacterTile)tile);
            }
        }
        return characters;
    }

    public String inventoryString(){
        return this.player.inventoryString();
    }

    @Override
    public String toString() {
        return this.currentRoom.toString();
    }

    public void movePlayer(int x, int y) {
        int[] playerLocation = player.getLocation();
        int xCoord = playerLocation[0] + x;
        int yCoord = playerLocation[1] + y;

        int width = currentRoom.getWidth();
        int height = currentRoom.getHeight();

        // making sure the tile is in bounds
        if ((0 <= xCoord && xCoord < width) && (0 <= yCoord && yCoord < height)) {
            
            ArrayList<CharacterTile> charTiles = getCharacterTiles();
            if(charTiles != null){
                for(CharacterTile tile : charTiles){
                    Npc npc = (Npc)tile.getCharacter();
                    player.takeDamage(npc.getAttack());
                }
            }
            uptickTurns();
            currentRoom.getTile(xCoord, yCoord).accept(action);
            uptickTurns();
        }
        // player tries to move outside of playable area
        else {
            System.out.println("Cannot move out of bounds");
            textUpdated("You cannot move out of bounds!");
        }
        mudUpdated();
    }

    public void renderRooms(){
        this.map.renderRooms();
        this.currentRoom.specializeTiles();
        this.currentRoom.setPlayer(player);
        this.action = new Interact(this, currentRoom, player);
    }

    public int getPlayerGold(){
        return this.player.getGoldAmount();
    }

    public Inventory getInventory(){
        return this.player.getInventory();
    }

    public static void main(String[] args) {
        Pc play = new Pc(1, 10, "mars", new Inventory(), 100);
        Map map = new Map();
        map.setPlayer(play);
        MUD game = new MUD(map, "Save 1");
        game.printCurrentRoom();
        
        // game.movePlayer(0, 1);
        // game.printCurrentRoom();
        // game.movePlayer(1, 0);
        // game.printCurrentRoom();

        // game.nextRoom();
        // game.printCurrentRoom();
        
    }

}
