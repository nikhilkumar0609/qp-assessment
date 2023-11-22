package com.grocery.booking.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grocery.booking.app.entities.GroceryItem;
import com.grocery.booking.app.entities.Order;
import com.grocery.booking.app.exceptions.InsufficientInventoryException;
import com.grocery.booking.app.exceptions.InvalidItemException;
import com.grocery.booking.app.payloads.ApiResponse;
import com.grocery.booking.app.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/view-items")
    public List<GroceryItem> viewGroceryItems() {
        return userService.viewGroceryItems();
    }

    @PostMapping("/book-items")
    public ResponseEntity<?> bookGroceryItems(@RequestBody List<Long> itemIds) {
        try {
            Order order = userService.bookGroceryItems(itemIds);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (InvalidItemException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (InsufficientInventoryException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("An error occurred while processing the request.", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}