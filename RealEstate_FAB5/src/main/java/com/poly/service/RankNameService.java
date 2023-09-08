package com.poly.service;

import java.util.List;
import com.poly.bean.RankName;

public interface RankNameService {
	
	public List<RankName> findAll();

	public RankName findById(Integer id);

	public RankName create(RankName r);

	public RankName update(RankName r);

	public void delete(Integer id);
}
