package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.TypePropertys;
import com.poly.dao.TypePropertysDAO;
import com.poly.service.TypePropertyService;

@Service
public class TypePropertysServiceImpl implements TypePropertyService{

	@Autowired
	TypePropertysDAO dao;
	
	@Override
	public List<TypePropertys> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public TypePropertys findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public TypePropertys create(TypePropertys e) {
		// TODO Auto-generated method stub
		return dao.save(e);
	}

	@Override
	public TypePropertys update(TypePropertys e) {
		// TODO Auto-generated method stub
		return dao.save(e);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public List<TypePropertys> findSuggest() {
		// TODO Auto-generated method stub
		return dao.getRandomSuggestRealEstate();
	}

	@Override
	public List<TypePropertys> findSelectTop6() {
		// TODO Auto-generated method stub
		return dao.getTop6Type();
	}

}
