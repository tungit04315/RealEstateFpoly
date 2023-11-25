package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.bean.Statistical;

public interface StatisticsDAO extends JpaRepository<Statistical, Integer>{

	@Query(value="SELECT"
			+ "    MONTH(t.create_at) AS month,"
			+ "    YEAR(t.create_at) AS year,"
			+ "    SUM(dt.price) AS total_price"
			+ "FROM"
			+ "    transactions t"
			+ "INNER JOIN"
			+ "    detail_transactions dt ON t.transactions_id = dt.transactions_id"
			+ "WHERE YEAR(t.create_at) = 2023"
			+ "GROUP BY"
			+ "    YEAR(t.create_at), MONTH(t.create_at)"
			+ "ORDER BY"
			+ "    YEAR(t.create_at), MONTH(t.create_at)", nativeQuery = true)
	public List<Statistical> getStatistical();
}
