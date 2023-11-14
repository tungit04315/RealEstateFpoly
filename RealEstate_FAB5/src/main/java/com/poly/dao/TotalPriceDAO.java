package com.poly.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.TotalPrice;

public interface TotalPriceDAO extends JpaRepository<TotalPrice, Integer>{

	@Query(value="SELECT new ThongKe(SUM(d.price) AS TotalPrice)"
			+ " FROM detail_transactions d"
			+ " INNER JOIN transactions t ON d.transactions_id = t.transactions_id"
			+ " WHERE YEAR(t.create_at) = ?1;", nativeQuery = true)
	public Integer getTotal(Integer year);
}
