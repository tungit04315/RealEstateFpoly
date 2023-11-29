package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.TypePropertys;
import com.poly.service.TypePropertyService;

@RestController
public class TypePropertyRestController {

	@Autowired
	TypePropertyService typeService;
	
	@GetMapping("/type-property")
	public List<TypePropertys> getAll(){
		return typeService.findSelectTop6();
	}
	
	@GetMapping("/type-property-findById")
	public TypePropertys getFindById(@Param("id") Integer id) {
		return typeService.findById(id);
	}
	
	@RequestMapping("/type-property-suggest")
	public List<TypePropertys> getSuggestType(){
		return typeService.findSuggest();
	}
	
	@GetMapping("/admin-test-type")
	public Page<TypePropertys> get(){
		Pageable pageable = PageRequest.of(1 - 1, 4);
		Page<TypePropertys> typeProperty = typeService.findPageAll(pageable);
		return typeProperty;
	}
}
