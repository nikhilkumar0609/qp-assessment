package com.grocery.booking.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.booking.app.entities.GroceryItem;
import com.grocery.booking.app.exceptions.ItemNotFoundException;
import com.grocery.booking.app.payloads.ApiResponse;
import com.grocery.booking.app.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add-item")
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem item) {
    	GroceryItem addedItem = adminService.addGroceryItem(item);
         return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @GetMapping("/view-items")
    public List<GroceryItem> viewGroceryItems() {
        return adminService.viewGroceryItems();
    }

    @DeleteMapping("/remove-item/{itemId}")
    public ResponseEntity<ApiResponse> removeGroceryItem(@PathVariable Long itemId) {
        adminService.removeGroceryItem(itemId);
        return new ResponseEntity<>(new ApiResponse("Item deleted Successfully", true), HttpStatus.OK);
    }

    @PutMapping("/update-item")
    public ResponseEntity<ApiResponse> updateGroceryItem(@RequestBody GroceryItem item) {
    	try {
    		adminService.updateGroceryItem(item);
            return new ResponseEntity<>(new ApiResponse("Grocery updated Successfully", true), HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("An error occurred while processing the request.", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/manage-inventory/{itemId}/{quantity}")
    public ResponseEntity<ApiResponse> manageInventory(@PathVariable Long itemId, @PathVariable int quantity) {
    	try {
            adminService.manageInventory(itemId, quantity);
            return new ResponseEntity<>(new ApiResponse("Inventory managed Successfully", true), HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("An error occurred while processing the request.", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}