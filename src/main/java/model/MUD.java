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
    private Map map;
    private String name;
    private Character player;
    private Room currentRoom;
    private int numTurns;
    private Cycle cycle;

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

    /**
     * method used in PTUI to check if player's health is <= 0
     * @return player's current health
     */
    public double getHealth(){
        return player.getHealth();
    }

    public List<Npc> getNpcs(){
        Tile[][] tiles = getCurrentRoom().getTiles();
        List<Npc> npcs = new ArrayList<>();
        for(int i = 0; i <= getCurrentRoom().getWidth(); i++){
            for(int x = 0; x <= getCurrentRoom().getHeight(); x++){
                // if(){
                //     // npcs.add();
                // }
            }
        }

        return npcs;
    }

    public void checkCycle(){
        if(numTurns % 10 == 0){
            cycle.switchState(cycle);
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
