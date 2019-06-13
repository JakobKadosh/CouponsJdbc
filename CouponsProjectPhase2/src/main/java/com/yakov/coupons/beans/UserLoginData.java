package com.yakov.coupons.beans;

import com.yakov.coupons.enums.ClientType;

public class UserLoginData {
	
	private long userId, companyId;
	private ClientType clientType;
	private int token;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	public ClientType getClientType() {
		return clientType;
	}
	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public UserLoginData(long userId, long companyId, ClientType clientType, int token) {
		setUserId(userId);
		setCompanyId(companyId);
		setClientType(clientType);
		setToken(token);
	}
	@Override
	public String toString() {
		return "UserLoginData [user_id=" + userId + ", company_id=" + companyId + ", client_type=" + clientType
				+ ", token=" + token + "]";
	}
	
	
	
}
