package model;

public class Npc extends Character {
    public Npc(int health, double attack, String name,int goldAmount){
        super(health,attack,name,goldAmount);
    }

    @Override
    public double getAttack(int turnNumber){
        
    }

}
