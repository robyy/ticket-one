package com.admitone.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.admitone.controller.data.OrderHistoryData;
import com.admitone.facade.TransactionFacade;
import com.admitone.model.entity.Event;
import com.admitone.model.entity.Order;
import com.admitone.model.entity.User;

/**
 * Assumption: Since in current design, event only has id and eventName 2
 * fields, it doesn't have event time and location, so when the same user
 * purchase the same event more than 1 time, only update the qty(increment) for
 * the existing order.
 * 
 * @author yyan
 *
 */

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
	@Autowired
	private TransactionFacade transactionFacade;

	private static final String PURCHASE_OK = "Order successfully purchased";
	private static final String UPDATE_OK = "Order successfully updated";
	private static final String INVALID_USER = "Invalid user name input or user does no exist";
	private static final String INVALID_EVENT = "Invalid event id input or event doest not exist";
	private static final String INVALID_QTY = "Must purchase or cancle at least 1 ticket";
	private static final String INVALID_ORDER = "The order tried to be cancelled does not exist";
	private static final String INVALID_MORE_QTY = "Can not cancel or exchange more tickets than you have ordered";
	private static final String CANCEL_OK = "Order successfully cancelled";
	private static final String READ_ORDER_HISTORY_OK = "Order history successfully read";
	private static final String INVALID_SOURCE_EVENT = "Invalid source event id";
	private static final String INVALID_TARGET_EVENT = "Invalid target event id";
	private static final String EXCHANGE_OK = "Order successfully exchanged";
	private static final String INVALID_EVENT_RANGE = "Invalid event id range, make sure the small one goes first";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/purchase")
	public ResponseEntity<CommonResponse> purchase(@RequestParam(value = "qty") int qty,
			@RequestParam(value = "eventid") long eventID, @RequestParam(value = "username") String userName) {
		String message;

		User user = transactionFacade.findByUserName(userName);
		Event event = transactionFacade.findOne(eventID);
		
		if (user == null) {
			// TODO refactor
//			create a validateInput method for every order action method
//			return a ResponseEntity<?>,check if the CommonResponse.getMessage equals INPUT_OK?
			
//			Then move them out to Utils or Service class.
			
			
			return invalidUserResponse(userName);
		}

		if (event == null) {
			return invalidEventResponse(eventID);
		}

		if (qty <= 0) {
			return invalidQtyResponse(qty);
		}

		Order order = transactionFacade.findByUserAndEvent(user, event);
		// existing order(same user and event)
		if (order != null) {
			order.setQty(qty + order.getQty());

			transactionFacade.save(order);
			logger.info("order successfully updated, userName: " + user.getUserName() + " , event id: +"
					+ event.getEventName());

			message = UPDATE_OK;
			CommonResponse orderReseponse = new CommonResponse(message);
			return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.OK);
		}

		order = new Order(user, event, qty);
		try { // may not need try catch here.
			transactionFacade.save(order);
			logger.info("order successfully purchased, userName: " + user.getUserName() + " , event id: +"
					+ event.getEventName());
		} catch (Exception ex) {
			logger.error(ex.toString());
		}

		message = PURCHASE_OK;
		CommonResponse orderReseponse = new CommonResponse(message);
		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.OK);
	}

	private ResponseEntity<CommonResponse> invalidQtyResponse(int qty) {
		String message;
		logger.debug("invalid qty input: " + qty);

		message = INVALID_QTY;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<CommonResponse> invalidEventResponse(long eventID) {
		String message;
		logger.debug("invalid event id input or event not existed: " + eventID);

		message = INVALID_EVENT;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<CommonResponse> invalidUserResponse(String userName) {
		String message;
		logger.debug("invalid user name input or user not existed: " + userName);

		message = INVALID_USER;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}

	@RequestMapping("/cancellation")
	public ResponseEntity<CommonResponse> cancel(@RequestParam(value = "qty") int qty,
			@RequestParam(value = "eventid") long eventID, @RequestParam(value = "username") String userName) {
		String message;

		User user = transactionFacade.findByUserName(userName);
		if (user == null) {
			return invalidUserResponse(userName);
		}

		Event event = transactionFacade.findOne(eventID);
		if (event == null) {
			return invalidEventResponse(eventID);
		}

		if (qty <= 0) {
			return invalidQtyResponse(qty);
		}

		Order order = transactionFacade.findByUserAndEvent(user, event);

		// order doesn't exist, no such event + user combination
		if (order == null) {
			return invalidOrderResponse(user, event);
		}

		if (order.getQty() < qty) {
			return invalidMoreQtyResponse();
		}

		order.setQty(order.getQty() - qty);
		transactionFacade.save(order);
		logger.info("order successfully cancelled");

		message = CANCEL_OK;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.OK);
	}

	private ResponseEntity<CommonResponse> invalidMoreQtyResponse() {
		String message;
		logger.debug("qty tried to cancel or exchange is greater than the qty in the order!");

		message = INVALID_MORE_QTY;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<CommonResponse> invalidOrderResponse(User user, Event event) {
		String message;
		logger.debug("order doesn't exist, no such event: " + event.getEventName() + "user: " + user.getUserName()
				+ "combination!");

		message = INVALID_ORDER;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}

	@RequestMapping("/exchange")
	public ResponseEntity<CommonResponse> exchange(@RequestParam(value = "qty") int qty, @RequestParam(value = "eventidf") long eventIDFrom,
			@RequestParam(value = "eventidt") long eventIDTo, @RequestParam(value = "username") String userName) {
		String message;
		User user = transactionFacade.findByUserName(userName);
		if (user == null) {
			return invalidUserResponse(userName);
		}

		Event eventFrom = transactionFacade.findOne(eventIDFrom);
		if (eventFrom == null) {
			return invalidSourceEventResponse(eventIDFrom);
		}

		Event eventTo = transactionFacade.findOne(eventIDTo);
		if (eventTo == null) {
			return invalidTargetEventResponse(eventIDTo);
		}

		if (qty <= 0) {
			return invalidQtyResponse(qty);
		}

		Order sourceOrder = transactionFacade.findByUserAndEvent(user, eventFrom);

		// order doesn't exist, no such event + user combination
		if (sourceOrder == null) {
			logger.debug("source order doesn't exist, no such event: " + eventFrom.getEventName() + "user: "
					+ user.getUserName() + "combination!");
			
			message = INVALID_ORDER;
			CommonResponse orderReseponse = new CommonResponse(message);

			return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
		}

		if (sourceOrder.getQty() < qty) {
			return invalidMoreQtyResponse();
		}

		Order targetOrder = transactionFacade.findByUserAndEvent(user, eventTo);

		// user has already purchased the target event
		if (targetOrder != null) {
			sourceOrder.setQty(sourceOrder.getQty() - qty);
			targetOrder.setQty(targetOrder.getQty() + qty);
			transactionFacade.save(sourceOrder);
			transactionFacade.save(targetOrder);

			logger.info("order successfully updated for both source and target orders");

			message = EXCHANGE_OK;
			CommonResponse orderReseponse = new CommonResponse(message);

			return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.OK);
		}

		// user has not purchased the other event
		targetOrder = new Order(user, eventTo, qty);
		sourceOrder.setQty(sourceOrder.getQty() - qty);
		transactionFacade.save(sourceOrder);
		transactionFacade.save(targetOrder);

		logger.info("order successfully updated for source order and new order created for target event");

		message = EXCHANGE_OK;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.OK);
	}

	private ResponseEntity<CommonResponse> invalidTargetEventResponse(long eventIDTo) {
		String message;
		logger.debug("invalid target event id input or target event not existed: " + eventIDTo);
		
		message = INVALID_TARGET_EVENT;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<CommonResponse> invalidSourceEventResponse(long eventIDFrom) {
		String message;
		logger.debug("invalid source event id input or source event not existed: " + eventIDFrom);
		
		message = INVALID_SOURCE_EVENT;
		CommonResponse orderReseponse = new CommonResponse(message);

		return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/{eIDStart}/{eIDEnd}", method = RequestMethod.GET)
	public ResponseEntity<?> readOrdersByEventIDRange(@PathVariable(value = "eIDStart") long eIDStart, @PathVariable(value = "eIDEnd") long eIDEnd) {
		String message;
		
		if(eIDStart > eIDEnd) {
			message = INVALID_EVENT_RANGE;
			
			CommonResponse orderReseponse = new CommonResponse(message);
			return new ResponseEntity<CommonResponse>(orderReseponse, HttpStatus.NOT_FOUND);
		}
		
		Collection<Order> orders =  transactionFacade.findByEventIDRange(eIDStart, eIDEnd);
		
		List<OrderHistoryData> listOfOrderHistoryData = createOrderHistoryData(orders);
		
		ReadOrdersResponse readOrderResponse = new ReadOrdersResponse(READ_ORDER_HISTORY_OK, listOfOrderHistoryData);
		return new ResponseEntity<ReadOrdersResponse>(readOrderResponse, HttpStatus.OK);
	}
	

	/**
	 * Display order history for a given customer
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{userName:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> readOrdersByUserName(@PathVariable(value = "userName") String userName) {
		String message;
		User user = transactionFacade.findByUserName(userName);

		if (user == null) {
			return invalidUserResponse(userName);
		}

		Collection<Order> orders = transactionFacade.findByUser(user);

		// getOrderResponse.setOrders(orders.stream().map(o -> new
		// OrderData()));

		List<OrderHistoryData> listOfOrderHistoryData = createOrderHistoryData(orders);
		
		ReadOrdersResponse readOrderResponse = new ReadOrdersResponse(READ_ORDER_HISTORY_OK, listOfOrderHistoryData);
		return new ResponseEntity<ReadOrdersResponse>(readOrderResponse, HttpStatus.OK);
	}
	
	private List<OrderHistoryData> createOrderHistoryData(Collection<Order> orders) {
		List<OrderHistoryData> listOfOrderHistoryData = new ArrayList<>();
		for (Order o : orders) {
			OrderHistoryData orderHistoryData = new OrderHistoryData(o.getId(), o.getUser().getUserName(),
					o.getEvent().getId(), o.getEvent().getEventName(), o.getQty(), o.isApproved());
			listOfOrderHistoryData.add(orderHistoryData);
		}
		
		return listOfOrderHistoryData;
	}
	


	// private void validateUser(String userName) {
	// this.accountRepository.findByUsername(userName).orElseThrow(
	// () -> new UserNotFoundException(userId));
	// }

	// @ResponseStatus(HttpStatus.NOT_FOUND)
	// class UserNotFoundException extends RuntimeException {
	//
	// public UserNotFoundException(String userId) {
	// super("could not find user '" + userId + "'.");
	// }
	// }

}
