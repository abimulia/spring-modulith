/**
 * Products.java
 * 28-Nov-2024
 */
package com.abimulia.sm.products;

import org.springframework.context.event.EventListener;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abimulia.sm.orders.OrderPlacedEvent;

/**
 * @author abimu
 *
 * @version 1.0 (28-Nov-2024)
 * @since 28-Nov-2024 11:13:57 PM
 * 
 * 
 * Copyright(c) 2024 Abi Mulia
 */

@Service
@Transactional
public class Products {
	
	@ApplicationModuleListener
	void on (OrderPlacedEvent orderPlaceEvent) throws Exception {
		System.out.println("starting ["+ orderPlaceEvent+"]");
		Thread.sleep(5_000);
		System.out.println("stopping ["+ orderPlaceEvent+"]");
		
	}

}
