package DBMS.group6.BowlLabDB.order.models;

import java.sql.Timestamp;
import java.util.List;

import DBMS.group6.BowlLabDB.menu.models.MenuItem;

public record OrderResponse(
        Integer id,
        String customerName,
        Timestamp dateOrdered,
        Float totalPrice,
        Boolean orderComplete,
        List<MenuItem> orderItems) {
}
