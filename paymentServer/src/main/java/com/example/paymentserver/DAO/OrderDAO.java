package com.example.paymentserver.DAO;

import com.example.paymentserver.Entity.OrderEntity;

public interface OrderDAO {
    public void createOrder(OrderEntity orderEntity);
    public OrderEntity readOrder(String orderId);
    public void deleteOrder(OrderEntity orderEntity);
}
