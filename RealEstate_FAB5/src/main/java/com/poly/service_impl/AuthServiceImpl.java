package com.poly.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Auth;
import com.poly.dao.AuthDAO;
import com.poly.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	AuthDAO dao;
	
	@Override
	public Auth create(Auth auth) {
		return dao.save(auth);
	}

}
