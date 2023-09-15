package com.poly.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.service.UsersService;

@Controller
public class AccountController {

	@Autowired
	UsersService userService;
	
	// Đăng nhập
	@GetMapping("/login")
	public String getLogin() {
		return "account/login";
	}
	
	// Đăng nhập thành công
	@RequestMapping("/login/action/success")
	public String postLogin(Model m) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> authList = new ArrayList<>();
		
		//Check if the user is authenticated
		if(authentication != null && authentication.isAuthenticated()) {
			List<String> roleNames = userService.getRolesByUsername(authentication.getName());
			
			for(String roleName : roleNames) {
				authList.add("ROLE_" + roleName);
			}
		}
		
		if(authList.contains("ROLE_ADMIN")) {
			return "/admin";
		}else {
			return "redirect:/home";
		}
	}
	
	// Đăng nhập thất bại
	@RequestMapping("/login/action/error")
	public String loginError(Model model) {
		return "redirect:/login";
	}
	
	// Đăng nhập
	
	// Đăng xuất
	@RequestMapping("/logout/success")
	public String logoutSuccess() {
		return "redirect:/login";
	}
	// Đăng xuất
	
	//Quên mật khẩu
	@GetMapping("/forget-password")
	public String getForgetPassword() {
		return "account/forgetPassword";
	}
	//Quên mật khẩu
	
	//OTP
	@GetMapping("/OTP")
	public String getOTP() {
		return "account/otp";
	}
	//OTP
	
	//Đổi mật khẩu
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "account/changePassword";
	}
	//Đổi mật khẩu
}
