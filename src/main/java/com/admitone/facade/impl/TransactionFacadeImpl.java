package com.admitone.facade.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admitone.facade.TransactionFacade;
import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;
import com.admitone.service.EventService;
import com.admitone.service.OrderService;
import com.admitone.service.UserService;

@Service
public class TransactionFacadeImpl implements TransactionFacade {
	@Autowired
	private UserService userService;
	@Autowired
	private EventService eventService;
	@Autowired
	private OrderService orderService;

	@Override
	public Order save(Order order) {
		return orderService.save(order);
	}

	@Override
	public Order findByUserAndEvent(User user, Event event) {
		return orderService.findByUserAndEvent(user, event);
	}

	@Override
	public Collection<Order> findByUser(User user) {
		return orderService.findByUser(user);
	}

	@Override
	public Collection<Order> findByEventIDRange(long start, long end) {
		return orderService.findByEventIDRange(start, end);
	}

	@Override
	public User findByUserName(String userName) {
		return userService.findByUserName(userName);
	}

	@Override
	public Event findOne(long id) {
		return eventService.findOne(id);
	}

}
