package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.EmptyTile;
import model.Tiles.ExitTile;
import model.Tiles.ObstacleTile;
import model.Tiles.TrapTile;
import model.Tiles.ConcreteTile;

/**
 * This class represents a map, which is a collection of 2 or more rooms
 * Each map includes at least one start room and one goal room
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
public class Map {

    @JsonProperty("rooms") private List<Room> rooms; // list of rooms in the map
    @JsonProperty("player") private Character player;

    /**
     * Map of rooms the player will go through
     * Currently hardcoded
     */
    @JsonCreator 
    public Map(){
        this.rooms = createRooms();
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    // public List build_board(int x_dimension,int y_dimension, ) {

    // }
    
    /**
     * Creates hardcoded rooms for map
     * @return List of rooms
     */
    public List<Room> createRooms(){
        List<Room> createdRooms = new ArrayList<>();

        ConcreteTile[][] tiles1 = new ConcreteTile[10][10];
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                tiles1[row][col] = new EmptyTile(row, col);
            }
        }
        
        tiles1[0][0] = new CharacterTile(player);
        tiles1[0][1] = new ObstacleTile("Sleeping Snorlax");
        tiles1[3][5] = new ChestTile(new Chest(new Item[0]));

        Random rand = new Random();
        int trap_num = rand.nextInt(6);
        int obstacle_num = rand.nextInt(6);
        int chest_num = rand.nextInt(4);
        List<String> occupied_spots = new ArrayList<>();

        for (int i = 0; i <= trap_num; i++) {
            int random_x = rand.nextInt(10);
            int random_y = rand.nextInt(10);
            if (random_x == 9 && random_y == 9) {
                trap_num++;
                continue;
            }

            String x_val = String.valueOf(random_x);
            String y_val = String.valueOf(random_y);
            String result = x_val + y_val;

            if (occupied_spots.contains(result)) {
                trap_num++;
                continue;
            }
            
            occupied_spots.add(result);
            tiles1[random_x][random_y] = new TrapTile("Spike Trap", "Deadly Spikes, ouch!");
        }

        for (int i = 0; i <= obstacle_num; i++) {
            int random_x = rand.nextInt(10);
            int random_y = rand.nextInt(10);
            if (random_x == 9 && random_y == 9) {
                obstacle_num++;
                continue;
            }

            String x_val = String.valueOf(random_x);
            String y_val = String.valueOf(random_y);
            String result = x_val + y_val;

            if (occupied_spots.contains(result)) {
                obstacle_num++;
                continue;
            }
            
            occupied_spots.add(result);
            tiles1[random_x][random_y] = new ObstacleTile("Big #@!%$ Boulder");
        }

        for (int i = 0; i <= chest_num; i++) {
            int random_x = rand.nextInt(10);
            int random_y = rand.nextInt(10);
            if (random_x == 9 && random_y == 9) {
                chest_num++;
                continue;
            }

            String x_val = String.valueOf(random_x);
            String y_val = String.valueOf(random_y);
            String result = x_val + y_val;

            if (occupied_spots.contains(result)) {
                chest_num++;
                continue;
            }
            
            occupied_spots.add(result);
            tiles1[random_x][random_y] = new ChestTile(new Chest(new Item[0]));
        }

        ConcreteTile exit1 = tiles1[9][9];

        int coin_flip = rand.nextInt(2);
        if (coin_flip == 1) {
            int random_x = rand.nextInt(10);
            int coin_flip_2 = rand.nextInt(2);
            if (coin_flip_2 == 1) {
                tiles1[random_x][0] = new ExitTile();
                exit1 = tiles1[random_x][0];
            }
            else {
                tiles1[random_x][9] = new ExitTile();
                exit1 = tiles1[random_x][9];
            }
        }
        else {
            int random_y = rand.nextInt(10);
            int coin_flip_2 = rand.nextInt(2);
            if (coin_flip_2 == 1) {
                tiles1[0][random_y] = new ExitTile();
                exit1 = tiles1[0][random_y];
            }
            else {
                tiles1[9][random_y] = new ExitTile();
                exit1 = tiles1[9][random_y];
            }
        }

        // tiles1[0][0] = new CharacterTile(player);
        // tiles1[3][5] = new ChestTile(new Chest(new Item[0]));
        //tiles1[7][2] = new CharacterTile();
        // tiles1[9][9] = new ExitTile();
        // Tile exit1 = tiles1[9][9];
        Npc[] npcs1 = {};

        Room room1 = new Room(10, 10, "Room one: The beggining of the journey", tiles1, true, false, exit1, npcs1);

        ConcreteTile[][] tiles2 = new ConcreteTile[8][8];
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                tiles2[row][col] = new EmptyTile(row, col);
            }
        }

        tiles2[3][4] = new TrapTile("Spike Trap", "Deadly Spikes, ouch!");
        tiles2[7][2] = new ObstacleTile("Big #@!%$ Boulder");
        tiles2[1][5] = new ChestTile(new Chest(new Item[0]));
        tiles2[7][7] = new ExitTile();
        ConcreteTile exit2 = tiles2[7][7];
        Npc[] npcs2 = {};

        Room room2 = new Room(8, 8, "Room two yippee", tiles2, false, true, exit2, npcs2);

        createdRooms.add(room1);
        createdRooms.add(room2);

        return createdRooms;
    }

    /**
     * @return list of rooms in the map
     */
    @JsonProperty("rooms")
    public List<Room> getRooms(){
        return rooms;
    }

    @JsonProperty("player")
    public Character getPlayer(){
        return this.player;
    }

    /**
     * returns toString of all rooms
     */
    public String toString(){
        String retVal = "";
        for(Room room : rooms){
            retVal += room.toString() + "\n";
        }

        return retVal;
    }
}