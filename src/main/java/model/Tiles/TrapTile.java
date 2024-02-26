package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import model.Visitor;

public class TrapTile extends ConcreteTile{
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("armed") private boolean armed;
    @JsonProperty("discovered") private boolean discovered;
    @JsonProperty("damage") private int damage;

    @JsonCreator
    public TrapTile(@JsonProperty("name")String name,@JsonProperty("description")String description){
        this.name = name;
        this.description = description;
        this.armed = true;
        this.discovered = false;
        this.damage = 25;
        super.setType("TRAP");
    }

    @JsonCreator
    public TrapTile(@JsonProperty("name")String name,@JsonProperty("description")String description, @JsonProperty("damage")int damage){
        this.name = name;
        this.description = description;
        this.armed = true;
        this.discovered = false;
        this.damage = damage;
        super.setType("TRAP");
    }

    @Override
    public void accept(Visitor visitor) {
        //Double dispatch, object calls visitors proper method
        visitor.visitTrapTile(this);
    }

    public void discover(){
        this.discovered = true;
    }

    public void disarm(){
        this.armed = false;
    }

    @Override
    public String toString() {
        if(discovered){
            return "[ T ]";
        }
        else{
            return "[ H ]";
        }
        
    }

	public String getName() {
		return name;
	}
    
    public boolean getArmed(){
        return armed;
    }

    public boolean getDiscovered(){
        return discovered;
    }

	public String getDescription() {
		return description;
	}

	public boolean isArmed() {
		return armed;
	}

    public int getDamage(){
        return damage;
    }
}
