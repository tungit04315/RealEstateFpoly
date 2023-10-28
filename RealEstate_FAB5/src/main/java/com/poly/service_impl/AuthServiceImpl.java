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

	@Override
	public Auth findByUserID(String username) {
		// TODO Auto-generated method stub
		return dao.selectByUserId(username);
	}

	@Override
	public void delete(String username) {
		dao.deleteByUserId(username);
		
	}

}
