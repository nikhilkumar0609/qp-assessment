package com.grocery.booking.app.services;

import java.util.List;

import com.grocery.booking.app.entities.GroceryItem;
import com.grocery.booking.app.entities.Order;

public interface UserService {

    List<GroceryItem> viewGroceryItems();

    Order bookGroceryItems(List<Long> itemIds);
}
