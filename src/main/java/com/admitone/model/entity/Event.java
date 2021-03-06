package com.admitone.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Event should have a qty filed to represent how many tickets can be purhcased
 * for this specific show, and when user purchase, cancel, exchange actions
 * involve this show, will update the qty accordingly. But for the sake of
 * simplicity, that every show has an infinite capacity, also skip this field.
 *
 * @author yyan
 */

@Entity
@Table(name = "events")
public class Event implements Serializable {
	// Notice that Hibernate generates columns in alphabetical order

	// An autogenerated id (unique for each user in the db)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	// Design for the future
	// private double cost;

	// private String location;

	// private LocalDateTime eventTime;

	@NotBlank
	@Column(unique = true)
	private String eventName;

	public Event() {
	}

	public Event(long id) {
		this.id = id;
	}

	public Event(String eventName) {
		this.eventName = eventName;
	}
	
	public Event(long id, String eventName) {
		this.id = id;
		this.eventName = eventName;
	}

	// public Event(double cost, String location, String eventName,
	// LocalDateTime eventTime) {
	// this.cost = cost;
	// this.location = location;
	// this.eventName = eventName;
	// this.eventTime = eventTime;
	// }

	// public double getCost() {
	// return cost;
	// }
	//
	// public void setCost(double cost) {
	// this.cost = cost;
	// }

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	// public LocalDateTime getEventTime() {
	// return eventTime;
	// }
	//
	// public void setEventTime(LocalDateTime eventTime) {
	// this.eventTime = eventTime;
	// }
	//
	// public String getLocation() {
	// return location;
	// }
	//
	// public void setLocation(String location) {
	// this.location = location;
	// }
	//
	public long getId() {
		return id;
	}

}
