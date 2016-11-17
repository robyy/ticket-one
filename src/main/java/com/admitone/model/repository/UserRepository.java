package com.admitone.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admitone.model.entity.User;

/**
 * A DAO for the entity User is simply created by extending the JpaRepository
 * interface provided by Spring Data. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * You don't need to implement those methods, and moreover it is
 * possible create new query methods working only by defining their signature.
 * 
 * @author yyan
 *
 */
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// userName is defined unique in the entity
	User findByUserName(String userName);
}
