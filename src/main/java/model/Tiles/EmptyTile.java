package model.Tiles;

import model.Visitor;
import com.fasterxml.jackson.annotation.JsonCreator;

public class EmptyTile extends ConcreteTile{
    private String name = "empty";
    @JsonCreator
    public EmptyTile(){

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
