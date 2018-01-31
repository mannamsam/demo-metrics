package com.centrifuge.metrics.demometrics.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.centrifuge.metrics.demometrics.model.Customer;

/**
 * 
 * Data access object to store customer data temporarily in the memory. Provides
 * methods to add, update, get and delete customer data from the memory.
 * 
 * @author Ram Mannam
 * @version 1.0
 *
 */
@Component
public class CustomerDAO {

	private static List<Customer> customers;
	{
		customers = new ArrayList<Customer>();

		for (long i = 0; i < 100; i++) {
			customers.add(new Customer(i, "Bill " + i, "Kornick" + i));
		}
	}

	public List<Customer> list() {
		return customers;
	}

	public Customer get(Long id) {
		for (Customer customer : customers) {
			if (customer.getId().equals(id)) {
				return customer;
			}
		}

		return null;
	}

	public Customer create(Customer customer) {
		customer.setId(System.currentTimeMillis());
		customers.add(customer);

		return customer;
	}

	public Boolean delete(Long id) {
		Customer c = new Customer(id);
		if ( customers.contains(c)) {
			customers.remove(c);
			
			return true;
		}
		
		return false;
	}

	public Customer update(Long id, Customer customerRequest) {
		for ( Customer c:customers) {
			if ( c.getId() == id && customerRequest.getId() == id) {
				c.setFirstName(customerRequest.getFirstName());
				c.setLastName(customerRequest.getLastName());
				
				return c;
			}
		}
		

		return null;
	}

}
