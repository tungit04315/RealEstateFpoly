package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Pay;
import com.poly.bean.Transactions;
import com.poly.dao.PayDAO;
import com.poly.dao.TransactionsDao;
import com.poly.service.TransactionService;

@RestController
public class TransactionRestController {

	@Autowired
	TransactionService transactionService;

	@PostMapping("/rest/create-transaction")
	public Transactions getTest(@RequestBody JsonNode data) {
		return transactionService.createJson(data);
	}

}
