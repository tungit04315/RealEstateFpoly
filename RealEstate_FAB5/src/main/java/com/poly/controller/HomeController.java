package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping({"/home", "/"})
	public String getHome(Model m) {
		return "home/index";
	}
	
	@RequestMapping("/home/post")
	public String getPost(Model m) {
		return "home/post";
	}
	
	@RequestMapping("/home/detail")
	public String getPostDetail(Model m) {
		return "home/detail";
	}
	
	@RequestMapping("/home/error")
	public String getError(Model m) {
		return "error/404";
	}
	
	@RequestMapping("/home/contact")
	public String getContact(Model m) {
		return "home/contact";
	}
	
	@RequestMapping("/home/messager")
	public String getMessager(Model m) {
		return "home/messager";
	}
	
	@RequestMapping("/home/manager/post")
	public String getManagerPost(Model m) {
		return "home/managerPost";
	}
	
	@RequestMapping("/home/manager/pay")
	public String getManagerPay(Model m) {
		return "home/pay";
	}
	
	@RequestMapping("/home/manager/profile")
	public String getManagerProfile(Model m) {
		return "home/profile";
	}
}
