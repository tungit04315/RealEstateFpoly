package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.DetailTransactions;
import com.poly.service.DetailTransactionService;

@RestController
public class DetailTransactionsRestController {

	@Autowired
	DetailTransactionService detailTransactionService;
	
	@PostMapping("/rest/create-detail-transaction")
	public DetailTransactions Craete(@RequestBody JsonNode data) {
		return detailTransactionService.createJson(data);
	}
}
