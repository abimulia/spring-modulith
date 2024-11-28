/**
 * OrdersController.java
 * 28-Nov-2024
 */
package com.abimulia.sm.orders;


import java.util.Set;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	private final ApplicationEventPublisher eventPublisher;
	
	OrderService(OrderRepository repository,ApplicationEventPublisher eventPublisher){
		this.repository = repository;
		this.eventPublisher = eventPublisher;
	}
	
	void place(Order order) {
		var saved = this.repository.save(order);
		System.out.println("saved ["+saved+"]");
		this.eventPublisher.publishEvent(new OrderPlacedEvent(saved.id(),"Order placed"));
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

@Configuration
class AmqpIntegrationConfiguration{
	
	@Bean
	Binding binding(Queue queue, Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ORDERS_Q).noargs();
	}
	
	@Bean 
	Exchange exchange() {
		return ExchangeBuilder.directExchange(ORDERS_Q).build();
	}
	
	@Bean
	Queue queue() {
		return QueueBuilder.durable(ORDERS_Q).build();
	}
	
	static final String ORDERS_Q = "orders";
}