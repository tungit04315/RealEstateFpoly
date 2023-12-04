package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.DetailTransactions;

public interface DetailTransactionsDAO extends JpaRepository<DetailTransactions, Integer>{

	@Query(value="select * from detail_transactions d where d.transactions_id in (select t.transactions_id from transactions t where t.users = ?1)", nativeQuery = true)
	public List<DetailTransactions> getDetailTransactionsByUser(String username);
	
	@Query(value="select top 10 * from detail_transactions where transactions_type = 'true' order by transactions_id desc", nativeQuery = true)
	public List<DetailTransactions> getDetailTransactionPay();
	
	@Query(value="select top 10 * from detail_transactions where transactions_type = 'false' order by transactions_id desc", nativeQuery = true)
	public List<DetailTransactions> getDetailTransactionPost();
}
