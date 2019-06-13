package com.yakov.coupons.beans;


import com.yakov.coupons.exceptions.MyException;

public class Customer {
	/**
	 * Customer bean java file props setters getters constructor and a toString
	 * method regex for email validation
	 */
	// ---------------------prop----------------------------------
	private User user;
	private long customerId;
	private String firstName, lastName, email, password;

	// ---------------------constructor--------------------------------
	/**
	 * 
	 * @param id         the customer id in the Customers table in the DB
	 * @param firstName customer's first name
	 * @param lastName  customer's last name
	 * @param email      customer's email
	 * @param password   customer's password
	 * @throws Exception general exception
	 * @see com.yakov.coupons.Coupons.exceptionHandling
	 */
	public Customer(long customerId, User user, String firstName, String lastName, String email, String password)
			 {
		this(user,firstName,lastName,email,password);
		setCustomerId(customerId);
	}

	public Customer(User user, String firstName, String lastName, String email, String password)  {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setUser(user);
	}

	public Customer() {
		super();
	}

	// ---------------------getters + setters-------------------------
	

	public User getUser() {
		return user;
	}

	public void setUser(User user)  {
			this.user = user;
	}

	

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName customer's first name
	 * @throws MyException declared in exceptionHandling package
	 * @see com.yakov.coupons.Coupons.exceptionHandling.MyException
	 */
	public void setFirstName(String firstName)  {

		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email the customers email address
	 * @throws EmailException declared in exceptionHandling package
	 * @see exceptionHandling.EmailException
	 */
	public void setEmail(String email)  {
			this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
			this.password = password;
	}

	// ---------------------methods--------------------------------
	@Override
	public String toString() {
		return "Customer ID: " + customerId + "\nFirst name: " + firstName + "\nLast name: " + lastName + "\nemail: " + email
				+ "\nPassword: " + password;
	}



}
