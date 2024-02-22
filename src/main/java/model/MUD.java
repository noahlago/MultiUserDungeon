package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JsonProperty("map") public Map map;
    @JsonProperty("name") public String name;
    @JsonProperty("player") public Pc player;
    @JsonProperty("currentRoom") public Room currentRoom;
    @JsonProperty("numTurns") public int numTurns;
    @JsonProperty("cycle") public Cycle cycle;

    /**
     * Instance of a MUD game
     * @param map -- map to use
     * @param name -- name of users
     */
    @JsonCreator
    public MUD(@JsonProperty("map") Map map, @JsonProperty("name")String name){
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
    public Map getMap(){
        return map;
    }

    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    public Character getPlayer(){
        return this.player;
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
        return player;
    }

    /**
     * @return number of turns made so far
     */
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
    public void printCurrentRoom(){
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
     * NPC takes damage based on player's attack stat
     * @param npc the NPC to attack
     */
    public void attackNpc(Npc npc){
        System.out.println("You attacked " + npc + " for " + player.getAttack() + " damage\n" + npc.getName() + "'s health is now " + getHealth());
        npc.takeDamage(player.getAttack());
    }

    /**
     * Player takes damage
     * @param amount the amount of damage to take
     */
    public void takeDamage(double amount){
        System.out.println("You took " + amount + " damage\n Your health is now " + getHealth());
        player.takeDamage(amount);
    }

    /**
     * Player uses item
     * @param item item to use
     * @return true if successfully used, false otherwise
     */
    public boolean useItem(Item item){
        boolean result = player.useItem(item);
        if(result == true){
            System.out.println("You gained " + item.healthPoints + " health\nYour health is now " + getHealth());
            return true;
        }
        System.out.println("Could not use " + item.getName());
        return false;
    }

    /**
     * Equips armor to player
     * @param armor armor to equip
     * @return true if successfully equipped, false otherwise
     */
    public boolean equipArmor(Item armor){
        boolean result = player.equipArmor(armor);
        if(result == true){
            System.out.println("You equipped " + armor.getName() + " and gained " + armor.getDefensePercent() + "% health");
            return true;
        }
        System.out.println("Could not equip " + armor.getName());
        return false;
    }

    /**
     * Equips weapon to player
     * @param weapon weapon to equip
     * @return true if successfully equipped, false otherwise
     */
    public boolean equipWeapon(Item weapon){
        boolean result = player.equipWeapon(weapon);
        if(result == true){
            System.out.println("You equipped " + weapon.getName() + " and gained " + weapon.getAttackDamage() + "% attack");
            return true;
        }
        System.out.println("Could not equip " + weapon.getName());
        return false;
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
        System.out.println("It is currently " + cycle.toString());
        return cycle;
    }

    /**
     * Checks the turn count and switches state of the cycle if interval of 10
     * Alerts user when cycle switches from day to night / vice versa
     */
    public void checkCycle(){
        if(numTurns % 10 == 0){
            cycle.switchState(cycle);
            cycle.modifyDiurnalEnemies(getNpcs());
            cycle.modifyNocturnalEnemies(getNpcs());

            System.out.println("It switched to " + cycle.toString());
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
     * npcs attack pcs after each round
     * move to next room (if tile is exit)
     * win game if tile is exit and room is goal
     */

    // public static void main(String[] args) {
    //     Pc play = new Pc(1,10,"mars",new Inventory(),100);
    //     Map map = new Map();
    //     map.setPlayer(play);
    //     MUD game = new MUD(map,"Save 1");
    //     game.printCurrentRoom();
    // }
}
