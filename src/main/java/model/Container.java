package model;

import java.util.ArrayList;

/**
 * This interface provides the method setup for the other classes that are a part of the 
 * Inventory subsystem, and represents the Component of the Compositon Strategy. 
 * @author Noah Lago (ndl3389@rit.edu)
 */
public interface Container<E>{
    /**Adds a new Item to the Container. */
    boolean add(Item newItem);
    /**Removes the designated Item, and returns whether removal was successful. */
    boolean remove(Item item);
    /**Returns the size of the Container (how many Items it can hold). */
    int getSize();
    /**Returns all Items held by this Container. */
    ArrayList<Item> items();
    /**Returns whether this Container's maximum capacity has been reached. */
    boolean isFull();
}
