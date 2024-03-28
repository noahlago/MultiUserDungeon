package model;

import model.Tiles.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class represents a room, which is made up of a certain number of tiles (in width and height)
 * Each room has one exit tile, and other tiles can either be empty, traps, obstacles, characters, or chests
 * @author Zoe Rizzo (zjr1377@rit.edu)
 */
public class Room {
    @JsonProperty("width")private int width; // width of room
    @JsonProperty("height")private int height; // height of room
    @JsonProperty("description")private String description; // description of room
    @JsonProperty("tiles")private ConcreteTile[][] tiles; // tiles within the room
    @JsonProperty("isStart")private boolean isStart; // if room is start of map
    @JsonProperty("isGoal")private boolean isGoal; // if room is end of map
    @JsonProperty("exit")private ConcreteTile exit; // exit tile
    @JsonProperty("npcs")private Npc[] npcs;

    /**
     * Room -- defines an instance of a room
     * 
     * @param width
     * @param height
     * @param description
     * @param tiles
     * @param isStart
     * @param isGoal
     * @param exit
     */
    @JsonCreator
    public Room(@JsonProperty("width")int width,@JsonProperty("height")int height, @JsonProperty("description")String description, @JsonProperty("tiles")ConcreteTile[][] tiles,@JsonProperty("isStart")boolean isStart,@JsonProperty("isGoal")boolean isGoal,@JsonProperty("exit")ConcreteTile exit,@JsonProperty("npc")Npc[] npcs){
        this.width = width;
        this.height = height;
        this.description = description;
        this.tiles = tiles;
        this.isStart = isStart;
        this.isGoal = isGoal;
        this.exit = exit;
        this.npcs = npcs;
    }

    /**
     * @return width of room
     */
    public int getWidth(){
        return width;
    }

    /**
     * @return height of room
     */
    public int getHeight(){
        return height;
    }

    /**
     * @return list of tiles in room
     */
    public ConcreteTile[][] getTiles(){
        return tiles;
    }

    public void updateTiles(ConcreteTile[][] tiles){
        this.tiles = tiles;
    }

    /**
     * @param x -- x coordinate
     * @param y -- y coordinate
     * @return tile within given coordinate
     */

    public ConcreteTile getTile(int x, int y){
        return tiles[x][y];
    }


    public String getDescription(){
        return description;
    }

    /**
     * @return true if room is start, false otherwise
     */
    public boolean getIsStart(){
        return isStart;
    }

    /**
     * @return true if room is goal, false otherwise
     */
    public boolean getIsGoal(){
        return isGoal;
    }

    /**
     * @return exit tile
     */
    public ConcreteTile getExit(){
        return exit;
    }

    public Npc[] getNpcs(){
        return npcs;
    }

    public String toString(){
        String grid = "Room of " + width + "x" + height + " tiles\n" + description +"\n\n";
        
        for(ConcreteTile[] row: tiles){
            for(ConcreteTile col: row){
                grid += col.toString();
            }
            grid+= "\n";
        }

        return grid;
    }

    public void specializeTiles(){
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                ConcreteTile current = tiles[i][j];
                String type = current.getType();
                if(type.equals("CHARACTER")){
                    tiles[i][j] = new CharacterTile(tiles[i][j].getCharacter(), i , j);
                }else if(type.equals("CHEST")){
                    tiles[i][j] = new ChestTile(tiles[i][j].getChest());
                }else if(type.equals("EMPTY")){
                    tiles[i][j] = new EmptyTile(tiles[i][j].getRow(), tiles[i][j].getCol());
                }else if(type.equals("EXIT")){
                    tiles[i][j] = new ExitTile();
                }else if(type.equals("TRAP")){
                    tiles[i][j] = new TrapTile(tiles[i][j].getName(), tiles[i][j].getDescription());
                }else if(type.equals("OBSTACLE")){
                    tiles[i][j] = new ObstacleTile(tiles[i][j].getName());
                }else{
                    tiles[i][j] = new EmptyTile(i, j);
                }
            }
        }
        //System.out.println(this);
    }
}
