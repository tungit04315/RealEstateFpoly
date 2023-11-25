package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Logins;
import com.poly.dao.LoginsDAO;
import com.poly.service.LoginsService;

@Service
public class LoginsServiceImpl implements LoginsService{

	@Autowired
	LoginsDAO dao;
	
	@Override
	public List<Logins> ALL() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Logins findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public Logins Create(Logins Logins) {
		// TODO Auto-generated method stub
		return dao.save(Logins);
	}

	@Override
	public Logins Update(Logins Logins) {
		// TODO Auto-generated method stub
		return dao.save(Logins);
	}

	@Override
	public void Delete(Integer Logins) {
		// TODO Auto-generated method stub
		dao.deleteById(Logins);
	}

	@Override
	public Logins findByLogins() {
		// TODO Auto-generated method stub
		return dao.getLogin();
	}

}
