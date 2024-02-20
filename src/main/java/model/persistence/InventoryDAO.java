package model.persistence;

import java.io.IOException;
import java.util.Collection;

import model.Inventory;

public interface InventoryDAO {
    public Collection<Inventory> getAllInventories() throws IOException;

    public Inventory updateInventory(Inventory inventory) throws IOException;

    public Inventory newInventory(Inventory inventory) throws IOException;

    public Inventory deleteInventory(Inventory inventory) throws IOException;
}
