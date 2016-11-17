package com.admitone.model.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	Order findByUserAndEvent(User user, Event event);
	Collection<Order> findByUser(User user);
	
	@Query(value = "SELECT * FROM ORDERS WHERE EVENT_ID >= ?1 and EVENT_ID <= ?2", nativeQuery = true)
	Collection<Order> findByEventIDRange(long start, long end);
}
