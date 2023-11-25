package com.poly.service;

import java.util.List;

import com.poly.bean.District;
import com.poly.bean.Province;
import com.poly.bean.Wards;

public interface AddressService {
	
	public List<Province> getFindAllProvince();
	
	public List<District> getFindAllDistrict(Integer id);
	
	public List<Wards> getFindAllWards(Integer id);
}
