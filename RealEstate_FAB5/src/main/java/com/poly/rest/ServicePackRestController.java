package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.ServicePack;
import com.poly.service.ServicePackService;

@RestController
public class ServicePackRestController {

	@Autowired
	ServicePackService servicePack;
	
	@GetMapping("/rest/service-pack")
	public List<ServicePack> getFindAll(){
		return servicePack.getFindAll();
	}
	
	@GetMapping("/rest/service-pack-findById")
	public ServicePack getFindById(@Param("id") Integer id) {
		return servicePack.FindBy(id);
	}
}
