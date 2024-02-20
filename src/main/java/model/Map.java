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

public class Map {

    private List<Room> rooms; // list of rooms in the map

    /**
     * Map of rooms the player will go through
     * Currently hardcoded
     */
    public Map(){
        this.rooms = createRooms();
    }
    
    /**
     * Creates hardcoded rooms for map
     * @return List of rooms
     */
    public List<Room> createRooms(){
        List<Room> createdRooms = new ArrayList<>();

        Tile[][] tiles1 = new Tile[10][10];
        for(int row = 0; row <= 10; row++){
            for(int col = 0; col <= 10; col++){
                tiles1[row][col] = new EmptyTile();
            }
        }

        tiles1[3][5] = new ChestTile();
        tiles1[7][2] = new CharacterTile();
        tiles1[10][10] = new ExitTile();
        Tile exit1 = tiles1[10][10];

        Room room1 = new Room(10, 10, "Room one yippee", tiles1, true, false, exit1);

        Tile[][] tiles2 = new Tile[8][8];
        for(int row = 0; row <= 10; row++){
            for(int col = 0; col <= 10; col++){
                tiles2[row][col] = new EmptyTile();
            }
        }

        tiles2[3][4] = new TrapTile();
        tiles2[8][2] = new ObstacleTile();
        tiles2[1][5] = new ChestTile();
        tiles2[8][8] = new ExitTile();
        Tile exit2 = tiles2[8][8];

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
