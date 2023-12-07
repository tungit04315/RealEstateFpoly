package com.poly.statistical;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.bean.DetailTransactions;
import com.poly.bean.Transactions;
import com.poly.bean.Users;
import com.poly.service.DetailTransactionService;
import com.poly.service.TransactionService;
import com.poly.service.UsersService;

@SpringBootTest
public class UnitTestStatistical {

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	DetailTransactionService detailTransactionService;
	
	@Autowired
	UsersService userService;
	
	@Test
	void findAll() {
		assertThat(transactionService.findAll()).isNotNull();
	}
	
	@Test
	void findById() {
		List<Transactions> list = transactionService.findAll();
		Transactions s = transactionService.findById(list.get(0).getTransactions_id());
		assertThat(s).isNotNull();
	}
	
	@Test
	void findByUsername() {
		String username = "tungngayngo";
		Transactions s = transactionService.findByUserId(username);
		assertThat(s).isNotNull();
	}
	
	@Test
	void createTransactions() {
		String username = "tungngayngo";
		Users u = userService.findById(username);
		Transactions s = new Transactions();
		s.setCreate_at(new Date());
		s.setUsers(u);
		
		assertThat(transactionService.create(s)).isNotNull();
	}
	
	@Test
	void createTransactionsJson() {
		ObjectMapper mapper = new ObjectMapper();
		String username = "tungngayngo";
		Users u = userService.findById(username);
		JsonNode user = mapper.valueToTree(u);
		JsonNode s = mapper.createObjectNode()
				.set("users", user);
		
		assertThat(transactionService.createJson(s)).isNotNull();
	}
	
	@Test
	void getCurrentAndPreviousMonth() {
		List<Object[]> list = transactionService.getCurrentAndPreviousMonth();
		assertThat(list).isNotNull();
	}
	
	@Test
	void getIncomeInRecentYears() {
		List<Object[]> list = transactionService.getIncomeInRecentYears();
		assertThat(list).isNotNull();
	}
	
	@Test
	void getTotalYear() {
		int year = 2023;
		double total = transactionService.getTotalYear(year);
		assertThat(total).isNotNull();
	}
	
	@Test
	void detailTransactionFindAll() {
		List<DetailTransactions> list = detailTransactionService.findAll();
		assertThat(list).isNotNull();
	}
	
	@Test
	void findAllByUser() {
		String username = "tungngayngo";
		Pageable page = PageRequest.of(0, 5);
		Page<DetailTransactions> list = detailTransactionService.findAllByUser(username, page);
		assertThat(list).isNotNull();
	}
	
	@Test
	void findAllDetailTransactionPay() {
		
		List<DetailTransactions> list = detailTransactionService.findAllDetailTransactionPay();
		assertThat(list).isNotNull();
	}
	
	@Test
	void findAllDetailTransactionPost() {
		
		List<DetailTransactions> list = detailTransactionService.findAllDetailTransactionPost();
		assertThat(list).isNotNull();
	}
	
	@Test
	void findByIdDetailTransaction() {
		List<DetailTransactions> list = detailTransactionService.findAll();
		DetailTransactions d = detailTransactionService.findById(list.get(0).getDetail_id());
		assertThat(d).isNotNull();
	}
	
	@Test
	void findByTransactionId() {
		String username = "tungngayngo";
		DetailTransactions d = detailTransactionService.findByTransactionId(username);
		assertThat(d).isNotNull();
	}
	
	@Test
	void createDetailTransactions() {
		Transactions s = transactionService.findById(35);
		DetailTransactions d = new DetailTransactions();
		d.setAccount_get(0);
		d.setBank_code("NCB");
		d.setFullname_get("tungngayngo");
		d.setPrice(20000);
		d.setTimer("10:46 PM");
		d.setTransactions_type(false);
		d.setTransactions_id(s);
		assertThat(detailTransactionService.create(d)).isNotNull();
	}
	
	@Test
	void createDetailTransactionsJson() {
		ObjectMapper mapper = new ObjectMapper();
		Transactions s = transactionService.findById(35);
		JsonNode sJson = mapper.valueToTree(s);
		JsonNode d = mapper.createObjectNode()
				.put("price", 20000)
				.put("transactions_type", true)
				.put("timer", "10:46 PM")
				.put("account_get", 0)
				.put("fullname_get", "tungngayngo")
				.put("bank_code", "NCB")
				.set("transactions_id", sJson);
		assertThat(detailTransactionService.createJson(d)).isNotNull();
	}
}
