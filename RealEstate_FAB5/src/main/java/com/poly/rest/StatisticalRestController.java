package com.poly.rest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.poly.dao.StatisticsDAO;
import com.poly.dao.TransactionsDao;

@RestController
public class StatisticalRestController {

	@Autowired
	StatisticsDAO dao;
	
	@Autowired
	TransactionsDao transactionDao;
	
	@GetMapping("/rest/statisticl")
	public List<Object[]> getALL(){
		LocalDate currentDate = LocalDate.now();
        
        int currentYear = currentDate.getYear();
		return  transactionDao.getMonthlyTotalPrices(currentYear);
	}
	
	@GetMapping("/rest/income-in-recent-years")
	public List<Object[]> getIncomeInRecentYears(){
		return  transactionDao.getIncomeInRecentYears();
	}
}
