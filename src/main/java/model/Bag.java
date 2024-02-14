package model;

public class Bag implements Container<Item>{
    private int size;
    private Item[] items;
    private int totalItems;

    public Bag(int size){
        this.size = size;
        this.items = new Item[size];
        this.totalItems = 0;
    }
    
    @Override
    public void add(Item newItem) throws IndexOutOfBoundsException {
        if(this.totalItems < size){
            this.items[totalItems] = newItem;
            totalItems++;
        }else{
            throw new IndexOutOfBoundsException("This bag is full. ");
        }
    }

    @Override
    public Item remove(int index) {
        try{
            Item removed = this.items[index];
            this.items[index] = null;
            return removed;
        }catch(IndexOutOfBoundsException outOfBounds){
            System.out.println("Invalid Bag index. ");
            return null;
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Item[] items() {
        return this.items;
    } 
}
