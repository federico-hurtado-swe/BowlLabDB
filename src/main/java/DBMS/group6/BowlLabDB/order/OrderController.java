package DBMS.group6.BowlLabDB.order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import DBMS.group6.BowlLabDB.order.models.Order;
import DBMS.group6.BowlLabDB.order.models.OrderItem;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // /find/unfinished
    @GetMapping("/find/unfinished")
    List<Order> findAllUnfinished() {
        return this.orderService.findAllUnfinished();
    }

    // /find/{date}
    @GetMapping("/findByDate/{date}")
    List<Order> findByDate(@PathVariable LocalDate date) {
        return this.orderService.findByDate(date);
    }

    // /find/{custId}
    @GetMapping("/findByCustomer/{custId}")
    List<Order> findByCustomer(@PathVariable Integer custId) {
        return this.orderService.findByCustomer(custId);
    }

    // /update/{orderId}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/{orderId}")
    void markComplete(@PathVariable Integer orderId) {
        orderService.markComplete(orderId);
    }

    // /create
    // {
    //     "custID": 1,
    //     "orderItems": [
    //         { "item_id": 1 },
    //         { "item_id": 2 }
    //     ]
    // }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    void createOrder(@Valid @RequestBody Map<String, Object> orderRequest) {
        Integer custID = (Integer) orderRequest.get("custID");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> orderItemsData = (List<Map<String, Object>>) orderRequest.get("orderItems");

        List<OrderItem> orderItems = orderItemsData.stream()
                .map(data -> new OrderItem((Integer) data.get("item_id"), null)) // Assuming item_id is provided
                .toList();

        this.orderService.createOrder(custID, orderItems);
    }

}
