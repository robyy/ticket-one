package com.admitone.controller.data;

import java.io.Serializable;

/**
 * For front end view use
 * 
 * @author yyan
 *
 */
public class OrderHistoryData implements Serializable{
	private long orderID;
	private String userName;
	private String eventName;
	private long eventID;
	private int qty;
	private boolean approveStatus;
	
	public OrderHistoryData(long orderID, String userName, long eventID, String eventName, int qty, boolean approveStatus) {
//		super();
		this.orderID = orderID;
		this.userName = userName;
		this.eventID = eventID;
		this.eventName = eventName;
		this.qty = qty;
		this.approveStatus = approveStatus;
	}

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public boolean isApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(boolean approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
