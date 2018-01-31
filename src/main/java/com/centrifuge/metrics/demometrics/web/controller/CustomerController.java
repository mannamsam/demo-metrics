package com.centrifuge.metrics.demometrics.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centrifuge.metrics.demometrics.data.CustomerDAO;
import com.centrifuge.metrics.demometrics.model.Customer;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Provides REST interface for accessing, adding, deleting and updating customer
 * information. Also, captures different metrics data using <i>DropWizard
 * Metrics</i> library.
 * 
 * @author Ram Mannam
 * @version 1.0
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 */
@RestController
@RequestMapping("/api/v1")
@Api(value = "CustomerController")
public class CustomerController {

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private MetricRegistry metricRegistry;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Returns all customers from the data store
	 * 
	 * @return list of customers
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "View list of customers", response = Customer.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessfully retried list of customers"), })
	public List<Customer> getCustomers() {
		Timer.Context timer = metricRegistry.timer("CustomerController.getCustomers.Timer").time();

		try {
			List<Customer> list = customerDAO.list();

			return list;
		} finally {
			timer.stop();
		}
	}

	/**
	 * Finds and returns customer for the given customer id.
	 * 
	 * @param id	the primary key of the customer
	 * @return customer
	 */
	@GetMapping(value = "/customers/{id}", produces = "application/json")
	@ApiOperation(value = "Searches for specific customer with an ID", response = Customer.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessfully retried the customer by the given ID"),
			@ApiResponse(code = 404, message = "Customer is not found in the list") })
	public ResponseEntity<?> getCustomer(@PathVariable("id") Long id) {
		Timer.Context timer = metricRegistry.timer("CustomerController.getCustomer.Timer").time();

		logger.debug(" customer id = " + id);
		try {
			Customer customer = customerDAO.get(id);
			logger.debug(" customer = " + customer);

			if (customer == null) {
				return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity(customer, HttpStatus.OK);
		} finally {
			timer.stop();
		}
	}

	/**
	 * Creates customer and returns the data with customer id
	 * 
	 * @param customer	details of the customer
	 * @return customer
	 */
	@PostMapping(value = "/customers")
	@ApiOperation(value = "Creates customer with given customer details and returns the customer", response = Customer.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Sucessfully created the customer") })
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		Counter crateCustomerCounts = metricRegistry.counter("CustomerController.createCustomer.Counter");
		crateCustomerCounts.inc();

		customerDAO.create(customer);

		return new ResponseEntity(customer, HttpStatus.CREATED);
	}

	/**
	 * Deletes the customer by the given customer id
	 * 
	 * @param id
	 *            the primary key of the customer
	 * @return id primary key of the customer
	 */
	@DeleteMapping("/customers/{id}")
	@ApiOperation(value = "Deletes the customer by customer ID", response = Long.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessfully created the customer"),
			@ApiResponse(code = 404, message = "Customer not found for the given ID") })
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		Meter requests = metricRegistry.meter("CustomerController.deleteCustomer.Meter");
		requests.mark();

		if (null == customerDAO.delete(id)) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}

	/**
	 * Updates the customer information with supplied customer data.
	 * 
	 * @param id	primary key of the customer
	 * @param customer	information of the customer
	 * @return customer
	 */
	@PutMapping("/customers/{id}")
	@ApiOperation(value = "Updates customer with given customer details and returns the customer", response = Customer.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessfully created the customer"),
			@ApiResponse(code = 404, message = "Customer not found for the given ID") })
	public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		Histogram updateHistogram = metricRegistry.histogram("CustomerController.updateCustomer.Histogram");

		try {
			customer = customerDAO.update(id, customer);

			if (null == customer) {
				return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity(customer, HttpStatus.OK);
		} finally {
			updateHistogram.update(customer.getFirstName().length());
		}
	}

}
