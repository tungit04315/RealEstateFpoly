package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.bean.Transactions;

public interface TransactionsDao extends JpaRepository<Transactions, Integer>{

	@Query("Select o from Transactions o where o.users.username = ?1")
	public Transactions getTransaction(String user_id);
}
