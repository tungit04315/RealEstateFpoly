package com.poly.service;

import java.util.List;

import com.poly.bean.*;

public interface TypePropertyService {
	
	public List<TypePropertys> findAll();

	public TypePropertys findById(Integer id);

	public TypePropertys create(TypePropertys e);

	public TypePropertys update(TypePropertys e);

	public void delete(Integer id);
}
