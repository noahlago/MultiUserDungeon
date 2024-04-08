package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import model.Visitor;

public class ShrineTile extends ConcreteTile{
    @JsonProperty("name") private String name;
    @JsonProperty("canPray") private boolean canPray;

    @JsonCreator
    public ShrineTile(@JsonProperty("name")String name, @JsonProperty("canPray") boolean canPray){
        this.name = name;
        this.canPray = canPray;
        super.setType("SHRINE");
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitShrineTile(this);
    }

    @Override
    public String toString() {
        return "[ S ]";
    }

    public String getName() {
        return name;
    }

    public boolean isCanPray() {
        return canPray;
    }

    public void setCanPray(){
        canPray = true;
    }
    
}
