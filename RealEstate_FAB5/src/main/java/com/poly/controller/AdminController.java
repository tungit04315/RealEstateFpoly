package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	// Home
	@RequestMapping({"/admin","/admin/index"})
	public String getHome(Model m) {
		return "admin/index";
	}
	// Home
	
	// User List
	@RequestMapping({"/admin/users","/admin/user-list"})
	public String getUsers(Model m) {
		return "admin/users";
	}
	// User List
	
	// Post List
	@RequestMapping({"/admin/post","/admin/post-list"})
	public String getPostList(Model m) {
		return "admin/post";
	}
	// Post List
	
	// Transactions List
	@RequestMapping({"/admin/transactions","/admin/transaction-list"})
	public String getTransactions(Model m) {
		return "admin/transactions";
	}
	// Transactions List
	
	// Wallet List
	@RequestMapping({"/admin/wallet","/admin/wallet-list"})
	public String getWalletList(Model m) {
		return "admin/wallet";
	}
	// Wallet List
	
	// Profile User (Admin)
	@RequestMapping("/admin/profile")
	public String getProfile(Model m) {
		return "admin/profile";
	}
	// Profile User (Admin)
}
