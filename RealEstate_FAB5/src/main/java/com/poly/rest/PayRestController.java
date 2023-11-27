package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.Pay;
import com.poly.bean.Users;
import com.poly.service.PaymentService;
import com.poly.service.UsersService;
import com.poly.util.SessionService;

@CrossOrigin("*")
@RestController
public class PayRestController {

	@Autowired
	SessionService ss;
	
	@Autowired
	PaymentService payService;
	
	@Autowired
	UsersService userService;
	
	@RequestMapping("/rest/pay")
	public Pay getPayUser() {
		Users u = (Users) ss.getAttribute("user");
		return payService.findByID(u.getPay_id().getPay_id());
	}
	
	@PutMapping("/rest/set-money-pay")
	public Long setMoneyPay(@Param("user") String user, @Param("money") Long money) {
		Users u = userService.findById(user);
		Pay p = payService.findByID(u.getPay_id().getPay_id());
		if(money <= p.getPay_money()) {
			Long sum = p.getPay_money() - money;
			p.setPay_money(sum);
			payService.Update(p);
			return p.getPay_money();
		}else {
			throw new IllegalArgumentException("Ví tiền không đủ");
		}
		
	}

}
