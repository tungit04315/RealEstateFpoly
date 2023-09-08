package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	//Home Page 
	@RequestMapping({"/home", "/"})
	public String getHome(Model m) {
		return "home/index";
	}
	//Home Page 
	
	//Post Page 
	@RequestMapping("/home/post")
	public String getPost(Model m) {
		return "home/post";
	}
	//Post Page 
	
	//Post Detail Page 
	@RequestMapping("/home/detail")
	public String getPostDetail(Model m) {
		return "home/detail";
	}
	//Post Detail Page 
	
	//Error Page 
	@RequestMapping("/home/error")
	public String getError(Model m) {
		return "error/404";
	}
	//Error Page
	
	//Contact Page
	@RequestMapping("/home/contact")
	public String getContact(Model m) {
		return "home/contact";
	}
	//Contact Page
	
	//Messager Page
	@RequestMapping("/home/messager")
	public String getMessager(Model m) {
		return "home/messager";
	}
	//Messager Page
	
	//POST List Page
	@RequestMapping("/home/manager/post")
	public String getManagerPost(Model m) {
		return "home/managerPost";
	}
	//POST List Page
	
	//Pay Page
	@RequestMapping("/home/manager/pay")
	public String getManagerPay(Model m) {
		return "home/pay";
	}
	//Pay Page
	
	//Profile Page
	@RequestMapping("/home/manager/profile")
	public String getManagerProfile(Model m) {
		return "home/profile";
	}
	//Profile Page
}
