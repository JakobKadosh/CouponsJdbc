package com.yakov.coupons.beans;

import java.util.ArrayList;
import java.util.List;


public class Company {

	/**
	 * Company bean file with exception handling
	 */
	// ----------------------props------------------
	private long id;
	private String name;
	private String email;
	private String address;
	List<Coupon> companyCoupons = new ArrayList<Coupon>();

	// ----------------------constructors-----------
	/**
	 * 
	 * @param id              - Company id in the Companies table in the database
	 * @param name            - Company name
	 * @param email           - Company email address
	 * @param password        - Company's password to access system
	 * @param companysCoupons - An ArrayList of all the company's coupons
	 * @throws Exception general exception
	 * @see com.yakov.coupons.Coupons.exceptionHandling
	 */

	public Company(long id, String name, String email, String address) {
		this(name, email, address);
		setId(id);

	}

	public Company(String name, String email, String address) {
		super();
		setName(name);
		setEmail(email);
		setAddress(address);
	}

	public Company() {
		super();
	}

	// ----------------------getters + setters-----------

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Coupon> getCompaniesCoupons() {
		return companyCoupons;
	}

	public void setCompanyCoupons(List<Coupon> companyCoupons) {
		this.companyCoupons = companyCoupons;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\nName: " + name + "\nemail: " + email + "\naddress: " + address + "\nCoupons: "
				+ companyCoupons.toString();
	}

}
