package com.yakov.coupons.logic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yakov.coupons.beans.PostLoginUserData;
@Component
public class MyCachManager implements ICacheManager {

	private Map<Integer, PostLoginUserData>map;
	
	
	public MyCachManager() {
		this.map = new HashMap<Integer, PostLoginUserData>();
	}

	@Override
	public void put(Integer key, PostLoginUserData value) {
		this.map.put(key, value);
	}


	@Override
	public Object get(Integer key) {
		return map.get(key);
	}

}
