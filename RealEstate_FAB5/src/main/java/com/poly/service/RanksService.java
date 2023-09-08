package com.poly.service;

import java.util.List;
import com.poly.bean.Ranks;

public interface RanksService {

	public List<Ranks> findAll();

	public Ranks findById(Integer id);

	public Ranks create(Ranks r);

	public Ranks update(Ranks r);

	public void delete(Integer id);
}
