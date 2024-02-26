package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;

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

    public ConcreteTile[][] createRoom(int x_dimension,int y_dimension) {
        ConcreteTile[][] tiles1 = new ConcreteTile[10][10];
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                tiles1[row][col] = new EmptyTile(row, col);
            }
        }
        return tiles1;
    }
    
    public List<Item> createItemList() {
        
        List<Item> master_list = new ArrayList<>();
        Item diamond_armor = new CoolArmor("Diamond Armor","Made by Steve"); 
        master_list.add(diamond_armor);
        Item excalibur = new CoolSword("Excalibur", "Really OP");
        master_list.add(excalibur);     

        for (int i = 0; i <= 4; i++) {
            Item peasent_rags = new LameRags("Peasent Rags", "It's better than nothing");
            master_list.add(peasent_rags);
            Item banana_suit = new LameRags("Banana Suit", "Not very protective, but at least you'll stand out!");
            master_list.add(banana_suit);
            Item dull_blade = new LameKnife("Dull Blade", "At least you won't cut yourself");
            master_list.add(dull_blade);
            Item flint_knife = new LameKnife("Flint Knife", "You can make a campfire with this");
            master_list.add(flint_knife);
            Item power_potion = new GoodPotion("Power Potion", "Very very super cool power potion");
            master_list.add(power_potion);
            Item good_steak = new GoodSteak("Steak", "Cooked to perfection");
            master_list.add(good_steak);
        }   

        Collections.shuffle(master_list);

        return master_list;
    }

    public List<Npc> createNPCList() {
        List<Npc> master_list = new ArrayList<>();
        Npc dragon = new Npc(150.0, 20.0, "Dragon", 20);
        master_list.add(dragon);
        for (int i = 0; i<4; i++) {
            Npc goblin = new Npc(50.0, 5.0, "Goblin", 10);
            master_list.add(goblin);
            master_list.add(goblin);
            Npc troll = new Npc(75.0, 10.0, "Troll", 10);
            master_list.add(troll);
            Npc werewolf = new Npc(100.0, 10.0, "Werewolf", 10);
            master_list.add(werewolf);
            Npc minotaur = new Npc(125.0, 15.0, "Minotaur", 10);
            master_list.add(minotaur);
            Npc Golem = new Npc(150.0, 5.0, "Golem", 10);
            master_list.add(Golem);
        }

        Collections.shuffle(master_list);
        return master_list;
    }
        
       
    public ConcreteTile populateRoom(int x_dimension, int y_dimension, ConcreteTile[][] room) {
        Random rand = new Random();
        int trap_num = rand.nextInt(6);
        int obstacle_num = rand.nextInt(6);
        int chest_num = rand.nextInt(4);
        int opp_num = rand.nextInt(5) + 1;
        List<String> occupied_spots = new ArrayList<>();

        for (int i = 0; i <= opp_num; i++) {
            int random_x = rand.nextInt(x_dimension);
            int random_y = rand.nextInt(y_dimension);

            String x_val = String.valueOf(random_x);
            String y_val = String.valueOf(random_y);
            String result = x_val + y_val;

            if (occupied_spots.contains(result)) {
                opp_num++;
                continue;
            }

            occupied_spots.add(result);
            List<Npc> enemy_list = createNPCList();
            Npc character = enemy_list.get(0);
            room[random_x][random_y] = new CharacterTile(character);
        }

        for (int i = 0; i <= trap_num; i++) {
            int random_x = rand.nextInt(x_dimension);
            int random_y = rand.nextInt(y_dimension);
            if (random_x == x_dimension-1 && random_y == y_dimension-1) {
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
            room[random_x][random_y] = new TrapTile("Spike Trap", "Deadly Spikes, ouch!");
        }

        for (int i = 0; i <= obstacle_num; i++) {
            int random_x = rand.nextInt(x_dimension-1);
            int random_y = rand.nextInt(y_dimension-1);
            if (random_x == x_dimension-1 && random_y == y_dimension-1) {
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
            room[random_x][random_y] = new ObstacleTile("Big #@!%$ Boulder");
        }

        for (int i = 0; i <= chest_num; i++) {
            int random_x = rand.nextInt(x_dimension);
            int random_y = rand.nextInt(y_dimension);
            if (random_x == x_dimension-1 && random_y == y_dimension-1) {
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
            
            List<Item> loot_items = createItemList();
            int item_amount = rand.nextInt(5) + 1;
            Chest chest = new Chest(null);

            for (int x = 0; x < item_amount; x++) {
                Item item = loot_items.get(x);
                chest.add(item);
            }

            occupied_spots.add(result);
            room[random_x][random_y] = new ChestTile(chest);
        }

        ConcreteTile exit1 = room[9][9];
        room[0][0] = new CharacterTile(player);

        while (true) {
            int coin_flip = rand.nextInt(2);
            if (coin_flip == 1) {
                int random_x = rand.nextInt(x_dimension);
                int coin_flip_2 = rand.nextInt(2);
                String x_val = String.valueOf(random_x);
                String y_val = String.valueOf(coin_flip_2);
                String result = x_val + y_val;
    
                if (occupied_spots.contains(result)) {
                    continue;
                }

                if (coin_flip_2 == 1) {
                    if (random_x == 0) {
                        if (occupied_spots.contains("01") && occupied_spots.contains("10")) {
                            continue;
                        }
                    }
                    if (random_x == 9) {
                        if (occupied_spots.contains("89") && occupied_spots.contains("98")) {
                            continue;
                        }
                    }

                    String add = String.valueOf(random_x+1);
                    String block1 = add + "0";

                    String cord1 = String.valueOf(random_x-1);
                    String block2 = cord1 + "0";

                    String cord2 = String.valueOf(random_x);
                    String block3 = cord2 + "1";
                    
                    if (occupied_spots.contains(block3) && occupied_spots.contains(block2) && occupied_spots.contains(block1)) {
                        continue;
                    }

                    add = String.valueOf(random_x+1);
                    block1 = add + "9";

                    cord1 = String.valueOf(random_x-1);
                    block2 = cord1 + "9";

                    cord2 = String.valueOf(random_x);
                    block3 = cord2 + "8";
                    
                    if (occupied_spots.contains(block3) && occupied_spots.contains(block2) && occupied_spots.contains(block1)) {
                        continue;
                    }
                    
                    room[random_x][0] = new ExitTile();
                    exit1 = room[random_x][0];
                    break;
                }
                else {
                    room[random_x][9] = new ExitTile();
                    exit1 = room[random_x][9];
                    break;
                }
            }
            else {
                int random_y = rand.nextInt(y_dimension);
                int coin_flip_2 = rand.nextInt(2);
                String x_val = String.valueOf(coin_flip_2);
                String y_val = String.valueOf(random_y);
                String result = x_val + y_val;
    
                if (occupied_spots.contains(result)) {
                    continue;
                }
                
                if (coin_flip_2 == 1) {
                    if (random_y == 0) {
                        if (occupied_spots.contains("01") && occupied_spots.contains("10")) {
                            continue;
                        }
                    }
                    if (random_y == 9) {
                        if (occupied_spots.contains("89") && occupied_spots.contains("98")) {
                            continue;
                        }
                    }

                    String add = String.valueOf(random_y+1);
                    String block1 = "0" + add;

                    String cord1 = String.valueOf(random_y-1);
                    String block2 = "0" + cord1;

                    String cord2 = String.valueOf(random_y);
                    String block3 = "1" + cord2;
                    
                    if (occupied_spots.contains(block3) && occupied_spots.contains(block2) && occupied_spots.contains(block1)) {
                        continue;
                    }

                    add = String.valueOf(random_y+1);
                    block1 = "9" + add;

                    cord1 = String.valueOf(random_y-1);
                    block2 = "9" + cord1;

                    cord2 = String.valueOf(random_y);
                    block3 = "8" + cord2;
                    
                    if (occupied_spots.contains(block3) && occupied_spots.contains(block2) && occupied_spots.contains(block1)) {
                        continue;
                    }

                    room[0][random_y] = new ExitTile();        
                    exit1 = room[0][random_y];
                    break;
                }
                else {
                    room[9][random_y] = new ExitTile();
                    exit1 = room[9][random_y];
                    break;
                }
            }
        }
        return exit1;
    }
    
    /**
     * Creates hardcoded rooms for map
     * @return List of rooms
     */
    public List<Room> createRooms(){
        List<Room> createdRooms = new ArrayList<>();
        // Creates room based on dimensions
        ConcreteTile[][] tiles1 = createRoom(10,10);
        // Randomly populates room with traps, obsticales, and chests. Returns Exit located at random position
        ConcreteTile exit1 = populateRoom(10, 10, tiles1);
        Npc[] npcs1 = {};
        // fully creates the room
        Room room1 = new Room(10, 10, "Room one: The beggining of the journey", tiles1, true, false, exit1, npcs1);


        ConcreteTile[][] tiles2 = createRoom(8,8);
        ConcreteTile exit2 = populateRoom(8,8,tiles2);
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

    public void renderRooms(){
        for(Room room : rooms){
            room.specializeTiles();
        }
    }
}