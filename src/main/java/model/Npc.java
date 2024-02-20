package model;

public abstract class Npc extends Character {
    protected boolean isNocturnal = false;
    protected double baseHealth;

    public Npc(double health, double attack, String name,int goldAmount){
        super(health,attack,name,goldAmount);
        baseHealth = health;
    }

    @Override
    public double getAttack(){
       reutrn 0.0
     }
     @Override
     public void editStats(double factor){
        attack = attack * factor;
        health = baseHealth * factor;
     }
     public boolean getIsNocturnal(){
        return isNocturnal;
     }
}