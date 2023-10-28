package com.poly.service;

import com.poly.bean.Auth;

public interface AuthService {

	public Auth create(Auth auth);
	
	public Auth findByUserID(String username);
	
	public void delete(String username);
}
