package com.poly.service;

import java.util.List;

import com.poly.bean.Pay;


public interface PayService {

	public List<Pay> findAll();

	public Pay findById(Integer id);

	public Pay create(Pay pay);

	public Pay update(Pay pay);

	public void delete(Integer id);
	
}
