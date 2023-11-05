package com.poly.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<DetailTransactions> findAllByUser(String username) {
		// TODO Auto-generated method stub
		return dao.getDetailTransactionsByUser(username);
	}

}
