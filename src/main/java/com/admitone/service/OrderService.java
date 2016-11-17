package com.admitone.service;

import java.util.Collection;

import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;

public interface OrderService {
	Order save(Order order);
	Order findByUserAndEvent(User user, Event event);
	Collection<Order> findByUser(User user);
	Collection<Order> findByEventIDRange(long start, long end);
}
