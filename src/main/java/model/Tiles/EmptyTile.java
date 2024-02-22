package model.Tiles;

import model.Visitor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class EmptyTile extends ConcreteTile{
    @JsonProperty("row") private int row;
    @JsonProperty("col") private int col;
    @JsonProperty("name") private String name = "EMPTY";

    @JsonCreator
    public EmptyTile(@JsonProperty("row") int row, @JsonProperty("col") int col){
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }


    public int getRow() {
        return row;
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitEmptyTile(this);
    }

    @Override
    public String toString() {
        return "[   ]";
    }

    public String getName(){
        return this.name;
    }
}
