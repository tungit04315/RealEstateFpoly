package com.poly.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.bean.Albums;
import com.poly.bean.DetailTransactions;
import com.poly.bean.Pay;
import com.poly.bean.Post;
import com.poly.bean.Transactions;
import com.poly.bean.Users;
import com.poly.dao.PayDAO;
import com.poly.service.AlbumsService;
import com.poly.service.DetailTransactionService;
import com.poly.service.PaymentService;
import com.poly.service.PostService;
import com.poly.service.TransactionService;
import com.poly.service.UsersService;
import com.poly.util.MailerService;
import com.poly.util.ParamService;
import com.poly.util.SessionService;
import com.poly.util.SmsService;

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
		Post p = postService.getFindByid(8);
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

		Transactions transaction = transactionService.findById(Integer.parseInt(orderInfo));
		
		DetailTransactions detail = new DetailTransactions();
		detail.setPrice(amount);
		detail.setTransactions_id(transaction);
		detail.setTransactions_type(true);
		
		detailTransactionService.create(detail);
		
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
	// Pay Page

	// Profile Page
	@RequestMapping("/home/manager/profile")
	public String getManagerProfile(Model m) {
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
		List<Post> post_list = postService.getPostExpired();
		
		m.addAttribute("post_list", post_list);
		return "home/managerPostExpired";
	}
	//Post deadline extension
}
