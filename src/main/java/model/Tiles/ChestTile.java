package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import model.Chest;
import model.Visitor;

public class ChestTile extends ConcreteTile{
    @JsonProperty("chest") private Chest chest;

    @JsonCreator
    public ChestTile(@JsonProperty("chest")Chest chest){
        this.chest = chest;
        super.setType("CHEST");
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

    public Chest getChest() {
        return chest;
    }
    
}
