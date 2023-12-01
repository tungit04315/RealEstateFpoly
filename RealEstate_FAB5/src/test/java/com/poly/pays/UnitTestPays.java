package com.poly.pays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.poly.bean.Pay;
import com.poly.service.PaymentService;

@SpringBootTest
public class UnitTestPays {

	@Autowired
	PaymentService payService;
	
	
	
	@Test
	void getPayFindBy() {
		assertThat(payService.findByID(1)).isNotNull();
	}
	
	@Test
	void getPayDesc() {
		assertThat(payService.findByTop1Desc()).isNotNull();
	}
	
	@Test
	void createPay() {
		Pay p = new Pay();
		assertThat(payService.Create(p)).isNotNull();
	}
	
	@Test
	void updatePay() {
		Pay p = payService.findByID(9);
		p.setPay_money((long)20000);
		assertThat(payService.Update(p)).isNotNull();
	}
}
