package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.bean.*;

public interface DetailTransactionService {
	
	public List<DetailTransactions> findAll();
	
	public Page<DetailTransactions> findAll(Pageable p);
	
	public Page<DetailTransactions> findAllByUser(String username, Pageable p);
	
	public List<DetailTransactions> findAllDetailTransactionPay();
	
	public List<DetailTransactions> findAllDetailTransactionPost();

	public DetailTransactions findById(Integer id);
	
	public DetailTransactions findByTransactionId(String username);

	public DetailTransactions create(DetailTransactions u);
	
	public DetailTransactions createJson(JsonNode data);

	public DetailTransactions update(DetailTransactions u);

	public void delete(Integer id);
}
