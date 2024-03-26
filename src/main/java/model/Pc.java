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
    /**
     * Calculates the amount of damage to take based on armor and defense
     * @param amount the amount of damage to take
     */
    @Override
    public void takeDamage(double amount){
        if(armorSlot == null || armorSlot.getDefensePercent() == 0){
            health -= amount;
            System.out.println("You took " + amount + " damage\n Your health is now " + getHealth());
        }else{
            double damage = (amount * (1- armorSlot.getDefensePercent()));
            health = health - damage;
            System.out.println("You took " + damage + " damage\n Your health is now " + getHealth());
        }
    }

    /**
     * Calculates attack amount based on equipped weapon
     * @return attack amount
     */
    @Override
    public double getAttack(){
        if(weaponSlot == null || weaponSlot.getAttackDamage() == 0){
            return attack;
        }else{
            return (attack + weaponSlot.getAttackDamage());
        }
    }

    /**
     * Uses an item to increase player health
     * @param item item to use
     * @return true if item used successfully, false otherwise
     */
    public boolean useItem(Item item){
        if(item.getType() == ItemType.CONSUMABLE){
            inventory.remove(item);
            gainHealth(item.getHealthPoints());
            System.out.println("You gained " + item.healthPoints + " health\nYour health is now " + getHealth());
            return true;
        }
        System.out.println("Could not use " + item.getName());
        return false;
    }

    /**
     * Equips new armor into weapon slot, removes previously equipped armor
     * @param newArmor armor to equip
     * @return true if armor is successfully equipped, false otherwise
     */
    public boolean equipArmor(Item newArmor){
        if(newArmor.getType() == ItemType.ARMOR){
            if(armorSlot != null){
                if(RemoveArmor()){
                    System.out.println("Unequipped " + armorSlot.getName());
                }
                else{
                    System.out.println("Could not unequip current armor");
                    return false;
                }
            }
            this.armorSlot = newArmor;
            inventory.remove(newArmor);
            System.out.println("You equipped " + newArmor.getName() + " and gained " + newArmor.getDefensePercent() + "% health");
            return true;
        }
        System.out.println("Could not equip " + newArmor.getName() + " as armor");
        return false;
    }

    /**
     * Equips a new weapon into weapon slot, removes previously equipped weapon
     * @param newWeapon weapon to equip
     * @return true if weapon is successfully equipped, false otherwise
     */
    public boolean equipWeapon(Item newWeapon){
        if(newWeapon.getType() == ItemType.WEAPON){
            if(weaponSlot != null){
                if(RemoveWeapon()){
                    System.out.println("Unequipped " + weaponSlot.getName());
                }
                else{
                    System.out.println("Could not unequip current weapon");
                    return false;
                }
            }
            this.weaponSlot = newWeapon;
            inventory.remove(newWeapon);
            System.out.println("You equipped " + newWeapon.getName() + " and gained " + newWeapon.getAttackDamage() + "% attack");
            return true;
        }
        System.out.println("Could not equip " + newWeapon.getName() + " as weapon");
        return false;
    }
    
    /**
     * Unequips armor and stores in inventory
     * @return true if armor is removed, false if no armor equipped
     */
    public boolean RemoveArmor(){
        if(armorSlot != null){
            System.out.println("Unequipped " + armorSlot.getName());
            this.inventory.add(armorSlot);
            this.armorSlot = null;
            return true;
        }
        System.out.println("No armor equipped");
        return false;
    }

    /**
     * Unequips weapon and stores in inventory
     * @return true if weapon is removed, false if no weapon equipped
     */
    public boolean RemoveWeapon(){
        if(weaponSlot != null){
            System.out.println("Unequipped " + weaponSlot.getName());
            this.inventory.add(weaponSlot);
            this.weaponSlot = null;
            return true;
        }
        System.out.println("No weapon equipped");
        return false;
    }
    
    /**
     * Destroys an item in player's inventory
     * @param item item to destroy
     */
    public void destroyItem(Item item){
        System.out.println("Destroyed " + item.getName());
        inventory.remove(item);
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

    public void increaseGold(int amount){
        this.goldAmount += amount;
    }

    public void decreaseGold(int amount){
        this.goldAmount -= amount;
    }
    
}
