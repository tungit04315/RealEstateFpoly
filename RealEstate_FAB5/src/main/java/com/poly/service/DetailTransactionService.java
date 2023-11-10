package com.poly.service;

import java.util.List;

import com.poly.bean.*;

public interface DetailTransactionService {
	public List<DetailTransactions> findAll();
	
	public List<DetailTransactions> findAllByUser(String username);

	public DetailTransactions findById(Integer id);
	
	public DetailTransactions findByTransactionId(String username);

	public DetailTransactions create(DetailTransactions u);

	public DetailTransactions update(DetailTransactions u);

	public void delete(Integer id);
}
