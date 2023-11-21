package com.poly.controller;
import java.text.SimpleDateFormat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.bean.DetailTransactions;
import com.poly.bean.Pay;
import com.poly.bean.Users;
import com.poly.service.DetailTransactionService;
import com.poly.service.PaymentService;
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
	DetailTransactionService detailTransactionService;
	
	@Autowired
	SessionService ss;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PaymentService Pay;
	// Home
	@RequestMapping({"/admin","/admin/index"})
	public String getHome(Model m) {

		List<DetailTransactions> listPay = detailTransactionService.findAllDetailTransactionPay();
		List<DetailTransactions> listPost = detailTransactionService.findAllDetailTransactionPost();
		 
		m.addAttribute("details", listPay);
		m.addAttribute("detailPost", listPost);
		m.addAttribute("u", ss.getAttribute("user"));
		return "admin/index";
	}
	// Home
	
	// User List
	@RequestMapping({"/admin/users","/admin/user-list"})
	public String getUsers(Model m) {

		m.addAttribute("users", userService.findAll());

		return "admin/users";
	}
	
	@RequestMapping("/admin/user/findBy/{username}")
	public String getUsers(Model m, @PathVariable String username) {
		m.addAttribute("users", userService.findAll());
		m.addAttribute("u", userService.findById(username));
		return "admin/users";
	}
	
	@PostMapping("/admin/user/update")
	public String getUsersUpdate(Model m, Users u) {
		m.addAttribute("users", userService.findAll());
		userService.update(u);
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "redirect:/admin/user/findBy/" + u.getUsername();
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
		List<Users> u = userService.findAll();
		m.addAttribute("pays", u);
		Users user = new Users();
		user.setPay_id(new Pay());
		m.addAttribute("user", user);
		return "admin/wallet";
	}
	@RequestMapping("/admin/wallet-new")
	public String getWalletNew(Model m) {
		return "redirect:/admin/wallet";
	}
	@RequestMapping("/find/users/{id}")
	public String getWalletList(Model m,@PathVariable("id") String username) {
		List<Users> u= userService.findAll();
		m.addAttribute("pays", u);
		
		Users user = userService.findById(username);
		m.addAttribute("user", user);
		return "admin/wallet";
}
	@RequestMapping("/admin/user-update")
	public String getWalletUpdate(Model m,@Param("username")String username, @Param("pay")long pay) {
		List<Users> u= userService.findAll();
		m.addAttribute("pays", u);
		
		Users user = userService.findById(username);
		m.addAttribute("user", user);
		
		Pay p = Pay.findByID(user.getPay_id().getPay_id());
		p.setPay_money(pay);
		Pay.Update(p);
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
