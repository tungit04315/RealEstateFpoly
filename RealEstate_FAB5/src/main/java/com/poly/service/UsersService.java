package com.poly.service;

import java.util.List;
import java.util.Optional;

import com.poly.bean.Users;

public interface UsersService {
	
	public List<Users> findAll();

	public Users findById(String id);

	public Users create(Users u);

	public Users update(Users u);

	public void delete(String id);
	
	public Users findByEmail(String email);
	
	public Users findByPhone(String phone);
	
	public List<String> getRolesByUsername(String username);
	
	public Optional<Users> getAccount(String username);
}
