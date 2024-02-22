package model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a bag, which is what comprises the player character's inventory. 
 * In this implementation of the Composition Strategy, the Bag class is the implementation of the Component
 * that is composed into the Composite (Inventory) class.
 * A Bag allows for items to be added and removed as needed, and each bag has a set number of items it can hold. 
 * @author Noah Lago (ndl3389@rit.edu)
 */
public class Bag implements Container<Item>{
    /**Represents the number of items this bag can hold.  */
    @JsonProperty("size") private int size;
    /**Item array to store the Items currently held by this bag.  */
    @JsonProperty("items") private ArrayList<Item> items;
    /**A count of the number of Items currently held by this bag.  */
    @JsonProperty("totalItems") private int totalItems;

    public Bag(int size){
        this.size = size;
        this.items = new ArrayList<>();
        this.totalItems = 0;
    }
    
    /**
     * Allows a new Item to be added to the bag, as long as the bag is not full, and increments count of Items in this bag.
     * @param newItem - The new Item to be added to this bag. 
     * @exception throws IndexOutOfBoundsException if the bag is already full and an attempt is made to add a new Item to the bag. 
     */
    @Override
    public boolean add(Item newItem) throws IndexOutOfBoundsException {
        if(!isFull()){
            this.items.add(newItem);
            this.totalItems++;
            return true;
        }else{
            throw new IndexOutOfBoundsException("This bag is full. ");
        }
    }

    /**
     * Allows for a specific Item to be removed from the bag and decrements count of items in this Bag.
     * @param item - The Item to be removed. 
     */
    @Override
    public boolean remove(Item item) { //Potentially switch to unique item code given to each item?
        if(this.items.contains(item)){
            this.items.remove(item);
            this.totalItems--;
            return true;
        }
        return false;
    }

    /**
     * Returns the number of slots granted by carrying this Bag. 
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * Returns all of the items stored in this Bag. 
     */
    @Override
    public ArrayList<Item> items() {
        return this.items;
    }
    /**
     * Returns whether this Bag has reached it's maximum capacity. 
     */
    @Override
    public boolean isFull() {
        return this.totalItems == this.size;
    } 

    public int getTotalItems(){
        return this.totalItems;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }
}
