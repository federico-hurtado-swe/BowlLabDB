package DBMS.group6.BowlLabDB.order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
//import java.util.Optional;

import org.springframework.stereotype.Service;

import DBMS.group6.BowlLabDB.order.models.Order;
import DBMS.group6.BowlLabDB.order.models.OrderItem;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Integer custID, List<OrderItem> orderItems) {
        if (orderRepository.customerExistsById(custID)) {
            orderRepository.createOrder(custID, orderItems);
        } else {
            throw new IllegalArgumentException("Customer id: " + custID + " not found.");
        }
    }

    // Retrieve all unfinished orders
    public List<Order> findAllUnfinished() {
        return orderRepository.findAllUnfinished();
    }

    // Retrieve orders by date
    public List<Order> findByDate(LocalDate date) {
        return orderRepository.findByDate(date);
    }

    // Retrieve orders by customer
    public List<Order> findByCustomer(Integer custID) {
        return orderRepository.findByCustomer(custID);
    }

    // Mark order as complete
    public void markComplete(Integer id) {
        if (orderRepository.orderExistsById(id)) {
            orderRepository.markComplete(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
    }
}