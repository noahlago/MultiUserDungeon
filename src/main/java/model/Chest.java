package model;

import java.util.ArrayList;

/**
 * This class represents a Chest, which can be found on occasional tiles, scattered throughout the rooms. 
 * Each Chest contains 1-5 items initially, which the Player Character can loot form the Chest, adding them to their inventory. 
 * The Player Character could also add their items to the Chest, allowing them to clear out space in their inventory. 
 * In the Composite Strategy, this class is a Leaf implementation of the Container interface.
 * @author Noah Lago (ndl3389@rit.edu)
 */
public class Chest implements Container<Item>{
    /**Represents the maximum number of items a Chest can store.  */
    public static final int MAX_ITEMS = 5;
    /**Maintains a count of the current # of items in this Chest.  */
    private int totalItems;
    /**Stores the items in the chest in an ArrayList, for easy adding and removing. */
    private ArrayList<Item> items;

    public Chest(Item[] fillItems){
        if(fillItems.length > MAX_ITEMS){ //Prevents an attempt to add more than the max # of Items to a Chest. 
            throw new IndexOutOfBoundsException("Too many items to fit in this chest. ");
        }
        this.items = new ArrayList<>();
        for(Item item : fillItems){
            this.items.add(item);
        }
        this.totalItems = items.size();
    }

    /**
     * Allows a new Item to be added to the Chest, as long as the Chest isn't full. 
     * @param newItem - The new Item to be added to this Chest.
     * @exception throws IndexOutOfBoundsException if the Chest is already full of Items. 
     */
    @Override
    public boolean add(Item newItem) {
        if(!isFull()){
            this.items.add(newItem);
            totalItems++;
            return true;
        }else{
            throw new IndexOutOfBoundsException("This Chest is full. ");
        }
    }

    /**
     * Allows for a specific Item to be removed from the Chest, decrementing the count of Items in this Chest. 
     * @param item - The Item to be removed. 
     */
    @Override
    public boolean remove(Item item) {
        if(this.items.contains(item)){
            this.items.remove(item);
            this.totalItems--;
            return true;
        }
        return false;
    }

    /**
     * Returns the max # of Items this Chest can hold. 
     */
    @Override
    public int getSize() {
        return MAX_ITEMS;
    }

    /**
     * Returns all items stored in this Chest. 
     */
    @Override
    public ArrayList<Item> items() {
        return this.items;
    }

    /**
     * Returns whether this chest has reached max capacity. 
     */
    @Override
    public boolean isFull() {
        return this.totalItems == MAX_ITEMS;
    }

    /**
     * Returns whether this chest is empty. 
     */
    public boolean isEmpty() {
        return this.totalItems == 0;
    }
    
}
