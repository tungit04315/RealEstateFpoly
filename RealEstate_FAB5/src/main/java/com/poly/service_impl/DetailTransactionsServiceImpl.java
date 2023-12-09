package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.bean.DetailTransactions;
import com.poly.dao.DetailTransactionsDAO;
import com.poly.service.DetailTransactionService;

@Service
public class DetailTransactionsServiceImpl implements DetailTransactionService{

	@Autowired
	DetailTransactionsDAO dao;
	
	@Override
	public List<DetailTransactions> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public DetailTransactions findById(Integer id) {
		// TODO Auto-generated method stub
		return dao.findById(id).get();
	}

	@Override
	public DetailTransactions findByTransactionId(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DetailTransactions create(DetailTransactions u) {
		// TODO Auto-generated method stub
		return dao.save(u);
	}

	@Override
	public DetailTransactions update(DetailTransactions u) {
		// TODO Auto-generated method stub
		return dao.save(u);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public Page<DetailTransactions> findAllByUser(String username, Pageable p) {
		// TODO Auto-generated method stub
		return dao.getDetailTransactionsByUser(username, p);
	}

	@Override
	public DetailTransactions createJson(JsonNode data) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DetailTransactions d = mapper.convertValue(data, DetailTransactions.class);
		return dao.save(d);
	}

	@Override
	public List<DetailTransactions> findAllDetailTransactionPay() {
		// TODO Auto-generated method stub
		return dao.getDetailTransactionPay();
	}

	@Override
	public List<DetailTransactions> findAllDetailTransactionPost() {
		// TODO Auto-generated method stub
		return dao.getDetailTransactionPost();
	}

	@Override
	public Page<DetailTransactions> findAll(Pageable p) {
		// TODO Auto-generated method stub
		return dao.findAll(p);
	}

}
