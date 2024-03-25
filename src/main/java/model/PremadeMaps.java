package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Tiles.ConcreteTile;

/**
 * Creates a list of hard-coded premade maps (currently 3)
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
public class PremadeMaps {
    @JsonProperty("maps") private List<Map> maps;
    @JsonProperty("index") private int index;

    @JsonCreator
    public PremadeMaps(){
        this.maps = createMaps();
        this.index = 0;
    }

    /**
     * @return list of premade maps
     */
    public List<Map> getMaps(){
        return maps;
    }

    /**
     * Gets map given a specific index
     * @param ind index of map
     * @return specific map
     */
    public Map getMap(int ind){
        return maps.get(ind);
    }

    public void incrementIndex(){
        if(index < maps.size()){
            this.index++;
        }
    }

    public void decrementIndex(){
        if(index > 0){
            this.index--;
        }
    }

    /**
     * View next map in list
     */
    public void viewNextMap(){
        incrementIndex();
        getMap(index);
    }

    /**
     * View previous map in list
     */
    public void viewPrevMap(){
        decrementIndex();
        getMap(index);
    }

    /**
     * Creates hard-coded premade maps (currently 3 maps)
     * @return list of premade maps
     */
    public List<Map> createMaps(){
        List<Map> mapsList = new ArrayList<>();

        // Map 1
        Map map1 = new Map();
        List<Room> roomsMap1 = new ArrayList<>();

        // Room 1A
        ConcreteTile[][] tiles1A = map1.createRoom(10, 10);
        ConcreteTile exit1A = map1.populateRoom(10, 10, tiles1A, 10, 6);
        Npc[] npcs1A = {};
        Room room1A = new Room(10, 10, "Room one", tiles1A, true, false, exit1A, npcs1A);
        roomsMap1.add(room1A);

        // Room 1B
        ConcreteTile[][] tiles1B = map1.createRoom(10, 10);
        ConcreteTile exit1B = map1.populateRoom(10, 10, tiles1B, 0, 8);
        Npc[] npcs1B = {};
        Room room1B = new Room(10, 10, "Room two", tiles1B, false, false, exit1B, npcs1B);
        roomsMap1.add(room1B);

        // Room 1C
        ConcreteTile[][] tiles1C = map1.createRoom(10, 10);
        ConcreteTile exit1C = map1.populateRoom(10, 10, tiles1C, 5, 10);
        Npc[] npcs1C = {};
        Room room1C = new Room(10, 10, "Room three", tiles1C, false, true, exit1C, npcs1C);
        roomsMap1.add(room1C);

        map1 = new Map(roomsMap1);


        // Map 2
        Map map2 = new Map();
        List<Room> roomsMap2 = new ArrayList<>();

        // Room 2A
        ConcreteTile[][] tiles2A = map2.createRoom(10, 10);
        ConcreteTile exit2A = map2.populateRoom(10, 10, tiles2A, 0, 2);
        Npc[] npcs2A = {};
        Room room2A = new Room(10, 10, "Room one", tiles2A, true, false, exit2A, npcs2A);
        roomsMap2.add(room2A);

        // Room 2B
        ConcreteTile[][] tiles2B = map2.createRoom(10, 10);
        ConcreteTile exit2B = map2.populateRoom(10, 10, tiles2B, 10, 8);
        Npc[] npcs2B = {};
        Room room2B = new Room(10, 10, "Room two", tiles2B, false, false, exit2B, npcs2B);
        roomsMap2.add(room2B);

        // Room 2C
        ConcreteTile[][] tiles2C = map2.createRoom(10, 10);
        ConcreteTile exit2C = map2.populateRoom(10, 10, tiles2C, 3, 10);
        Npc[] npcs2C = {};
        Room room2C = new Room(10, 10, "Room three", tiles2C, false, false, exit2C, npcs2C);
        roomsMap2.add(room2C);

        map2 = new Map(roomsMap2);

        // Room 2D
        ConcreteTile[][] tiles2D = map2.createRoom(10, 10);
        ConcreteTile exit2D = map2.populateRoom(10, 10, tiles2D, 9, 0);
        Npc[] npcs2D = {};
        Room room2D = new Room(10, 10, "Room four", tiles2D, false, true, exit2D, npcs2D);
        roomsMap2.add(room2D);


        // Map 3
        Map map3 = new Map();
        List<Room> roomsMap3 = new ArrayList<>();

        // Room 3A
        ConcreteTile[][] tiles3A = map3.createRoom(10, 10);
        ConcreteTile exit3A = map3.populateRoom(10, 10, tiles3A, 10, 10);
        Npc[] npcs3A = {};
        Room room3A = new Room(10, 10, "Room one", tiles3A, true, false, exit3A, npcs3A);
        roomsMap3.add(room3A);

        // Room 3B
        ConcreteTile[][] tiles3B = map3.createRoom(10, 10);
        ConcreteTile exit3B = map3.populateRoom(10, 10, tiles3B, 0, 3);
        Npc[] npcs3B = {};
        Room room3B = new Room(10, 10, "Room two", tiles3B, false, true, exit3B, npcs3B);
        roomsMap3.add(room3B);

        map3 = new Map(roomsMap3);

        mapsList.add(map1);
        mapsList.add(map2);
        mapsList.add(map3);

        return mapsList;
    }

    public String toString(){
        String retVal = "";

        int i = 1;
        for(Map map : maps){
            retVal += "Map " + i + ": " + map.numRooms() + " rooms";
            i++;
        }

        return retVal;
    }
}
