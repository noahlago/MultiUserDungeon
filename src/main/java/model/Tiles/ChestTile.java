package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;

import model.Chest;
import model.Visitor;

@SuppressWarnings("unused")
public class ChestTile implements Tile{
    @JsonProperty("chest") private Chest chest;

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
