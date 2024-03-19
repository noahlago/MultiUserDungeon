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

    @JsonCreator 
    public Map(@JsonProperty("rooms") List<Room> rooms){
        this.rooms = rooms;
    }

    @JsonCreator 
    public Map(){
        this.rooms = null;
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
        
       
    public ConcreteTile populateRoom(int x_dimension, int y_dimension, ConcreteTile[][] room, int exitX, int exitY) {
        Random rand = new Random();
        int trap_num = rand.nextInt(6);
        int obstacle_num = rand.nextInt(6);
        int chest_num = rand.nextInt(4);
        int opp_num = rand.nextInt(5) + 1;
        List<String> occupied_spots = new ArrayList<>();
        occupied_spots.add("00");

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
            room[random_x][random_y] = new CharacterTile(character, random_x, random_y);
        }

        for (int i = 0; i <= trap_num; i++) {
            int random_x = rand.nextInt(x_dimension);
            int random_y = rand.nextInt(y_dimension);
            if (random_x == x_dimension-1 && random_y == y_dimension-1) {
                trap_num++;
                continue;
            }

            if (random_x == 0 && random_y == 1 || random_x == 1 && random_y == 0) {
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

            if (random_x == 0 && random_y == 1) {
                obstacle_num++;
                continue;
            }

            if (random_x == 1 && random_y == 0) {
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

            if (random_x == 0 && random_y == 1) {
                chest_num++;
                continue;
            }

            if (random_x == 1 && random_y == 0) {
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

        room[exitX][exitY] = new ExitTile();
        ConcreteTile exit1 = room[exitX][exitY];
        room[0][0] = new CharacterTile(player, 0, 0);
        
        return exit1;
    }

    public int numRooms(){
        return rooms.size();
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