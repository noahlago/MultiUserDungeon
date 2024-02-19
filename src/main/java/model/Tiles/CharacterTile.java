package model.Tiles;

import model.Visitor;

public class CharacterTile implements Tile{

    public CharacterTile(){
    }
    
    @Override
    public void accept(Visitor visitor) {

        //Double dispatch, object calls visitors proper method
        visitor.visitCharacterTile(this);
        
    }
}