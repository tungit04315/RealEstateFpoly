package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.bean.ServicePack;

public interface ServicePackService {
	
	public List<ServicePack> getFindAll();
	
	public Page<ServicePack> getFindAll(Pageable p);
	
	public ServicePack FindBy(Integer id);
	
	public ServicePack Create(ServicePack s);
	
	public ServicePack Update(ServicePack s);
	
	public void Delete(Integer id);
}
