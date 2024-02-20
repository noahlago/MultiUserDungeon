package model;

import model.Tiles.Tile;

public class Room {
    private int width;
    private int height;
    private String description;
    private Tile[][] tiles;
    private boolean isGoal;

    public Room(int width, int height, String description, Tile[][] tiles, boolean isGoal){
        this.width = width;
        this.height = height;
        this.description = description;
        this.tiles = tiles;
        this.isGoal = isGoal;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public Tile[][] getTiles(){
        return tiles;
    }

    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

    public boolean isGoal(){
        return isGoal;
    }

    public String toString(){
        return "Room of " + width + "x" + height + " tiles\n" + description;
    }
}
