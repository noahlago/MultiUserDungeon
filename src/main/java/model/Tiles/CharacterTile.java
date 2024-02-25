package model.Tiles;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import model.Character;
import model.Npc;
import model.Visitor;


public class CharacterTile extends ConcreteTile{
    @JsonProperty("character")private Character character;

    @JsonCreator
    public CharacterTile(@JsonProperty("character")Character character){
        this.character = character;
        super.setType("CHARACTER");
    }
    
    @Override
    public void accept(Visitor visitor) {

        //Double dispatch, object calls visitors proper method
        visitor.visitCharacterTile(this);
    }

    @Override
    public String toString() {
        //If the character is an NPC display it as !
        if(this.character instanceof Npc){
            return "[ ! ]";
        }
        //if playable character display as i
        return "[ i ]";
    }

    public Character getCharacter(){
        return this.character;
    }
}