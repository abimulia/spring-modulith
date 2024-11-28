/**
 * OrdersController.java
 * 28-Nov-2024
 */
package com.abimulia.sm.orders;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author abimu
 *
 * @version 1.0 (28-Nov-2024)
 * @since 28-Nov-2024 10:36:54 PM
 * 
 * 
 * Copyright(c) 2024 Abi Mulia
 */

@Controller
@ResponseBody
@RequestMapping("/orders")
class OrdersController {
	private final OrderService orderService;
	
	OrdersController(OrderService orderService){
		this.orderService = orderService;
	}
	
	@PostMapping("place")
	void place(@RequestBody Order order) {
		this.orderService.place(order);
	}

}

@Service
@Transactional
class OrderService {
	private final OrderRepository repository;
	
	OrderService(OrderRepository repository){
		this.repository = repository;
	}
	
	void place(Order order) {
		var saved = this.repository.save(order);
		System.out.println("saved ["+saved+"]");
	}
	
	
}

interface OrderRepository extends ListCrudRepository<Order, Integer>{
	
}

@Table("orders") //Aggregates
record Order(@Id Integer id, Set<LineItem> lineItems) {

}

@Table("orders_line_items")
record LineItem(@Id Integer id, int product, int quantity) {
	
}