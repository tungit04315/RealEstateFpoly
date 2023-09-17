package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

	@RequestMapping({"/admin","/admin/index"})
	public String getHome(Model m) {
		return "admin/index";
	}
	
	@RequestMapping({"/admin/users","/admin/user-list"})
	public String getUsers(Model m) {
		return "admin/users";
	}
	
	@RequestMapping({"/admin/post","/admin/post-list"})
	public String getPostList(Model m) {
		return "admin/post";
	}
	
	@RequestMapping({"/admin/transactions","/admin/transaction-list"})
	public String getTransactions(Model m) {
		return "admin/transactions";
	}
	
	@RequestMapping({"/admin/wallet","/admin/wallet-list"})
	public String getWalletList(Model m) {
		return "admin/wallet";
	}
	
	@RequestMapping("/admin/profile")
	public String getProfile(Model m) {
		return "admin/profile";
	}
}
