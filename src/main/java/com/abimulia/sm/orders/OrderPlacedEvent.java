/**
 * OrderPlacedEvent.java
 * 28-Nov-2024
 */
package com.abimulia.sm.orders;

import org.springframework.modulith.events.Externalized;

/**
 * @author abimu
 *
 * @version 1.0 (28-Nov-2024)
 * @since 28-Nov-2024 11:17:54 PM
 * 
 * 
 * Copyright(c) 2024 Abi Mulia
 */

@Externalized(target = AmqpIntegrationConfiguration.ORDERS_Q)
public record OrderPlacedEvent(int order,String message) {

}
