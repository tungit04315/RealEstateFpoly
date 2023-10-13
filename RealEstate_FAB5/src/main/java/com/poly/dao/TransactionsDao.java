package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.bean.Transactions;

public interface TransactionsDao extends JpaRepository<Transactions, Integer>{

	@Query("Select o from Transactions o where o.users.username = ?1")
	public Transactions getTransaction(String user_id);
	
	@Query(value="Select top 1 * from Transactions where users = ?1 ORDER BY transactions_id DESC", nativeQuery = true)
	public Transactions getTransactionByUserId(String user_id);
}
