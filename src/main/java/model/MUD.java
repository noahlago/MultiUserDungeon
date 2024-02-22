package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Main implementation of MUD game
 * Defines a map, pc's name, and methods for the game to run
 * 
 * Implementation not finished yet
 * 
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
public class MUD {
    @JsonProperty("map") private Map map;
    @JsonProperty("name") private String name;
    @JsonProperty("player") private Character player;
    @JsonProperty("currentRoom") private Room currentRoom;
    @JsonProperty("numTurns") private int numTurns;
    @JsonProperty("cycle") private Cycle cycle;

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
        this.cycle = new Day();
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
        return player;
    }

    /**
     * @return number of turns made so far
     */
    @JsonProperty("numTurns")
    public int getNumTurns(){
        return numTurns;
    }

    /**
     * Prints description of map
     */
    public void printMap(){
        System.out.println(this.map);
    }
    
    /**
     * Prints description of current room
     */
    private void printCurrentRoom(){
        System.out.println(this.currentRoom);
    }

    /**
     * Increases number of turns
     */
    public void uptickTurns(){
        numTurns += 1;
    }

    /**
     * method used in PTUI to check if player's health is <= 0
     * @return player's current health
     */
    public double getHealth(){
        return player.getHealth();
    }

    /**
     * Used to modify enemy stats when switching cycle state
     * @return list of NPCs in the current room
     */
    public Npc[] getNpcs(){
        return getCurrentRoom().getNpcs();
    }

    /**
     * @return current state of cycle
     */
    public Cycle getCycle(){
        return cycle;
    }

    /**
     * Checks the turn count and switches state of the cycle if interval of 10
     * Prints out current state
     */
    public void checkCycle(){
        if(numTurns % 10 == 0){
            cycle.switchState(cycle);
            cycle.modifyDiurnalEnemies(getNpcs());
            cycle.modifyNocturnalEnemies(getNpcs());

            System.out.println("It switched to " + cycle.toString());
        }
        else{
            System.out.println("It is currently " + cycle.toString());
        }
    }

    /**
     * Checks if the game is over
     * Tells user if they won or lost if game over
     * @return true if character health is <= 0 or character on exit tile of goal room, false otherwise
     */
    public boolean gameOver(){
        if(getHealth() <= 0){
            System.out.println("You lost!");
            return true;
        }
        // else if (/*character on exit tile in goal room*/){
        //     System.out.println("You won!");
        // }
        return false;
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
     * move to next room (if tile is exit)
     * use items 
     * equip weapons or armor
     * win game if tile is exit and room is goal
     */

    public static void main(String[] args) {
        Pc play = new Pc(1,10,"mars",new Inventory(),100);
        Map map = new Map();
        map.setPlayer(play);
        MUD game = new MUD(map,"Save 1");
        game.printCurrentRoom();
    }
}
