package com.poly.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.bean.Pay;
import com.poly.bean.Transactions;
import com.poly.bean.Users;
import com.poly.dao.PayDAO;
import com.poly.service.PaymentService;
import com.poly.service.TransactionService;
import com.poly.service.UsersService;
import com.poly.util.SessionService;

@Controller
public class HomeController {

	@Autowired
	SessionService ss;

	@Autowired
	PaymentService payService;

	@Autowired
	HttpServletRequest req;

	@Autowired
	UsersService userService;

	@Autowired
	PayDAO payDao;

	@Autowired
	TransactionService transactionService;

	
	//Home Page 
	@RequestMapping({"/home", "/"})
	public String getHome(Model m) {
		return "home/index";
	}
	// Home Page

	// Post Page
	@RequestMapping("/home/post")
	public String getPost(Model m) {
		return "home/post";
	}
	// Post Page

	// Post Detail Page
	@RequestMapping("/home/detail")
	public String getPostDetail(Model m) {
		return "home/detail";
	}
	// Post Detail Page

	// Error Page
	@RequestMapping("/home/error")
	public String getError(Model m) {
		return "error/404";
	}
	// Error Page

	// Contact Page
	@RequestMapping("/home/contact")
	public String getContact(Model m) {
		return "home/contact";
	}
	// Contact Page

	// Messager Page
	@RequestMapping("/home/messager")
	public String getMessager(Model m) {
		return "home/messager";
	}
	// Messager Page

	// POST List Page
	@RequestMapping("/home/manager/post")
	public String getManagerPost(Model m) {
		return "home/managerPost";
	}
	// POST List Page

	// Pay Page
	@RequestMapping("/home/manager/pay")
	public String getManagerPay(Model m) {
		return "home/pay";
	}

	@RequestMapping("/home/manager/pay-create")
	public String getPayNumber(Model m) {

		Users u = ss.getAttribute("user");

		Transactions transaction = new Transactions();
		transaction.setUsers(u);
		transaction.setCreate_at(new Date());

		transactionService.create(transaction);

		
		Transactions transactionFind = transactionService.findByUserId("user1");
		m.addAttribute("orderInfo", transactionFind.getTransactions_id());
		return "home/payNumber";
	}

	@RequestMapping("/home/manager/pay-create/check")
	public String getCreate(@RequestParam("amount") Integer amount
			,@RequestParam("orderInfo") String orderInfo) {

		String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
		String payURL = payService.createOrder(amount, orderInfo, baseUrl);
		return "redirect:" + payURL;
	}
	
	@RequestMapping("/home/manager/pay-create/result")
	public String getResult(Model m) {
		
		int status = payService.orderDetail(req);
        String totalPrice = req.getParameter("vnp_Amount");

        Long money = Long.parseLong(totalPrice)/100;

        if(status == 1) {
//        	Users u = ss.getAttribute("user");
//        	Pay p = payDao.findById(u.getPay_id().getPay_id()).get();
        	
        	Users u = userService.findById("user1");
        	Pay p = payService.findByID(u.getPay_id().getPay_id());
        	Long pay = p.getPay_money() + money;
        	p.setPay_money(pay);
        	payDao.save(p);
        	return "success";
        }else {
        	return "failder";
        }
        
	}
	// Pay Page

	// Profile Page
	@RequestMapping("/home/manager/profile")
	public String getManagerProfile(Model m) {
		Users u = (Users) ss.getAttribute("user");
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "home/profile";
	}
	// Profile Page
}
