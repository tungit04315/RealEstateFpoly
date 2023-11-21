package com.poly.service;

import java.util.List;

import com.poly.bean.Logins;

public interface LoginsService {
	
	public List<Logins> ALL();
	
	public Logins findById(Integer id);
	
	public Logins Create(Logins Logins);
	
	public Logins Update(Logins Logins);
	
	public Logins findByLogins();
	
	public void Delete(Integer Logins);
}
