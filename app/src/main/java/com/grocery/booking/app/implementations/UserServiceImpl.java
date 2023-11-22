package com.grocery.booking.app.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.booking.app.entities.GroceryItem;
import com.grocery.booking.app.entities.Order;
import com.grocery.booking.app.exceptions.InsufficientInventoryException;
import com.grocery.booking.app.exceptions.InvalidItemException;
import com.grocery.booking.app.repositories.GroceryItemRepository;
import com.grocery.booking.app.repositories.OrderRepository;
import com.grocery.booking.app.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private GroceryItemRepository groceryItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<GroceryItem> viewGroceryItems() {
		return groceryItemRepository.findAll();
	}

	@Override
	public Order bookGroceryItems(List<Long> itemIds) {
		List<GroceryItem> itemsToBook = groceryItemRepository.findAllById(itemIds);

	    if (itemsToBook.size() != itemIds.size()) {
	        throw new InvalidItemException("One or more items are invalid.");
	    }

	    // Check if inventory is sufficient for each item
	    for (GroceryItem item : itemsToBook) {
	        if (item.getInventory() <= 0) {
	            throw new InsufficientInventoryException("Insufficient inventory for item with ID: " + item.getId());
	        }
	    }

	    // Create an order with the selected items
	    Order order = new Order();
	    order.setItems(itemsToBook);

	    // Update inventory after booking
	    for (GroceryItem item : itemsToBook) {
	        item.setInventory(item.getInventory() - 1);
	        groceryItemRepository.save(item);
	    }

	    return orderRepository.save(order);
	}

   
}