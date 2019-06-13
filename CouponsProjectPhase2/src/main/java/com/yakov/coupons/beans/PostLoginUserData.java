package com.yakov.coupons.beans;

import com.yakov.coupons.enums.ClientType;

public class PostLoginUserData {

	private long userId, companId;
	private ClientType clientType;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCompanId() {
		return companId;
	}

	public void setCompanId(long companId) {
		this.companId = companId;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public PostLoginUserData(long userId, long companyId, ClientType clientType) {
		super();
		this.userId = userId;
		this.companId = companyId;
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "PostLoginUserData [userId=" + userId + ", companyId=" + companId + "]";
	};

}
