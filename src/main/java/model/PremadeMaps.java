package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.Tiles.ConcreteTile;

public class PremadeMaps {
    @JsonProperty("maps") private List<Map> maps;

    @JsonCreator
    public PremadeMaps(){
        this.maps = null;
    }

    /**
     * @return list of premade maps
     */
    public List<Map> getMaps(){
        return maps;
    }

    /**
     * @param index map number
     * @return single map
     */
    public Map getMap(int index){
        return maps.get(index);
    }

    public void createMaps(){
        // Map 1
        Map map1 = new Map();
        List<Room> rooms1 = new ArrayList<>();
        ConcreteTile[][] tiles1A = map1.createRoom(10,10);
        ConcreteTile exit1A = map1.populateRoom(10, 10, tiles1A, 10, 6);
        Npc[] npcs1A = {};
        Room room1A = new Room(10, 10, "Room one: The begining of the journey", tiles1A, true, false, exit1A, npcs1A);
    }
}
