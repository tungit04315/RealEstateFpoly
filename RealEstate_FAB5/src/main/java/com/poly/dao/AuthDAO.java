package com.poly.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Auth;

public interface AuthDAO extends JpaRepository<Auth, Integer>{

	@Modifying
	@Transactional
	@Query(value = "delete auth where users = ?1", nativeQuery = true)
	public void deleteByUserId(String user);
	
	@Query(value = "select * from auth where users = ?1", nativeQuery = true)
	public Auth selectByUserId(String user);
}
