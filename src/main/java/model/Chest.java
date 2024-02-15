package model;

import java.util.ArrayList;

public class Chest implements Container<Item>{
    public static final int MAX_ITEMS = 5;
    private int totalItems;
    private ArrayList<Item> items;

    public Chest(Item[] fillItems){
        if(fillItems.length > MAX_ITEMS){
            throw new IndexOutOfBoundsException("Too many items to fit in this chest. ");
        }
        this.items = new ArrayList<>();
        this.totalItems = fillItems.length;
    }

    @Override
    public boolean add(Item newItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public boolean remove(Item item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public int getSize() {
        return MAX_ITEMS;
    }

    @Override
    public ArrayList<Item> items() {
        return this.items;
    }

    @Override
    public boolean isFull() {
        return this.totalItems == MAX_ITEMS;
    }

    public boolean isEmpty() {
        return this.totalItems == 0;
    }
    
}
