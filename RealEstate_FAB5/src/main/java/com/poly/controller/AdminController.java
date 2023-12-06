package com.poly.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.poly.bean.*;
import com.poly.service.*;
import com.poly.util.*;

@Controller
public class AdminController {

	@Autowired
	ParamService paramService;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	RanksService rankService;

	@Autowired
	PaymentService payService;
	
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PaymentService Pay;
	
	
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
	//hiển thị thông tin người dùng trừ thông tin admin
	@RequestMapping("/admin/users")
	public String getUsers(Model m, @RequestParam(defaultValue = "1") int page) {
		Users u = ss.getAttribute("user");
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Users> users = userService.findAll(u.getUsername(), pageable);
		
		m.addAttribute("users", users);
		m.addAttribute("u", new Users());
		return "admin/users";
	}
	
	//làm mới form
	@RequestMapping("/admin/users-new")
	public String setUserNew(Model m) {
		return "redirect:/admin/users";
	}
	// chi tiết
	@RequestMapping("/admin/user/findBy/{username}")
	public String getUsers(Model m, @PathVariable String username, @RequestParam(defaultValue = "1") int page) {
		Users u = ss.getAttribute("user");
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Users> users = userService.findAll(u.getUsername(), pageable);
		
		m.addAttribute("users", users);
		m.addAttribute("u", userService.findById(username));
		return "admin/users";
	}
	//cập nhạt
	@PostMapping("/admin/user/update")
	public String getUsersUpdate(Model m, Users u) {
		m.addAttribute("users", userService.findAll());
		Users us = userService.findById(u.getUsername());
		Pay p = payService.findByID(us.getPay_id().getPay_id());
		Ranks r = rankService.findById(us.getRanks_id().getRanks_id());
		u.setPay_id(p);
		u.setRanks_id(r);
		u.setActive(true);
		userService.update(u);
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "redirect:/admin/user/findBy/" + u.getUsername();
	}
	
	//chặn người dùng
	@RequestMapping("/admin/user/delete/{username}")
	public String setAccountUser(Model m, @PathVariable String username){
		Users u = userService.findById(username);
		u.setActive(false);
		u.setCreate_block(new Date());
		userService.update(u);
		return "redirect:/admin/user/findBy/" + u.getUsername();
	}
	// User List
	
	// Post List
	//hiện thị thông tin bài viết
	@RequestMapping({"/admin/post","/admin/post-list"})
	public String getPostList(Model m, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Post> managerPost = postService.getPageAll(pageable);
		
		m.addAttribute("post", new Post());
		m.addAttribute("managerPost", managerPost);
		return "admin/post";
	}
	//hiển thị chi tiết bài viết
	@RequestMapping("/admin/post-find")
	public String getPostList(Model m, @Param("id") Integer id, @RequestParam(defaultValue = "1") Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 4);
		Page<Post> managerPost = postService.getPageAll(pageable);
		
		m.addAttribute("post", postService.getFindByid(id));
		m.addAttribute("albums", albumsServcie.findAlbumsByPostID(id));
		m.addAttribute("managerPost", managerPost);
		return "admin/post";
	}
	
	//xóa bài viết
	@RequestMapping("/admin/post-find-delete")
	public String getPostDelete(Model m, @Param("id") Integer id) {
		
		postService.SoftDeletePost(id);
		
		return "redirect:/admin/post";
	}
	
	// phục hồi bài viết
	@RequestMapping("/admin/post-find-update")
	public String getPostUpdate(Model m, @Param("id") Integer id) {
		Post p = postService.getFindByid(id);
		p.setDeletedAt(false);
		postService.Update(p);
		
		return "redirect:/admin/history-delete-post";
	}
	//xóa bài viết trong historyDelete
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
	// hiển thị thông tin cá nhân user
	@RequestMapping("/admin/profile")
	public String getProfile(Model m) {
		Users u = ss.getAttribute("user");
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "admin/profile";
	}
	
	// cập nhật thông tin
	@RequestMapping("/admin/profile-edit")
	public String setProfile(Model m, Users u) {
		Users uFind = userService.findById(u.getUsername());
		Pay p = payService.findByID(uFind.getPay_id().getPay_id());
		Ranks r = rankService.findById(uFind.getRanks_id().getRanks_id());
		u.setPay_id(p);
		u.setRanks_id(r);
		u.setActive(true);
		userService.update(u);
		ss.setAttribute("user", u);
		return "redirect:/admin/profile";	
	}
	
	//thay đổi mật khẩu
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
