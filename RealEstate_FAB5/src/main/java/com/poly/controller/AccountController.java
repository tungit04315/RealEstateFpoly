package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

	//Login
	@GetMapping("/login")
	public String getLogin() {
		return "account/login";
	}
	//Login
	
	//Forget Password
	@GetMapping("/forget-password")
	public String getForgetPassword() {
		return "account/forgetPassword";
	}
	//Forget Password
	
	//OTP
	@GetMapping("/OTP")
	public String getOTP() {
		return "account/otp";
	}
	//OTP
	
	//Change Password
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "account/changePassword";
	}
	//Change Password
}
