package model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class provides a concrete implementation of the Container interface, 
 * which represents a player character's Inventory, with all items the user carries. 
 * The user's inventory consists of a composition of (up to) 6 bags,
 * while allowing the user to add new bags, or replace current bags with larger ones. 
 * This class is a part of an implementation of the Composition Strategy as the Composite class, with minimal deviation.
 * @author Noah Lago (ndl3389@rit.edu)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory implements Container<Bag>{
    @JsonProperty("size") private int size;
    @JsonProperty("totalItems") private int totalItems;
    @JsonProperty("MAX_BAGS") private static final int MAX_BAGS = 6;
    @JsonProperty("bags") private Bag[] bags;
    @JsonProperty("numBags") private int numBags;

    public Inventory(){
        this.bags = new Bag[MAX_BAGS];
        this.size = 0;
        this.totalItems = 0;
        this.numBags = 0;
    }

    /**
     * Adds a new Item to a Bag in this Inventory, as long as at least one Bag has at least one open slot. 
     * @param newItem the new Item to be added to this Inventory. 
     * @return whether the new Item was successfully added to this Inventory. 
     */
    @Override
    public boolean add(Item newItem) {
        if(!isFull()){
            for(Container<Item> bag : this.bags){
                if(!bag.isFull()){
                    bag.add(newItem);
                    this.totalItems++;
                    return true;
                }
            }        
        }
        return false;
    }

    /**
     * Adds a new Bag to the player's Inventory if they have less than the maximum number of Bags. 
     * If the Inventory has reached the maximum number of bags, the Bag with the smallest capacity is removed. 
     * If the Inventory has the maximum number of bags, and the new Bag is smaller than all current Bags, no change is made.
     * @param newBag the new Bag to attempt to add to the Inventory. 
     * @return whether the new Bag was successfully added to this Inventory. 
     */
    public boolean addBag(Bag newBag){
        if(this.numBags < MAX_BAGS){
            this.bags[numBags] = newBag;
            numBags++;
            updateSize();
            return true;
        }else{
            Container<Item> smallestBag = newBag;
            int smallestIndex = -1;
            for(int i = 0; i < numBags; i++){
                if(this.bags[i].getSize() < smallestBag.getSize()){
                    smallestBag = bags[i];
                    smallestIndex = i;
                }
            }
            if(smallestIndex != -1){
                this.bags[smallestIndex] = newBag;
                updateSize();
                return true;
            }
            return false;
        }
    }

    /**
     * Private helper function to update the size field for the player's inventory when necessary. 
     */
    private void updateSize(){
        int totalSize = 0;
        for(Container<Item> bag : this.bags){
            if(bag != null){
                this.totalItems += bag.getSize();
            }
        }
        this.size = totalSize;
    }

    /**
     * Attempts to remove the designated Item from the player's Inventory, by iterating through all Bags and attempting to remove from each one until successful or out of Bags. 
     * @return whether the Item was successfully found in the Bags and removed. 
     */
    @Override
    public boolean remove(Item item) {
        for(Container<Item> bag : this.bags){
            if(bag.remove(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the maximum combined capacity of all Bags held in this Inventory. 
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * Iterates through each Bag held in this Inventory, to retrieve all Items from each bag and combine them into one ArrayList.
     * @return the ArrayList containing all Items from all Bags in the player's inventory. 
     */
    @Override
    public ArrayList<Item> items() {
        ArrayList<Item> allItems = new ArrayList<>();
        for(Container<Item> bag : this.bags){
            for(Item item : bag.items()){
                if(item != null){
                    allItems.add(item);
                }
            }
        }
        return allItems;
    }

    /**
     * @return all Bags contained in this Inventory
     */
    public Container<Item>[] children(){
        return this.bags;
    }

    /**
     * @return whether this Inventory has reached maximum capacity. 
     */
    @Override
    public boolean isFull() {
        return this.totalItems == this.size;
    }

    public Container<Item>[] getBags() {
        return bags;
    }

    public int getTotalItems(){
        return this.totalItems;
    }

    public int getNumBags(){
        return this.numBags;
    }

    @Override
    public String toString(){
        String inv = "Inventory: \n";
        for(Container<Item> bag : bags){
            if(bag != null){
                inv += "( ";
                for(Item item : bag.items()){
                    inv += "{" + item + "}";
                }
                inv += " )\n";
            }
        }

        return inv;
    }
    
}
