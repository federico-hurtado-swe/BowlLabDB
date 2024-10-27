package DBMS.group6.BowlLabDB.order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
//import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import DBMS.group6.BowlLabDB.order.models.OrderItem;
import DBMS.group6.BowlLabDB.order.models.Order;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to convert database rows into Order objects
    private final RowMapper<Order> rowMapperOrder = (rs, rowNum) -> new Order(
            rs.getInt("id"),
            rs.getInt("ordered_by"),
            rs.getTimestamp("date_ordered"),
            rs.getFloat("total_price"),
            rs.getBoolean("order_complete")
    );

    private final RowMapper<OrderItem> rowMapperOrderItem = (rs, rowNum) -> new OrderItem(
            rs.getInt("item_id"),
            rs.getInt("order_id")
    );

    @SuppressWarnings("deprecation")
    public void createOrder(Integer custID, List<OrderItem> orderItems) {
        // SQL to insert a new order
        String sql = "INSERT INTO Orders (ordered_by, total_price, order_complete) VALUES (?, ?, ?) RETURNING id"; 

        // Calculate the total price based on the OrderItem list (assuming each OrderItem has a price field)
        float totalPrice = calculateTotalPrice(orderItems); // Implement calculateTotalPrice() if needed
        //boolean orderComplete = false;
    
        // Insert new order
        //jdbcTemplate.update(sqlOrder, custID, new Timestamp(System.currentTimeMillis()), totalPrice, orderComplete);
    
        // Retrieve the last inserted order ID (assuming auto-increment ID)
        Integer orderId = jdbcTemplate.queryForObject(sql, new Object[]{custID, totalPrice, false}, Integer.class);
    
        // SQL to insert items into the OrderItems table
        String sqlOrderItem = "INSERT INTO OrderItems (order_id, item_id) VALUES (?, ?)";
        for (OrderItem item : orderItems) {
            jdbcTemplate.update(sqlOrderItem, orderId, item.item_id());
        }
    }

    @SuppressWarnings("deprecation")
    private float calculateTotalPrice(List<OrderItem> orderItems) {
        String sql = "SELECT price FROM Menu WHERE id = ?";
        float totalPrice = 0.0f;
    
        for (OrderItem item : orderItems) {
            Float price = jdbcTemplate.queryForObject(sql, new Object[]{item.item_id()}, Float.class);
            if (price != null) {
                totalPrice += price;
            }
        }
        return totalPrice;
    }

    // Retrieve all unfinished orders
    public List<Order> findAllUnfinished() {
        String sql = "SELECT * FROM Orders WHERE order_complete = false";
        return jdbcTemplate.query(sql, rowMapperOrder);
    }

    // Retrieve orders by date
    @SuppressWarnings("deprecation")
    public List<Order> findByDate(LocalDate date) {

        //not exactly sure this is right
        LocalDate day = date;

    
        String sql = "SELECT * FROM Orders WHERE DATE(date_ordered) = ?";
        return jdbcTemplate.query(sql, new Object[]{day}, rowMapperOrder);
    }

    // Retrieve orders by customer
    @SuppressWarnings("deprecation")
    public List<Order> findByCustomer(Integer custID) {
        String sql = "SELECT * FROM Orders WHERE ordered_by = ?";
        return jdbcTemplate.query(sql, new Object[]{custID}, rowMapperOrder);
    }

    // Mark order as complete
    public void markComplete(Integer id) {
        String sql = "UPDATE Orders SET order_complete = true WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Check if order exists by ID
    @SuppressWarnings("deprecation")
    public boolean orderExistsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM Orders WHERE id = ?";
        List<Integer> count = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> rs.getInt(1));
        return !count.isEmpty() && count.get(0) > 0;
    }

    public boolean customerExistsById(int custID) {
        String sql = "SELECT COUNT(*) FROM Customers WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, custID);
        return count != null && count > 0;
    }
}