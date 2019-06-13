package com.yakov.coupons.beans;

import java.util.Date;

import com.yakov.coupons.enums.CategoriesEnum;

public class Coupon {
	/**
	 * The coupon bean file, with exception handling, has props setters getters
	 * constructor and a toString method
	 */
	// ---------------------prop------------------

	private long id, companyId;
	private int amount;
	private String title, description, image;
	private Date startDate;
	private Date endDate;
	private double price;
	private CategoriesEnum category;

	



	
	// ---------------------constructor---------------------
		/**
		 * Creates new coupon with the following parameters
		 * 
		 * @param id          the coupon's id
		 * @param companyId  the company's id
		 * @param amount      the amount of coupons
		 * @param title       the coupon title
		 * @param description the coupon description
		 * @param image       the url for an image of the coupon
		 * @param startDate  the start date of the coupon
		 * @param endDate    the end date of the coupon
		 * @param price       the coupon's price
		 * @param category    the enum category of the coupon
		 * @throws Exception general exception
		 * @see com.yakov.coupons.Coupons.exceptionHandling package.
		 */

		public Coupon(long companyId, CategoriesEnum category, String title, String description, Date startDate, Date endDate,
				int amount, double price, String image) {
			super();
			setCompanyId(companyId);
			setTitle(title);
			setDescription(description);
			setEndDate(startDate);
			setEndDate(endDate);
			setAmount(amount);
			setPrice(price);
			setImage(image);
		}
		
		public Coupon(long id, long companyId, CategoriesEnum category, String title, String description, Date startDate,
				Date endDate, int amount, double price, String image) {
			this(companyId, category, title, description, startDate, endDate, amount, price, image);
			setId(id);
			
		}
		
		public Coupon() {
			super();
		}

		// ---------------------getters + setters---------------------
		
		
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(long companyId) {
			this.companyId = companyId;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public CategoriesEnum getCategory() {
			return category;
		}

		public void setCategory(CategoriesEnum category) {
			this.category = category;
		}


	// ---------------------methods--------------------------------
	@Override
	public String toString() {
		return "Coupon ID: " + id + "\nCompany ID: " + companyId  + "\nAmount: " + amount
				+ "\nTitle: " + title + "\nDescription:" + description + "\nImage link: " + image + "\nStart Date: "
				+ startDate + "\nEnd Date: " + endDate + "\nPrice: " + price + "$\nCategory: " + category
				+ "\nThank You. ";
	}
}
