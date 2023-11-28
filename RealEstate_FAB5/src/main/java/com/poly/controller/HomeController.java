package com.poly.controller;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.poly.bean.*;
import com.poly.service.*;
import com.poly.util.*;

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
	TransactionService transactionService;
	
	@Autowired
	DetailTransactionService detailTransactionService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	AlbumsService albumService;
	
	@Autowired
	ParamService paramService;
	
	@Autowired
	MailerService mailService;

	@Autowired
	SmsService smsService;
	
	@Autowired
	RanksService rankservice;
	
	//Home Page 
	@RequestMapping({"/home", "/"})
	public String getHome(Model m) {
		String hienthi = ss.getAttribute("visible");
		String tb = ss.getAttribute("thongbao");
		
		m.addAttribute("visible", hienthi);
		m.addAttribute("thongbao", tb);
		
		return "home/index";
	}
	// Home Page

	// Post Page
	@RequestMapping("/home/post")
	public String getPost(Model m) {
		Users u = (Users) ss.getAttribute("user");
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "home/post";
	}
	
	@RequestMapping("/home/post-update")
	public String getPostId(Model m) {
		Users u = (Users) ss.getAttribute("user");
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "home/postUpdate";
	}
	// Post Page

	// Post Detail Page
	@RequestMapping("/home/detail")
	public String getPostDetail(Model m, @Param("id") Integer id) {
		Post p = postService.getFindByid(id);
		List<Albums> albums = albumService.findAlbumsByPostID(p.getPost_id());
		m.addAttribute("post", p);
		m.addAttribute("albums", albums);
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
	@RequestMapping("/home/manager/contact")
	public String getContact(Model m) {
		return "home/contact";
	}
	
	@RequestMapping("/home/contact-email")
	public String getContactEmail(Model m) {
		String to = paramService.getString("to", "");
		String fullNameTo = paramService.getString("fullNameTo", "");
		String phone = paramService.getString("phone", "");
		String fullNameFrom = paramService.getString("fullNameFrom", "");
		String subject = paramService.getString("subject", "");
		String content = paramService.getString("content", "");
		
		try {
			mailService.sendEmailContact(to, subject, content, fullNameTo, fullNameFrom, phone);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "home/contact";
	}
	
	@RequestMapping("/home/contact-sms")
	public String getContactSms(Model m) {
		String fullName = paramService.getString("fullname", "");
		String content = paramService.getString("content", "");
		String phone = paramService.getString("phone", "");
		
		String phoneVN = "+84" + phone.substring(1);
		String body = "Xin chào anh, tôi là " + fullName + ", " + content;
		smsService.sendSms(phoneVN, body);
		return "home/contact";
	}
	// TEST COMMIT
	// Contact Page

	// Messager Page
	@RequestMapping("/home/manager/messager")
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

		
		Transactions transactionFind = transactionService.findByUserId(u.getUsername());
		m.addAttribute("orderInfo", transactionFind.getTransactions_id());
		return "home/payNumber";
	}

	@RequestMapping("/home/manager/pay-create/check")
	public String getCreate(@RequestParam("amount") Integer amount
			,@RequestParam("orderInfo") String orderInfo) {

		
		
		String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort();
		String payURL = payService.createOrder(amount, String.valueOf(orderInfo), baseUrl);
		return "redirect:" + payURL;
	}
	
	@RequestMapping("/home/manager/pay-create/result")
	public String getResult(Model m) {
		
		int status = payService.orderDetail(req);
        String totalPrice = req.getParameter("vnp_Amount");
        String orderInfo = req.getParameter("vnp_OrderInfo");
        String bankCode = req.getParameter("vnp_BankCode");

        Long money = Long.parseLong(totalPrice)/100;

        if(status == 1) {
        	Date today = new Date();
    		SimpleDateFormat formar = new SimpleDateFormat("hh:mm:ss aa");
    		Users u = ss.getAttribute("user");
    		
    		Transactions transaction = transactionService.findById(Integer.parseInt(orderInfo));
    		
    		DetailTransactions detail = new DetailTransactions();
    		detail.setPrice(money);
    		detail.setTransactions_id(transaction);
    		detail.setTransactions_type(true);
    		detail.setTimer(formar.format(today));
    		detail.setAccount_get(u.getPay_id().getPay_id());
    		detail.setFullname_get(u.getFullname());
    		detail.setBank_code(bankCode);
    		
    		detailTransactionService.create(detail);
        	//Users uFind = userService.findById(u.getUsername());
        	Pay p = payService.findByID(u.getPay_id().getPay_id());
        	Long pay = p.getPay_money() + money;
        	p.setPay_money(pay);
        	payService.Create(p);
        	
        	return "redirect:/payment-success";
        }else {
        	return "failder";
        }
        
	}
	
	@GetMapping("/payment-success")
	public String paymentSuccess(Model m) {
		m.addAttribute("visible", "true");
		return "home/payNumber";
	}
	
	@RequestMapping("/home/manager/history-transactions")
	public String getHistoryTransaction(Model m) {
		Users u = ss.getAttribute("user");
		m.addAttribute("list", detailTransactionService.findAllByUser(u.getUsername()));
		return "home/historyTransactions";
	}
	// Pay Page

	// Profile Page
	@RequestMapping("/home/manager/profile")
	public String getManagerProfile(Model m) {
		Users u = (Users) ss.getAttribute("user");
		m.addAttribute("u", userService.findById(u.getUsername()));
		m.addAttribute("rank", rankservice.findById(u.getRanks_id().getRanks_id()));
		String hienthi = ss.getAttribute("visible");
		String tb = ss.getAttribute("thongbao");
		
		m.addAttribute("visible", hienthi);
		m.addAttribute("thongbao", tb);
		return "home/profile";
	}
	
	@RequestMapping("/home/manager/profile-action")
	public String getManagerProfileAction(Model m) {
		Users u = (Users) ss.getAttribute("user");
		m.addAttribute("u", userService.findById(u.getUsername()));
		return "home/profile";
	}
	// Profile Page
	
	//Manager Likes Post
	@RequestMapping("/home/manager/likes")
	public String getManagerLikes(Model m) {
		return "home/managerLikes";
	}
	//Manager Likes Post
	
	//Post deadline extension
	@RequestMapping("/home/manager/post-deadline-extension")
	public String setPostDeadlineExtension(Model m) {
		Users u = ss.getAttribute("user");
		List<Post> post_list = postService.getPostExpired(u.getUsername());
		
		m.addAttribute("post_list", post_list);
		return "home/managerPostExpired";
	}
	
	@RequestMapping("/home/manager/post-deadline-extension-action")
	public String setPostDeadlineExtensionAction(Model m, @RequestParam("id") Integer id) {
		LocalDate now = LocalDate.now();
		LocalDate lastDay = now.plusDays(7);
		
		Post p = postService.getFindByid(id);
		Users u = ss.getAttribute("user");
		Users uFind = userService.findById(u.getUsername());
		
		p.setActive(true);
		p.setEnd_date(Date.from(lastDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		double money = p.getServices_id().getServices_price() * 1000;
		double total = u.getPay_id().getPay_money() - money;
		
		uFind.getPay_id().setPay_money((long) total);
		
		postService.Update(p);
		userService.update(uFind);

		return "redirect:/home/manager/post-deadline-extension";
	}
	
	@RequestMapping("/home/manager/delete-post")
	public String setDeletePost(Model m, @RequestParam("id") Integer id) {
		postService.SoftDeletePost(id);
		return "redirect:/home/manager/post-deadline-extension";
	}
	
	@RequestMapping("/home/manager/history-delete-post")
	public String getHistoryDeletePost(Model m) {
		Users u = ss.getAttribute("user");
		m.addAttribute("list", postService.getPostDelete(u.getUsername()));
		return "home/historyDeletePost";
	}
	//Post deadline extension
}
