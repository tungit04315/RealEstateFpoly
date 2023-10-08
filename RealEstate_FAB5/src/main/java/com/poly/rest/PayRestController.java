package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
//		Users uFind = userService.findById(u.getUsername());
//		System.out.println(uFind.getPay_id().getPay_id());
		return payService.findByID(u.getPay_id().getPay_id());
	}
	
//	@RequestMapping("/rest/pay")
//	public Integer getPayUser() {
//		
//		Users u = (Users) ss.getAttribute("user");
//		System.out.println(u);
//		Users uFind = userService.findById(u.getUsername());
//		return uFind.getPay_id().getPay_id();
//	}
}
