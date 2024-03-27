package model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import model.Tiles.ConcreteTile;

/**
 * Creates a game mode for the user to play that never ends 
 * @author Eliot Seol (eys7709@rit.edu)
 */

public class EndlessMode {
    // Dictionary Format: Original Room : [Index of Room : Edited Room]
    @JsonProperty("mapHistory") private Dictionary<Room, ArrayList<Object>> mapHistory= new Hashtable<>();
    @JsonProperty("endlessMap") private Map endlessMap;
    @JsonProperty("index") private int index;

    public Room generateRandomRoom(){ 
        
        ConcreteTile[][] tiles1A = endlessMap.createRoom(10, 10);
        ConcreteTile exit1A = endlessMap.populateRoom(10, 10, tiles1A);
        Npc[] npcs = {};
        Room newRoom = new Room(10, 10, "Room one", tiles1A, true, false, exit1A, npcs);
        ArrayList<Object> data_list = new ArrayList<>();
        int index = mapHistory.size() + 1;
        data_list.add(index);
        data_list.add(newRoom);
        mapHistory.put(newRoom, data_list);
        index = mapHistory.size();
        return newRoom;
    }

    // Gets the originally, unedited generated list of rooms
    public List<Room> getOriginalRoomList() {
        List<Room> keysList = new ArrayList<>();
        Enumeration<Room> keysEnumeration = mapHistory.keys();
        while(keysEnumeration.hasMoreElements()) {
            keysList.add(keysEnumeration.nextElement());
        }

        return keysList;
    }

    // returns the values of the mapHistory dictionary
    public List<ArrayList<Object>> getEditedRoomList() {
        List<ArrayList<Object>> valuesList = new ArrayList<>();
        Enumeration<ArrayList<Object>> valuesEnumeration = mapHistory.elements();
        while(valuesEnumeration.hasMoreElements()) {
            valuesList.add(valuesEnumeration.nextElement());
        }
        return valuesList;
    }

    public Room getPreviousRoom() {
        List<Room> keysList = getOriginalRoomList();
        if (mapHistory.size() - index > 5) {
            List<ArrayList<Object>> objects = getEditedRoomList();
            int position = index - 1;
            Object res = objects.get(position).get(1);
            return res;
        }
        Room res = keysList.get(index - 1);
        index--;
        return res;
    }

    public Room getNextRoom() {
        List<Room> keysList = getOriginalRoomList();
        if (index + 1 > mapHistory.size()) {
            return generateRandomRoom();
        }
        Room res = keysList.get(index + 1);
        index++;
        return res;
    }
}
