package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.poly.bean.Users;
import com.poly.util.SessionService;
import com.poly.service.UsersService;


@Controller
public class AccountController {

	@Autowired
	UsersService userService;
	
	@Autowired
	SessionService ss;
	
	//Captcha
	@Value("${recaptcha.secret}")
	private String recaptchaSecret;
	
	@Value("${recaptcha.url}")
	private String recaptchaSeverURL;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	//Captcha
	
	// Đăng ký - Captcha
	@PostMapping("/signup")
	public String login(HttpServletRequest request, Model model) throws IOException{
		if(true) {
			model.addAttribute("successRegister", "true");
			return "account/login";
		}
		
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		verifyReCAPTCHA(gRecaptchaResponse);
		model.addAttribute("successRegister", "true");
		return "account/login";
	}
	
	private void verifyReCAPTCHA(String gRecaptchaResponse) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("secret", recaptchaSecret);
		map.add("response", gRecaptchaResponse);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity(recaptchaSeverURL, request, String.class);
		
		System.out.println(response);
	}
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
	
	//Cập nhật tài khoản
	@PostMapping("/account/changeprofile")
	public String ChangeProfile(Model m, Users u, @Param("username") String username) {
		userService.update(u);
		ss.setAttribute("user", u);
//		Users user = (Users) ss.getAttribute("users");
//		m.addAttribute("u", userService.findById(user.getUsername()));
		return "redirect:/home/manager/profile";
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
