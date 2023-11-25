package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.bean.ServicePack;
import com.poly.dao.ServicePackDAO;
import com.poly.service.ServicePackService;

@Service
public class ServicePackServiceImpl implements ServicePackService{

	@Autowired
	ServicePackDAO dao;
	
	@Override
	public List<ServicePack> getFindAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public ServicePack FindBy(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public ServicePack Create(ServicePack s) {
		// TODO Auto-generated method stub
		return dao.save(s);
	}

	@Override
	public ServicePack Update(ServicePack s) {
		// TODO Auto-generated method stub
		return dao.save(s);
	}

	@Override
	public void Delete(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public Page<ServicePack> getFindAll(Pageable p) {
		// TODO Auto-generated method stub
		return dao.findAll(p);
	}

}
