package model.Tiles;

import model.Visitor;
import com.fasterxml.jackson.annotation.JsonCreator;

public class EntranceTile extends ConcreteTile{

    @JsonCreator
    public EntranceTile(){
        super.setType("ENTRANCE");
    }
    
    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitEntranceTile(this);
        
    }

    @Override
    public String toString() {
        return "[ e ]";
    }

}
