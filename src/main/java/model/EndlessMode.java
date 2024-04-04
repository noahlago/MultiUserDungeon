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
    // Dictionary Format: Original Room : [Original Room : Edited Room]
    @JsonProperty("mapHistory") private Dictionary<Room, Room> mapHistory= new Hashtable<>();
    @JsonProperty("endlessMap") private Map endlessMap;
    @JsonProperty("index") private int index;

    public Room generateRandomRoom(){ 
        ConcreteTile[][] tiles1A = endlessMap.createRoom(10, 10);
        ConcreteTile exit1A = endlessMap.populateRoom(10, 10, tiles1A);
        Npc[] npcs = {};
        Room newRoom = new Room(10, 10, "Endless room", tiles1A, true, false, exit1A, npcs);
        mapHistory.put(newRoom, newRoom);
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
    public List<Room> getEditedRoomList() {
        List<Room> valuesList = new ArrayList<>();
        Enumeration<Room> valuesEnumeration = mapHistory.elements();
        while(valuesEnumeration.hasMoreElements()) {
            valuesList.add(valuesEnumeration.nextElement());
        }
        return valuesList;
    }

    public Room getPreviousRoom() {
        List<Room> keysList = getOriginalRoomList();
        if (mapHistory.size() - index > 5) {
            List<Room> objects = getEditedRoomList();
            int position = index - 1;
            Room res = objects.get(position);
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

    public void updateRoom(Room originalRoom, Room editedRoom) {
        mapHistory.put(originalRoom,editedRoom);
        return;
    }
}
