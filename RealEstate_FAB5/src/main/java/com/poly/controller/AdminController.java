package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.bean.Users;
import com.poly.service.UsersService;
import com.poly.util.ParamService;
import com.poly.util.SessionService;

@Controller
public class AdminController {

	@Autowired
	ParamService paramService;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	SessionService ss;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		m.addAttribute("u", userService.findById("Phatbieu"));
		ss.setAttribute("user",userService.findById("Phatbieu"));
		return "admin/profile";
	}
	
	@RequestMapping("/admin/profile-edit")
	public String setProfile(Model m, Users u) {
		userService.update(u);
		ss.setAttribute("user", u);
		return "redirect:/admin/profile";	
	}
	@RequestMapping("/admin/ChangePass")
	public String setChangePass(Model m, Users u) {
		Users user = (Users) ss.getAttribute("user");

		String passmoi = paramService.getString("passmoi", "");
		String nhaplaipassmoi = paramService.getString("nhaplaipassmoi", "");

		System.out.println(u.getPasswords());
		System.out.println(user.getPasswords());

			if (passmoi.equalsIgnoreCase(nhaplaipassmoi)) {
				u.setPasswords(passwordEncoder.encode(passmoi));// db
				user.setPasswords(passwordEncoder.encode(passmoi));// session
				userService.update(user);
				m.addAttribute("successPass", true);
				return "redirect:/admin/profile";
			} else {
				m.addAttribute("errorPass", true);
				return "redirect:/admin/profile";
			}
	}
	// Profile User (Admin)
}
