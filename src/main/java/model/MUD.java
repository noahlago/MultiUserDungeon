package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Tiles.CharacterTile;
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
        return player; // make sure player has a toString
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
        numTurns+=1;
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



    public static void main(String[] args) {
        Pc play = new Pc(1,10,"mars",new Inventory(),100);
        Map map = new Map();
        map.setPlayer(play);
        MUD game = new MUD(map,"Save 1");
        game.printCurrentRoom();
    }
}
