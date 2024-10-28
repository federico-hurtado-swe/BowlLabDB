package DBMS.group6.BowlLabDB.order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import DBMS.group6.BowlLabDB.customer.CustomerRepository;
import DBMS.group6.BowlLabDB.customer.models.Customer;
import DBMS.group6.BowlLabDB.menu.MenuRepository;
import DBMS.group6.BowlLabDB.menu.models.MenuItem;
import DBMS.group6.BowlLabDB.order.models.Order;
import DBMS.group6.BowlLabDB.order.models.OrderItem;
import DBMS.group6.BowlLabDB.order.models.OrderResponse;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
            MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
    }

    public void createOrder(Integer custID, List<OrderItem> orderItems) {
        if (orderRepository.customerExistsById(custID)) {
            orderRepository.createOrder(custID, orderItems);
        } else {
            throw new IllegalArgumentException("Customer id: " + custID + " not found.");
        }
    }

    // Retrieve all unfinished orders
    public List<OrderResponse> findAllUnfinished() {
        List<Order> orders = orderRepository.findAllUnfinished();
        return this.gatherOrderData(orders);

    }

    // Retrieve orders by date
    public List<OrderResponse> findByDate(LocalDate date) {
        List<Order> orders = orderRepository.findByDate(date);

        return this.gatherOrderData(orders);
    }

    // Retrieve orders by customer
    public List<OrderResponse> findByCustomer(Integer custID) {
        List<Order> orders = orderRepository.findByCustomer(custID);

        return this.gatherOrderData(orders);
    }

    // Mark order as complete
    public void markComplete(Integer id) {
        if (orderRepository.orderExistsById(id)) {
            orderRepository.markComplete(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
    }

    private List<OrderResponse> gatherOrderData(List<Order> orders) {

        if (orders == null) {
            return null;
        }

        List<OrderResponse> fullOrders = new ArrayList<>();

        for (Order order : orders) {

            // get customer info from the order
            int customerId = order.ordered_by();
            Customer customer = customerRepository.getCustomerById(customerId);
            String customerName;
            String customerEmail;
            if (customer != null) {
                customerName = customer.firstName() + " " + customer.lastName();
                customerEmail = customer.email();
            } else {
                customerName = "Guest";
                customerEmail = "N/A";
            }

            // get list of items from the order
            List<Integer> orderItemIds = orderRepository.getOrderItemsByOrderId(order.id());
            System.out.println("order items ids: " + orderItemIds.toString());

            List<MenuItem> menuItems = new ArrayList<>();
            for (Integer itemId : orderItemIds) {
                Optional<MenuItem> itemFound = menuRepository.findById(itemId);
                if (itemFound.isPresent()) {
                    menuItems.add(itemFound.get());
                } else {
                    System.out.println("Could not find item");
                }
            }

            // create orderresponse object
            OrderResponse currOrder = new OrderResponse(order.id(), customerName, order.date_ordered(),
                    order.total_price(), order.order_complete(), menuItems);

            fullOrders.add(currOrder);
        }

        return fullOrders;
    }
}