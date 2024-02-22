package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Tiles.Tile;

/**
 * Main implementation of MUD game
 * Defines a map, pc's name, and methods for the game to run
 * 
 * Implementation not finished yet
 * 
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
public class MUD {
    private Map map;
    private String name;
    private Character player;
    private Room currentRoom;
    private int numTurns;
    private Interact action;

    /**
     * Instance of a MUD game
     * @param map -- map to use
     * @param name -- name of user
     */
    public MUD(@JsonProperty("map") Map map, @JsonProperty("name") String name){
        this.map = map;
        this.name = name;
        this.player = new Pc(100, 10, name, new Inventory(), 0);
        this.numTurns = 0;
        currentRoom = this.map.getRooms().get(0);
        this.action = new Interact(this.currentRoom, this.player);
    }

    /**
     * @return map toString
     */
    @JsonProperty("map")
    public Map getMap(){
        return map;
    }

    @JsonProperty("currentRoom")
    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    @JsonProperty("player")
    public Character getPlayer(){
        return this.player;
    }
    /**
     * @return name of user
     */
    @JsonProperty("name")
    public String getName(){
        return name;
    }

    /**
     * @return character toString
     */
    public Character getPlayerStats(){
        return player; // make sure player has a toString
    }

    /**
     * @return number of turns made so far
     */
    @JsonProperty("turns")
    public int getTurns(){
        return numTurns;
    }

    private void printMap(){
        System.out.println(this.map);
    }
    
    private void printCurrentRoom(){
        System.out.println(this.currentRoom);
    }

    public void uptickTurns(){
        numTurns+=1;
    }
    @Override
    public String toString(){
        return this.currentRoom.toString();
    }


    /*
     * to do:
     * 
     * add visitor functionality (move to another tile)
     * attack npcs
     * npcs attack pcs after each round
     * day / night cycle
     * move to next room (if tile is exit)
     * use items 
     * equip weapons or armor
     * win game if tile is exit and room is goal
     * lose game if pc health <= 0
     */

     public void movePlayer(int x, int y){
        int[] playerLocation = player.getLocation();
        int xCoord = playerLocation[0] + x;
        int yCoord = playerLocation[1] + y;

        int width = currentRoom.getWidth();
        int height = currentRoom.getHeight();

        //making sure the tile is in bounds
        if((0<= xCoord && xCoord < width) && (0<= yCoord && yCoord < height)){
            Tile nextTile = currentRoom.getTile(xCoord, yCoord);
            System.out.println(nextTile);

            nextTile.accept(action);

        }

        //player tries to move outside of playable area
        else{
            System.out.println("Cannot move out of bounds");
        }
     }



    public static void main(String[] args) {
        Pc play = new Pc(1,10,"mars",new Inventory(),100);
        Map map = new Map();
        map.setPlayer(play);
        MUD game = new MUD(map,"Save 1");
        game.printCurrentRoom();
        game.movePlayer(0, 1);
        
        game.printCurrentRoom();
    }
}
