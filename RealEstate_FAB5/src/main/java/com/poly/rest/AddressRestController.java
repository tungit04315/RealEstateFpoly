package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.District;
import com.poly.bean.Province;
import com.poly.bean.Wards;
import com.poly.service.AddressService;

@RestController
public class AddressRestController {

	@Autowired
	AddressService addressService;
	
	@GetMapping("/rest/province")
	public List<Province> getAllProvince(){
		return addressService.getFindAllProvince();
	}
	
	@GetMapping("/rest/district")
	public List<District> getAllDistrict(@Param("id") Integer id){
		return addressService.getFindAllDistrict(id);
	}
	
	@GetMapping("/rest/wards")
	public List<Wards> getAllWards(@Param("id") Integer id){
		return addressService.getFindAllWards(id);
	}
	
}
