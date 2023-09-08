package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Endow;
import com.poly.dao.EndowDAO;
import com.poly.service.EndowService;

@Service
public class EndowServiceImpl implements EndowService{

	@Autowired
	EndowDAO dao;
	
	@Override
	public List<Endow> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Endow findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Endow create(Endow e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Endow update(Endow e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
