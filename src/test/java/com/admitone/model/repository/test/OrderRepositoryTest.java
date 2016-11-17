package com.admitone.model.repository.test;
/*
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;
import com.admitone.model.repository.OrderRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;
    
    @Test
    public void testFindByName() {
    	User user = new User("qq@qq.com", "*");
    	Event event = new Event("qq music");
    	entityManager.persist(user);
    	entityManager.persist(event);
        entityManager.persist(new Order(user, event, 11));

        List<Order> order = (List)orderRepository.findByEventIDRange(99, 100);
        assertEquals("qq music", order.get(0).getEvent().getEventName());
        assertEquals("qq@qq.com", order.get(0).getUser().getUserName());
        assertEquals(11, order.get(0).getQty());
    }
}
*/