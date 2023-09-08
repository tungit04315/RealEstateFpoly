package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Pay;
import com.poly.dao.PayDAO;
import com.poly.service.PayService;

@Service
public class PayServiceImpl implements PayService{

	@Autowired
	PayDAO dao;
	
	@Override
	public List<Pay> findAll() {
		return dao.findAll();
	}

	@Override
	public Pay findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Pay create(Pay pay) {
		return dao.save(pay);
	}

	@Override
	public Pay update(Pay pay) {
		return dao.save(pay);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
