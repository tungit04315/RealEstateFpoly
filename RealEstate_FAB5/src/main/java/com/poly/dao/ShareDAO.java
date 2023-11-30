package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Shares;

public interface ShareDAO extends JpaRepository<Shares, Integer>{

	@Query(value="select * from shares where users = ?1", nativeQuery = true)
	public Page<Shares> getListShare(String username, Pageable p);
}
