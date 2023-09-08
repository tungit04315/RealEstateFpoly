package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Ranks;
import com.poly.dao.RanksDAO;
import com.poly.service.RanksService;

@Service
public class RanksServiceImpl implements RanksService{

	@Autowired
	RanksDAO dao;
	
	@Override
	public List<Ranks> findAll() {
		return dao.findAll();
	}

	@Override
	public Ranks findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Ranks create(Ranks r) {
		return dao.save(r);
	}

	@Override
	public Ranks update(Ranks r) {
		return dao.save(r);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
