package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Logins;

public interface LoginsDAO extends JpaRepository<Logins, Integer>{

	@Query(value="select top 1 * from logins order by logins_id desc", nativeQuery = true)
	public Logins getLogin();
}
