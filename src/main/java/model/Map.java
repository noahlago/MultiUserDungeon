package model;

import java.util.ArrayList;
import java.util.List;

import model.Tiles.CharacterTile;
import model.Tiles.ChestTile;
import model.Tiles.EmptyTile;
import model.Tiles.ExitTile;
import model.Tiles.ObstacleTile;
import model.Tiles.Tile;
import model.Tiles.TrapTile;

/**
 * This class represents a map, which is a collection of 2 or more rooms
 * Each map includes at least one start room and one goal room
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
public class Map {

    private List<Room> rooms; // list of rooms in the map
    private Character player;

    /**
     * Map of rooms the player will go through
     * Currently hardcoded
     */
    public Map(){
        this.rooms = createRooms();
    }

    public void setPlayer(Character player) {
        this.player = player;
    }
    
    /**
     * Creates hardcoded rooms for map
     * @return List of rooms
     */
    public List<Room> createRooms(){
        List<Room> createdRooms = new ArrayList<>();

        Tile[][] tiles1 = new Tile[10][10];
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                tiles1[row][col] = new EmptyTile();
            }
        }

        tiles1[0][0] = new CharacterTile(player);
        tiles1[3][5] = new ChestTile(new Chest(new Item[0]));
        //tiles1[7][2] = new CharacterTile();
        tiles1[9][9] = new ExitTile();
        Tile exit1 = tiles1[9][9];

        Room room1 = new Room(10, 10, "Room one: The begging of the journey", tiles1, true, false, exit1);

        Tile[][] tiles2 = new Tile[8][8];
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                tiles2[row][col] = new EmptyTile();
            }
        }

        tiles2[3][4] = new TrapTile("Spike Trap", "Deadly Spikes, ouch!");
        tiles2[7][2] = new ObstacleTile("Big #@!%$ Boulder");
        tiles2[1][5] = new ChestTile(new Chest(new Item[0]));
        tiles2[7][7] = new ExitTile();
        Tile exit2 = tiles2[7][7];

        Room room2 = new Room(8, 8, "Room two yippee", tiles2, false, true, exit2);

        createdRooms.add(room1);
        createdRooms.add(room2);

        return createdRooms;
    }

    /**
     * @return list of rooms in the map
     */
    public List<Room> getRooms(){
        return rooms;
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
