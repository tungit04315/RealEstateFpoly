package com.poly.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.bean.DetailTransactions;
import com.poly.bean.ServicePack;
import com.poly.bean.Users;
import com.poly.service.DetailTransactionService;
import com.poly.service.ServicePackService;
import com.poly.service.TransactionService;
import com.poly.service.UsersService;
import com.poly.util.SessionService;

@Controller
public class AdminController {

	@Autowired
	UsersService userService;
	
	@Autowired
	ServicePackService ServicePackService;
	
	@Autowired
	DetailTransactionService detailTransactionService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	SessionService ss;
	
	// Home
	@RequestMapping({"/admin","/admin/index"})
	public String getHome(Model m) {
		LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
		
		List<DetailTransactions> listPay = detailTransactionService.findAllDetailTransactionPay();
		List<DetailTransactions> listPost = detailTransactionService.findAllDetailTransactionPost();
		List<Object[]> listCPMonth = transactionService.getCurrentAndPreviousMonth();
		
		m.addAttribute("totalYear", transactionService.getTotalYear(currentYear));
		m.addAttribute("listCPMonth", listCPMonth);
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
		return "admin/wallet";
	}
	// Wallet List
	
	// Profile User (Admin)
	@RequestMapping("/admin/profile")
	public String getProfile(Model m) {
		m.addAttribute("u", userService.findById("tungngayngo")); 
		return "admin/profile";
	}
	
	@RequestMapping("/admin/profile-edit")
	public String setProfile(Model m, Users u) {
		userService.update(u);
		ss.setAttribute("user", u);
		return "redirect:/admin/profile";
	}
	// Profile User (Admin)
	
	// Service Pack 
	@RequestMapping("/admin/service-pack")
	public String getServicePack(Model m, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<ServicePack> service = ServicePackService.getFindAll(pageable);
		m.addAttribute("service", service);
		m.addAttribute("totalNumber", service.getTotalElements());
		return "admin/servicePack";
	}
	
	@PostMapping("/admin/add-service-pack")
	public String addServicePack(Model m, ServicePack s) {
		ServicePackService.Create(s);
		return "redirect:/admin/service-pack";
	}
	
	@PostMapping("/admin/update-service-pack")
	public String updateServicePack(Model m, ServicePack s) {
		ServicePackService.Update(s);
		return "redirect:/admin/service-pack";
	}
	
	@DeleteMapping("/admin/delete-service-pack/{id}")
	public String deleteServicePack(Model m, @PathVariable("id") Integer id) {
		ServicePackService.Delete(id);
		return "redirect:/admin/service-pack";
	}
	// Service Pack 
}
