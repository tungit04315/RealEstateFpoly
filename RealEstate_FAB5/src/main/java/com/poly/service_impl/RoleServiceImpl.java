package com.poly.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.bean.Roles;
import com.poly.dao.RolesDAO;
import com.poly.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RolesDAO dao;
	
	@Override
	public Roles findbyId(String id) {
		return dao.findById(id).get();
	}

}
