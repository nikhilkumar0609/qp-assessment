package com.grocery.booking.app.services;

import java.util.List;

import com.grocery.booking.app.entities.GroceryItem;

public interface AdminService {

    GroceryItem addGroceryItem(GroceryItem item);

    List<GroceryItem> viewGroceryItems();

    void removeGroceryItem(Long itemId);

    GroceryItem updateGroceryItem(GroceryItem item);

    void manageInventory(Long itemId, int quantity);
}