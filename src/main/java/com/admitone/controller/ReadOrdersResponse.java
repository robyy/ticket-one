package com.admitone.controller;

import java.util.List;

import com.admitone.controller.data.OrderHistoryData;

public class ReadOrdersResponse extends CommonResponse {
	private List<OrderHistoryData> orders;
	
	public ReadOrdersResponse(String commonMessage) {
		super(commonMessage);
	}

	public ReadOrdersResponse(String commonMessage, List<OrderHistoryData> orders) {
		super(commonMessage);
		this.orders = orders;
	}

	public List<OrderHistoryData> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderHistoryData> orders) {
		this.orders = orders;
	}

}
