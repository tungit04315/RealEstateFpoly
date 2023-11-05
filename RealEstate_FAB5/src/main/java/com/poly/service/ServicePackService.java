package com.poly.service;

import java.util.List;

import com.poly.bean.ServicePack;

public interface ServicePackService {
	
	public List<ServicePack> getFindAll();
	
	public ServicePack FindBy(Integer id);
	
	public ServicePack Create(ServicePack s);
	
	public ServicePack Update(ServicePack s);
	
	public void Delete(Integer id);
}
