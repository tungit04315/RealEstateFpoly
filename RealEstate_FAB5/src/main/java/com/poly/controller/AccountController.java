package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

	@GetMapping("/login")
	public String getLogin() {
		return "account/login";
	}
	
	@GetMapping("/forget-password")
	public String getForgetPassword() {
		return "account/forgetPassword";
	}
	
	@GetMapping("/OTP")
	public String getOTP() {
		return "account/otp";
	}
	
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "account/changePassword";
	}
}
