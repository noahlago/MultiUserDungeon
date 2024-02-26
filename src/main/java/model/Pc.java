package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
public class Pc extends Character {
    @JsonProperty("inventory") private Inventory inventory;
    @JsonProperty("weaponSlot") protected Item weaponSlot;
    @JsonProperty("armorSlot") protected Item armorSlot;

    @JsonCreator
    public Pc(@JsonProperty("health") double health, @JsonProperty("attack") double attack, @JsonProperty("name") String name,@JsonProperty("inventory") Inventory inventory, @JsonProperty("goldAmount") int goldAmount){
        super(health,attack,name,goldAmount);
        this.inventory = inventory;
        if(inventory.getBags()[0] == null){
            inventory.addBag(new Bag(5));
        }
    }
    @Override
    public void takeDamage(double amount){
        if(armorSlot == null || armorSlot.getDefensePercent() == 0){
            health -= amount;
        }else{
            health = health - (amount * (1- armorSlot.getDefensePercent()));
        }
    }
    @Override
    public double getAttack(){
        if(weaponSlot == null || weaponSlot.getAttackDamage() == 0){
            return attack;
        }else{
            return (attack + weaponSlot.getAttackDamage());
        }
    }

    public boolean useItem(Item item){
        if(item.getHealthPoints() > 0){
            inventory.remove(item);
            gainHealth(item.getHealthPoints());
            return true;
        }
        return false;
    }

    public boolean equipArmor(Item newArmor){
        if(inventory.remove(newArmor) == true && newArmor.getDefensePercent() != 0){
            this.armorSlot = newArmor;
            return true;
        }
        return false;
    }
    public boolean equipWeapon(Item newWeapon){
        if(newWeapon.getAttackDamage() != 0 && inventory.remove(newWeapon) == true){
            this.weaponSlot = newWeapon;
            return true;
        }
        return false;
    }
    
    public boolean RemoveArmor(){
        if(armorSlot != null){
        this.inventory.add(armorSlot);
        this.armorSlot = null;
        return true;
        }
        return false;
    }
    public boolean RemoveWeapon(){
        if(weaponSlot != null){
        this.inventory.add(weaponSlot);
        this.weaponSlot = null;
        return true;
        }
        return false;
    }
    
    public void destroyItem(Item item){

        this.inventory.remove(item);
    }
    public Inventory getInventory(){
        return inventory;
    }
    public Item getWeaponSlot(){
        return weaponSlot;
    }
    public Item getArmorSlot(){
        return armorSlot;
    }
    public int getGoldAmount(){
        return goldAmount;
    }
    public double getHealth(){
        return health;
    }
    public String getName(){
        return name;
    }
    
    public int getCurrX(){
        return this.currX;
    }

    public int getCurrY(){
        return this.currY;
    }

    public String inventoryString(){
        return this.inventory.toString();
    }

    public void addItem(Item item){
        this.inventory.add(item);
    }
    
}
