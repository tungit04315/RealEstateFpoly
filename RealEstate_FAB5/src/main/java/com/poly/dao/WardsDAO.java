package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Wards;

public interface WardsDAO extends JpaRepository<Wards, Integer>{

	@Query(value="select * from wards where district_id = ?1", nativeQuery = true)
	public List<Wards> getFindAll(Integer id);
}
