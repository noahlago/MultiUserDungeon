package model.Tiles;

import model.Chest;
import model.Visitor;

@SuppressWarnings("unused")
public class ChestTile implements Tile{
    private Chest chest;

    public ChestTile(Chest chest){
        this.chest = chest;
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitChestTile(this);
    }

    @Override
    public String toString() {
        return "[ C ]";
    }
}
