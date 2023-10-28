package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.bean.Auth;
import com.poly.bean.Users;
import com.poly.service.AuthService;
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
	
	@Autowired
	AuthService authService;

	// Home
	@RequestMapping({"/admin","/admin/index"})
	public String getHome(Model m) {
		return "admin/index";
	}
	// Home
	
	// User List
	@RequestMapping({"/admin/users","/admin/user-list"})
	public String getUsers(Model m) {
		m.addAttribute("users",userService.findAll());
		

		return "admin/users";
	}
	
	//delete
	@RequestMapping("/admin/user-delete")
	public String deleteUsers(Model m, @RequestParam("id") String username) {
		authService.delete(username);
		userService.delete(username);
		return "redirect:/admin/users";
	}
	
	//chi tiết-123
	@RequestMapping("/admin/user-detail")
	public String fillUser(Model m, @RequestParam("id") String username) {
		Users user = userService.findById(username);
		Auth auth = authService.findByUserID(username);
		m.addAttribute("user", user);
		m.addAttribute("roles", auth.getRoles().getRoles_id());
		m.addAttribute("users",userService.findAll());
		return "admin/users";
	} 
	
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
		return "admin/wallet";
	}
	@RequestMapping("/find/users/{id}")
	public String getWalletList(Model m,@PathVariable("id") String username) {
		List<Users> u= userService.findAll();
		m.addAttribute("pays", u);
		
		Users user = userService.findById(username);
		m.addAttribute("user", user);
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
