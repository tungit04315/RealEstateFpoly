package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.bean.Transactions;
import com.poly.dao.TransactionsDao;
import com.poly.service.TransactionService;

@Service
public class TransactionsServiceImpl implements TransactionService{

	@Autowired
	TransactionsDao dao;
	
	@Override
	public List<Transactions> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transactions findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transactions create(Transactions u) {
		// TODO Auto-generated method stub
		return dao.save(u);
	}

	@Override
	public Transactions update(Transactions u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Transactions findByUserId(String username) {
		// TODO Auto-generated method stub
		return dao.getTransaction(username);
	}

}
