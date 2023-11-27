package com.poly.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.bean.DetailTransactions;
import com.poly.bean.Post;
import com.poly.bean.ServicePack;
import com.poly.bean.Transactions;
import com.poly.bean.TypePropertys;
import com.poly.bean.Users;
import com.poly.service.AlbumsService;
import com.poly.service.DetailTransactionService;
import com.poly.service.PostService;
import com.poly.service.ServicePackService;
import com.poly.service.TransactionService;
import com.poly.service.TypePropertyService;
import com.poly.service.UsersService;
import com.poly.util.SessionService;

@Controller
public class AdminController {

	@Autowired
	UsersService userService;
	
	@Autowired
	ServicePackService ServicePackService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	AlbumsService albumsServcie;
	
	@Autowired
	TypePropertyService typePropertyService;
	
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
	@RequestMapping("/admin/users")
	public String getUsers(Model m, @RequestParam(defaultValue = "1") int page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Users> users = userService.findAll(pageable);
		
		m.addAttribute("users", users);
		m.addAttribute("u", new Users());
		return "admin/users";
	}
	
	@RequestMapping("/admin/users-new")
	public String setUserNew(Model m) {
		return "redirect:/admin/users";
	}
	
	@RequestMapping("/admin/user/findBy/{username}")
	public String getUsers(Model m, @PathVariable String username, @RequestParam(defaultValue = "1") int page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Users> users = userService.findAll(pageable);
		
		m.addAttribute("users", users);
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
	
	@RequestMapping("/admin/user/delete/{username}")
	public String setAccountUser(Model m, @PathVariable String username){
		Users u = userService.findById(username);
		u.setActive(false);
		userService.update(u);
		return "redirect:/admin/user/findBy/" + u.getUsername();
	}
	// User List
	
	// Post List
	@RequestMapping({"/admin/post","/admin/post-list"})
	public String getPostList(Model m, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Post> managerPost = postService.getPageAll(pageable);
		
		m.addAttribute("post", new Post());
		m.addAttribute("managerPost", managerPost);
		return "admin/post";
	}
	
	@RequestMapping("/admin/post-find")
	public String getPostList(Model m, @Param("id") Integer id, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Post> managerPost = postService.getPageAll(pageable);
		
		m.addAttribute("post", postService.getFindByid(id));
		m.addAttribute("albums", albumsServcie.findAlbumsByPostID(id));
		m.addAttribute("managerPost", managerPost);
		return "admin/post";
	}
	
	@RequestMapping("/admin/post-find-delete")
	public String getPostDelete(Model m, @Param("id") Integer id) {
		
		postService.SoftDeletePost(id);
		
		return "redirect:/admin/post";
	}
	
	@RequestMapping("/admin/post-find-update")
	public String getPostUpdate(Model m, @Param("id") Integer id) {
		Post p = postService.getFindByid(id);
		p.setDeletedAt(false);
		postService.Update(p);
		
		return "redirect:/admin/history-delete-post";
	}
	
	@RequestMapping("/admin/history-delete-post")
	public String getHistoryDeletePostList(Model m, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Post> managerPost = postService.getPostDelete(pageable);
		m.addAttribute("managerPost", managerPost);
		return "admin/historyDeletePost";
	}

	// Post List
	
	// Transactions List
	@RequestMapping({"/admin/transactions","/admin/transaction-list"})
	public String getTransactions(Model m, @RequestParam(defaultValue = "1") int page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<DetailTransactions> detailTransactions = detailTransactionService.findAll(pageable);
		m.addAttribute("transactions", detailTransactions);
		m.addAttribute("transaction", new DetailTransactions());
		return "admin/transactions";
	}
	
	@RequestMapping("/admin/transaction/findBy/{id}")
	public String getTransactions(Model m, @PathVariable("id") Integer id, @RequestParam(defaultValue = "1") int page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<DetailTransactions> detailTransactions = detailTransactionService.findAll(pageable);
		m.addAttribute("transactions", detailTransactions);
		m.addAttribute("transaction", detailTransactionService.findById(id));
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
	
	@GetMapping("/admin/delete-service-pack/{id}")
	public String deleteServicePack(Model m, @PathVariable("id") Integer id) {
		ServicePackService.Delete(id);
		return "redirect:/admin/service-pack";
	}
	// Service Pack 
	// Type Propertys
	@RequestMapping("/admin/type-property")
	public String getTypeProperty(Model m, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 6);
		Page<TypePropertys> typeProperty = typePropertyService.findPageAll(pageable);
		m.addAttribute("typeProperty", typeProperty);
		m.addAttribute("totalNumber", typeProperty.getTotalElements());
		return "admin/typePropertys";
	}
	
	@PostMapping("/admin/add-type-property")
	public String createTypeProperty(Model m, TypePropertys p) {
		typePropertyService.create(p);
		return "redirect:/admin/type-property";
	}
	
	@PostMapping("/admin/update-type-property")
	public String updateTypeProperty(Model m, TypePropertys p) {
		typePropertyService.update(p);
		return "redirect:/admin/type-property";
	}
	
	@GetMapping("/admin/delete-type-property/{id}")
	public String deleteTypeProperty(Model m, @PathVariable("id") Integer id) {
		typePropertyService.delete(id);
		return "redirect:/admin/type-property";
	}
	// Type Propertys
}
