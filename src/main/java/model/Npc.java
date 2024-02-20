package model;

public abstract class Npc extends Character {
    protected boolean isNocturnal = false;
    protected double baseHealth;
    protected double baseAttack;

    public Npc(double health, double attack, String name,int goldAmount){
        super(health,attack,name,goldAmount);
        baseHealth = health;
        baseAttack = attack;
    }

    @Override
    public double getAttack(){
       return attack;
     }
     @Override
     public void editStats(double factor){
        attack = baseAttack * factor;
        health = baseHealth * factor;
     }
     public boolean getIsNocturnal(){
        return isNocturnal;
     }
}