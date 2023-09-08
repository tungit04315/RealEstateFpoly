package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.RankName;
import com.poly.dao.RankNameDAO;
import com.poly.service.RankNameService;

@Service
public class RankNameServiceImpl implements RankNameService{

	@Autowired
	RankNameDAO dao;
	
	@Override
	public List<RankName> findAll() {
		return dao.findAll();
	}

	@Override
	public RankName findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public RankName create(RankName r) {
		return dao.save(r);
	}

	@Override
	public RankName update(RankName r) {
		return dao.save(r);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
