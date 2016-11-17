package com.admitone.service;

import com.admitone.model.entity.User;

public interface UserService {
	// userName is defined unique in the entity
	User findByUserName(String userName);
}
