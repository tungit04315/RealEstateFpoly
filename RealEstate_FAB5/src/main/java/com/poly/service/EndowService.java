package com.poly.service;

import java.util.List;
import com.poly.bean.Endow;

public interface EndowService {
	
	public List<Endow> findAll();

	public Endow findById(Integer id);

	public Endow create(Endow e);

	public Endow update(Endow e);

	public void delete(Integer id);
}
