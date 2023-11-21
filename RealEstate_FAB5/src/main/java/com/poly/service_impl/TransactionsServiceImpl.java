package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
//		return dao.getTransactionByUserId(null);
		return dao.findById(id).get();
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
		return dao.getTransactionByUserId(username);
	}

	@Override
	public Transactions createJson(JsonNode data) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Transactions t = mapper.convertValue(data, Transactions.class);
		return dao.save(t);
	}

}
