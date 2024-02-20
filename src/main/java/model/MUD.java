package model;


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
    private int numTurns;

    /**
     * Instance of a MUD game
     * @param map -- map to use
     * @param name -- name of user
     */
    public MUD(Map map, String name){
        this.map = map;
        this.name = name;
        this.player = new Pc(100, 10, name, new Inventory(), 0);
        this.numTurns = 0;
    }

    /**
     * @return map toString
     */
    public Map getMap(){
        return map;
    }

    /**
     * @return name of user
     */
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
    public int getTurns(){
        return numTurns;
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
}
