package com.centrifuge.metrics.demometrics.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.centrifuge.metrics.demometrics.data.CustomerDAO;
import com.centrifuge.metrics.demometrics.model.Customer;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CustomerDAO customerDAO;

	@MockBean
	private CustomerController customerController;

	@Test
	public void listCustomers() throws Exception {

		List<Customer> customers = Arrays.asList(new Customer(1l, "FirstName", "LastName"),
				new Customer(2l, "FirstName", "LastName"));

		given(this.customerController.getCustomers()).willReturn(customers);

		this.mvc.perform(get("/api/v1/customers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));

		verify(customerController, times(1)).getCustomers();
		verifyNoMoreInteractions(customerController);
	}

}