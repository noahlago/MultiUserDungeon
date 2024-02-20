package model;

import model.Tiles.Tile;

public class Room {
    private int width; // width of room
    private int height; // height of room
    private String description; // description of room
    private Tile[][] tiles; // tiles within the room
    private boolean isStart; // if room is start of map
    private boolean isGoal; // if room is end of map
    private Tile exit; // exit tile

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
    public Room(int width, int height, String description, Tile[][] tiles, boolean isStart, boolean isGoal, Tile exit){
        this.width = width;
        this.height = height;
        this.description = description;
        this.tiles = tiles;
        this.isStart = isStart;
        this.isGoal = isGoal;
        this.exit = exit;
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
    public Tile[][] getTiles(){
        return tiles;
    }

    /**
     * @param x -- x coordinate
     * @param y -- y coordinate
     * @return tile within given coordinate
     */
    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

    /**
     * @return true if room is start, false otherwise
     */
    public boolean isStart(){
        return isStart;
    }

    /**
     * @return true if room is goal, false otherwise
     */
    public boolean isGoal(){
        return isGoal;
    }

    /**
     * @return exit tile
     */
    public Tile getExit(){
        return exit;
    }

    public String toString(){
        return "Room of " + width + "x" + height + " tiles\n" + description;
    }
}
