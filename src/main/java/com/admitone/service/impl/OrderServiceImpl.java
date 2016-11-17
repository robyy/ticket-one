package com.admitone.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;
import com.admitone.model.repository.OrderRepository;
import com.admitone.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Order findByUserAndEvent(User user, Event event) {
		return orderRepository.findByUserAndEvent(user, event);
	}

	@Override
	public Collection<Order> findByUser(User user) {
		return orderRepository.findByUser(user);
	}

	@Override
	public Collection<Order> findByEventIDRange(long start, long end) {
		return orderRepository.findByEventIDRange(start, end);
	}

}
