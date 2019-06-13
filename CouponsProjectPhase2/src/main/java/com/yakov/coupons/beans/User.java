package com.yakov.coupons.beans;

import com.yakov.coupons.enums.ClientType;

public class User {

	// User variables
	private Long userId;
	private String userName;
	private String userPassword;
	private Long companyId;
	private ClientType clientType;

	
	//constructors (an empty one. one with company and user id.
	//	one without user id. one without company id)  
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public ClientType getClientType() {
		return clientType;
	}
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	public User(Long userId, String userName, String userPassword,Long companyId, ClientType clientType) {
		this(userName,userPassword,clientType);
		setUserId(userId);
		setCompanyId(companyId);
	}
	public User() {
		super();
	}
	
	
	public User(String userName, String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public User(String userName, String userPassword, Long companyId, ClientType clientType)  {
		this(userName,userPassword,clientType);
		setCompanyId(companyId);
	}
	public User(String userName, String userPassword, ClientType clientType) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.clientType = clientType;
	}
	public User(Long userId, String userName, String userPassword, ClientType clientType) {
		this(userName,userPassword,clientType);
		setUserId(userId);
	}

	@Override
	public String toString() {
		return "User [user_id=" + userId + ", user_name=" + userName + ", user_password=" + userPassword
				+ ", company_id=" + companyId + ", type=" + clientType + "]";
	}

	
	
}
