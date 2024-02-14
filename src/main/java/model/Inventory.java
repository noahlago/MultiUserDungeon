package model;

public class Inventory implements Container<Container<Item>>{
    private int size;
    private int totalItems;
    private static final int MAX_BAGS = 6;
    private Container<Item>[] bags;
    private int numBags;

    @SuppressWarnings("unchecked")
    public Inventory(){
        this.bags = new Container[MAX_BAGS];
        this.size = 0;
        this.totalItems = 0;
        this.numBags = 0;
    }

    @Override
    public void add(Item newItem) {
        //add item to a non-full bag
    }

    public void addBag(Container<Item> newBag){
        if(this.numBags < MAX_BAGS){
            this.bags[numBags] = newBag;
            numBags++;
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
            }
        }

        //UPDATE INVENTORY SIZE
    }

    @Override
    public Container<Item> remove(int index) {
        Container<Item> removed = this.bags[index];
        this.bags[index] = null;
        return removed;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Item[] items() {
        Item[] allItems = new Item[this.size];
        int index = 0;
        for(Container<Item> bag : this.bags){
            for(Item item : bag.items()){
                if(item != null){
                    allItems[index] = item;
                    index++;
                }
            }
        }
        return allItems;
    }

    public Container<Item>[] children(){
        return this.bags;
    }

    public int totalItems(){
        return this.totalItems;
    }
    
}
