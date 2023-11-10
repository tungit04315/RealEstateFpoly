package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.TypePropertys;
import com.poly.service.TypePropertyService;

@RestController
public class TypePropertyRestController {

	@Autowired
	TypePropertyService typeService;
	
	@GetMapping("/type-property")
	public List<TypePropertys> getAll(){
		return typeService.findAll();
	}
	
	@GetMapping("/type-property-findById")
	public TypePropertys getFindById(@Param("id") Integer id) {
		return typeService.findById(id);
	}
}
