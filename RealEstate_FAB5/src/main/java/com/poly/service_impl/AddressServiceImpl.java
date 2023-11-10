package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.District;
import com.poly.bean.Province;
import com.poly.bean.Wards;
import com.poly.dao.DistrictDAO;
import com.poly.dao.ProvinceDAO;
import com.poly.dao.WardsDAO;
import com.poly.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	ProvinceDAO provinceDao;
	
	@Autowired
	DistrictDAO districtDao;
	
	@Autowired
	WardsDAO wardsDao;
	
	@Override
	public List<Province> getFindAllProvince() {
		// TODO Auto-generated method stub
		return provinceDao.findAll();
	}

	@Override
	public List<District> getFindAllDistrict(Integer id) {
		// TODO Auto-generated method stub
		return districtDao.getFindAll(id);
	}

	@Override
	public List<Wards> getFindAllWards(Integer id) {
		// TODO Auto-generated method stub
		return wardsDao.getFindAll(id);
	}

}
