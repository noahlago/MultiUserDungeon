package model.Tiles;

import model.Visitor;

public interface Tile {
    public void accept(Visitor visitor);

    public String toString();
} 
