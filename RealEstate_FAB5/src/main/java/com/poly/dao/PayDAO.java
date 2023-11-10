package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Pay;

public interface PayDAO extends JpaRepository<Pay, Integer>{

	@Query(value="select top 1 * from pay order by pay_id desc", nativeQuery = true)
	Pay getTop1PayDesc();
}
