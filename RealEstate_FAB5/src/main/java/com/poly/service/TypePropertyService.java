package com.poly.service;

import java.util.List;

import com.poly.bean.*;

public interface TypePropertyService {
	
	public List<TypePropertys> findAll();
	
	public List<TypePropertys> findSuggest();
	
	public List<TypePropertys> findSelectTop6();

	public TypePropertys findById(Integer id);

	public TypePropertys create(TypePropertys e);

	public TypePropertys update(TypePropertys e);

	public void delete(Integer id);
}
