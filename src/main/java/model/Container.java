package model;

public interface Container<E>{
    void add(Item newItem);
    E remove(int index);
    int getSize();
    Item[] items();
}
