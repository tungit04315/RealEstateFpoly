package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.Transactions;



public interface TransactionService {

	public List<Transactions> findAll();

	public Transactions findById(Integer id);
	
	public Transactions findByUserId(String username);

	public Transactions create(Transactions u);
	
	public Transactions createJson(JsonNode data);

	public Transactions update(Transactions u);

	public void delete(Integer id);
}
