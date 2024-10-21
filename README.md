# BowlLabDB

## Pre-reqs

1. Install maven
2. Install docker
3. Start docker

## Commands (do in this order)

1. docker-compose down -v
2. mvn spring-boot:run

## endpoints needed:

### Order

1. create order
   - will be given the name of the customer who makes the order and a list of item ids
   - need to populate the order table and populate the OrderItems table
2. View all orders that are not completed
3. View all orders for the day (complete and not)
4. View all orders for a customer (all orders w/ a customer id)

### Reservations

1. Create reservations
2. View all reservations for a date range
   - have the request body take startDate and endDate
3. View all current customer reservations
   - reservations by a customer that are not in the past.

### Reviews

1. Create review
2. View all reviews
3. View all reviews written by a customer
