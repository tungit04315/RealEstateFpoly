package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Maps;

public interface MapDAO extends JpaRepository<Maps, Integer>{
	@Query(value="select * from maps where post_id = ?1", nativeQuery = true)
	public Maps getMapByPostId(Integer id);
}
