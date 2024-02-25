package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import model.Visitor;

public class TrapTile extends ConcreteTile{
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("armed") private boolean armed;
    @JsonCreator
    public TrapTile(@JsonProperty("name")String name,@JsonProperty("description")String description){
        this.name = name;
        this.description = description;
        this.armed = true;
        super.setType("TRAP");
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitTrapTile(this);
    }

    @Override
    public String toString() {
        return "[ T ]";
    }

	public String getName() {
		return name;
	}
    
    public boolean getArmed(){
        return armed;
    }

	public String getDescription() {
		return description;
	}

	public boolean isArmed() {
		return armed;
	}
}
