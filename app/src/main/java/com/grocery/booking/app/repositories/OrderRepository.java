package com.grocery.booking.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grocery.booking.app.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}