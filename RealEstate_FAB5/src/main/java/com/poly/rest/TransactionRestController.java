package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.Pay;
import com.poly.bean.Transactions;
import com.poly.dao.PayDAO;
import com.poly.dao.TransactionsDao;

@RestController
public class TransactionRestController {

	@Autowired
	TransactionsDao dao;
	
	@Autowired
	PayDAO payDao;
	
	@GetMapping("/test")
	public Integer getTest() {
		
		Transactions s = dao.getTransaction("user1");
		return s.getTransactions_id();
	}
	
	
}
