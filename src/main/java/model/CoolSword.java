package model;

public class CoolSword extends CharacterOption {
    public CoolSword(CharacterOption characterOption){
        super(characterOption);
    }
    
    public double getAttack(){
        return (super.getAttack() * 1.5);
    }
}
