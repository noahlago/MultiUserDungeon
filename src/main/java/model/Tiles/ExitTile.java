package model.Tiles;

import model.Visitor;
import com.fasterxml.jackson.annotation.JsonCreator;
public class ExitTile extends ConcreteTile{
    private String name = "EXIT";
    @JsonCreator
    public ExitTile(){

    }
    
    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitExitTile(this);
        
    }

    @Override
    public String toString() {
        return "[ E ]";
    }

    public String getName(){
        return this.name;
    }
}
