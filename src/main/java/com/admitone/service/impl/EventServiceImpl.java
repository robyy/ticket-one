package com.admitone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admitone.model.entity.Event;
import com.admitone.model.repository.EventRepository;
import com.admitone.service.EventService;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event findOne(long id) {
		return eventRepository.findOne(id);
	}
}
