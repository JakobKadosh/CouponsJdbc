package com.yakov.coupons.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.beans.Customer;
import com.yakov.coupons.dao.CustomerDAO;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class CustomerController {
	// getting variables of all the dao in class level to be used in the controller
	// functionality
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private UserController userController;
	// many of the functions have input validations to do before manipulating data
	// on the Data Base

	

	public Customer getCustomerByID(long customerID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "customer id is invalid");
		}
		return customerDAO.getCustomerByID(customerID);
	}

	
	public void updateCustomer(Customer customer) throws MyException {
		if (isCustomerValidToUpdate(customer)) {
			customerDAO.updateCustomer(customer);
		} else {

			throw new MyException(ErrorType.GENERAL_ERROR, "Customer details are invalid");
		}
	}

	public void addCustomer(Customer customer) throws MyException {
		if (isCustomerValidToAdd(customer)) {
			// Adding a new user to the data base before adding him as a customer
			//plus setting the customer ID according to the added user. 
			customer.setCustomerId(userController.addUser(customer.getUser()));
			customerDAO.addCustomer(customer);
		}
		else throw new MyException(ErrorType.GENERAL_ERROR, "Customer details are invalid");
	}
	//SQL is responsible to delete all dependencies "ON DELETE CASCADE"

	public void deleteCustomer(long customerID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "cutomer id is invalid");
		} else {
			customerDAO.deleteCustomer(customerID);
		}
	}


	public List<Customer> getAllCustomers() throws MyException {
		return customerDAO.getAllCustomers();
	}

	private boolean isCustomerValidToAdd(Customer customer) throws MyException {
		if (!InputChecker.isValidName(customer.getFirstName())) {
			throw new MyException(ErrorType.INVALID_NAME, "First name is invalid");
		} else if (!InputChecker.isValidName(customer.getLastName())) {
			throw new MyException(ErrorType.INVALID_NAME, "Last name is invalid");
		} else if (customerDAO.isCustomerExist(customer.getEmail(), customer.getPassword())) {
			throw new MyException(ErrorType.NAME_IS_ALREADY_EXISTS, "customer already registerd on the data base");
		} else if (customer.getUser() == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user object is null!");
		
		} else if (!InputChecker.isValidPassword(customer.getPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "Password is invalid");
		}
		return true;
	}

	private boolean isCustomerValidToUpdate(Customer customer) throws MyException {
		if (!InputChecker.isValidName(customer.getFirstName())) {
			throw new MyException(ErrorType.INVALID_NAME, "First name is invalid");
		} else if (!InputChecker.isValidName(customer.getLastName())) {
			throw new MyException(ErrorType.INVALID_NAME, "Last name is invalid");
		} else if (customer.getUser() == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "user object is null!");
		} else if (!InputChecker.isValidPassword(customer.getPassword())) {
			throw new MyException(ErrorType.INVALID_PASSWORD, "Password is invalid");
		}
		return true;
	}
}
