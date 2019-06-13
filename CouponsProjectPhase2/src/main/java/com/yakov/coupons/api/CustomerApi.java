package com.yakov.coupons.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.beans.Customer;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.logic.CustomerController;

@RestController
@RequestMapping("/customers")
public class CustomerApi {
	@Autowired
	private CustomerController customerController;

	@PostMapping()
	public void addCustomer(@RequestBody Customer customer) throws MyException {
		customerController.addCustomer(customer);
	}

	@GetMapping("/{customerId}")
	public Customer getCustomerById(@PathVariable("customerId") long Id) throws MyException {
		return customerController.getCustomerByID(Id);
	}
	
	
	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) throws MyException {
		customerController.updateCustomer(customer);
	}

	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long id) throws MyException {
		customerController.deleteCustomer(id);
	}

	@GetMapping
	public List<Customer> getAllCustomers() throws MyException {
		return customerController.getAllCustomers();
	}

}
