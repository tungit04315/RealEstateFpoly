package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.TypePropertys;

public interface TypePropertysDAO extends JpaRepository<TypePropertys, Integer>{

	@Query(value="SELECT TOP 14 * FROM type_propertys ORDER BY NEWID()", nativeQuery = true)
	public List<TypePropertys> getRandomSuggestRealEstate();
	
	@Query(value="SELECT TOP 10 * FROM type_propertys", nativeQuery = true)
	public List<TypePropertys> getTop6Type();
	
}
