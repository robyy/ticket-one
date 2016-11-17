package com.admitone.facade;

import java.util.Collection;

import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;

public interface TransactionFacade {
	// order service
	Order save(Order order);
	Order findByUserAndEvent(User user, Event event);
	Collection<Order> findByUser(User user);
	Collection<Order> findByEventIDRange(long start, long end);
	
	// user service
	User findByUserName(String userName);
	
	// event service
	Event findOne(long id);
}
