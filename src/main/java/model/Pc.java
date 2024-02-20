package model;

public class Pc extends Character {
    private Inventory inventory;
    public Pc(int health, double attack, String name,Inventory inventory,int goldAmount){
        super(health,attack,name,goldAmount);
        this.inventory = inventory;
    }
    @Override
    public void takeDamage(int amount){
        //lessen damage based off of armor
    }
    @Override
    public double getAttack(int turnNumber){
        //raise attack based off of items
    }
}
