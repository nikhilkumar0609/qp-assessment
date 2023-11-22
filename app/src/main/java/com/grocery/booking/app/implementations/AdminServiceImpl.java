package com.grocery.booking.app.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.booking.app.entities.GroceryItem;
import com.grocery.booking.app.exceptions.ItemNotFoundException;
import com.grocery.booking.app.repositories.GroceryItemRepository;
import com.grocery.booking.app.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
    private GroceryItemRepository groceryItemRepository;

    @Override
    public GroceryItem addGroceryItem(GroceryItem item) {
        return groceryItemRepository.save(item);
    }

    @Override
    public List<GroceryItem> viewGroceryItems() {
        return groceryItemRepository.findAll();
    }

    @Override
    public void removeGroceryItem(Long itemId) {
        groceryItemRepository.deleteById(itemId);
    }

    @Override
    public GroceryItem updateGroceryItem(GroceryItem item) {
    	long itemId = item.getId();
        Optional<GroceryItem> existingItem = groceryItemRepository.findById(itemId);

        if (existingItem.isPresent()) {
            GroceryItem updatedItem = existingItem.get();
            updatedItem.setName(item.getName());
            updatedItem.setPrice(item.getPrice());
            updatedItem.setInventory(item.getInventory());
            return groceryItemRepository.save(updatedItem);
        } else {
        	throw new ItemNotFoundException("Grocery item with ID " + itemId + " not found");
        }
    }

    @Override
    public void manageInventory(Long itemId, int quantity) {
        Optional<GroceryItem> existingItem = groceryItemRepository.findById(itemId);

        existingItem.ifPresentOrElse(
            item -> {
                item.setInventory(item.getInventory() + quantity);
                groceryItemRepository.save(item);
            },
            () -> {
                throw new ItemNotFoundException("Grocery item with ID " + itemId + " not found");
            }
        );
    }
   
}