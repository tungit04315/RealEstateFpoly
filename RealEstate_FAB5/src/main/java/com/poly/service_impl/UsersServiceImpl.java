package com.poly.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.bean.Auth;
import com.poly.bean.Users;
import com.poly.dao.AuthDAO;
import com.poly.dao.UsersDAO;
import com.poly.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService{
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	UsersDAO dao;
	
	@Autowired
	AuthDAO authDAO;

	@Override
	public List<Users> findAll() {
		return dao.findAll();
	}

	@Override
	public Users findById(String id) {
		Users users = dao.findById(id).orElse(null);
		if(users != null) {
			return users;
		}else {
			return null;
		}
	}

	@Override
	public Users create(Users u) {
		return dao.save(u);
	}

	@Override
	public Users update(Users u) {
		return dao.save(u);
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public Users findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users findByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRolesByUsername(String username) {
		List<String> roleNames = new ArrayList<>();

		List<Auth> authorities = authDAO.findAll();

		for (Auth auth : authorities) {
			if(auth.getUsers().getUsername().equals(username)){
				roleNames.add(auth.getRoles().getRoles_id());
			}
		}
		return roleNames;
	}

	@Override
	public Optional<Users> getAccount(String username) {
		return dao.findById(username);
	}

}
