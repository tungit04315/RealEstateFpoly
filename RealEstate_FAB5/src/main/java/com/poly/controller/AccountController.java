package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.poly.bean.Auth;
import com.poly.bean.Pay;
import com.poly.bean.Ranks;
import com.poly.bean.Roles;
import com.poly.bean.Users;
import com.poly.util.MailerService;
import com.poly.util.ParamService;
import com.poly.util.SessionService;
import com.poly.util.SmsService;
import com.poly.util.ValidatorUtil;

import ch.qos.logback.core.joran.conditional.IfAction;

import com.poly.service.AuthService;
import com.poly.service.PaymentService;
import com.poly.service.RanksService;
import com.poly.service.RoleService;
import com.poly.service.UsersService;
@Controller
public class AccountController {
	@Autowired
	RoleService roleService;

	@Autowired
	RanksService rankService;
	
	@Autowired
	AuthService authService;

	@Autowired
	UsersService userService;

	@Autowired
	SessionService ss;

	@Autowired
	ValidatorUtil validator;

	@Autowired
	MailerService mailService;

	@Autowired
	SmsService smsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	ParamService paramService;

	@Autowired
	PaymentService payService;
	
	// Captcha
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
	// Captcha

	// Đăng ký - Captcha
	@PostMapping("/signup/action")
	public String Register(Model m, Users u, @Param("username") String username, @Param("email") String email,@Param("phone") String phone) throws MessagingException {
		// System.out.println("xuất mẫu:"+userService.findById(username));
		Users ufind = userService.findByEmailOrPhone(email, null);
		Users ufindsdt = userService.findByEmailOrPhone(null, phone);
		Users findID = userService.findById(username);
		if(findID!=null) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Tên đăng nhập đã được đăng ký");
		}else
		if(ufindsdt != null) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Số điện thoại đã được sử dụng");
		}else
		if (ufind != null) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Email đã được đăng ký");
		}else {
			//payment
			Pay newpay = new Pay();
			newpay.setPay_money((long)0.00);
			payService.Create(newpay);
			Pay payFind =  payService.findByTop1Desc();
			
			//rank
			Ranks rank = rankService.findById(1);

			String password = paramService.getString("passwords", "");
			u.setPasswords(passwordEncoder.encode(password));
			u.setPay_id(payFind);
			u.setRanks_id(rank);
			u.setActive(false);
			userService.create(u);

			Auth uAuth = new Auth();
			uAuth.setUsers(u);
			uAuth.setRoles(roleService.findbyId("user"));
			authService.create(uAuth);
			ss.setAttribute("usermail", u.getEmail());

			//gui mail kich hoat tai khoan
			mailService.sendMailConfirm(u.getEmail(), "Kích hoạt tài khoản", u.getFullname(), null);
			//gui mail kich hoat tai khoan
			
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Đăng ký thành công!!");
		}
		return "account/login";
	}

	@GetMapping("/account/cofirm")
	public String GetAccountCofirm() {
		String email = ss.getAttribute("usermail");
		Users users = userService.findByEmailOrPhone(email, null);
		users.setActive(true);
		userService.update(users);
		
		return "redirect:/login";
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
	// Đăng ký - Captcha

	// Đăng nhập
	@GetMapping("/login")
	public String getLogin() {
		return "account/login";
	}

	// Đăng nhập thất bại
	@RequestMapping("/login/action/error")
	public String loginError(Model m) throws MessagingException {
		Users u = userService.findByEmailOrPhone(ss.getAttribute("usermail"), null);
		boolean chkUser = ss.getAttribute("checkUser");
		if(chkUser==false) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Tên đăng nhập không tồn tại");
			return "account/login";
		}else {
			boolean chkPass = ss.getAttribute("checkPass");
			if(chkPass==false) {
					m.addAttribute("visible", "true");
					m.addAttribute("thongbao", "Sai mật khẩu");
					u.setFail_login(u.getFail_login()+1);
					userService.update(u);
				return "account/login";
			}
		boolean blckAc =ss.getAttribute("BlockAcc");
		//System.out.println(chkUser);
		if (blckAc==true) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Tài khoản của bạn đã bị khoá! Hãy chọn quên mật khẩu!");
			return "account/login";
		}
		boolean chkActive =ss.getAttribute("checkActive");
		if(chkActive==false && chkUser==true) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Tài khoản chưa được kích hoạt bằng email");
			
			ss.setAttribute("checkUser", false);
			ss.setAttribute("checkActive", true);
			//gui mail kich hoat tai khoan
			mailService.sendMailConfirm(u.getEmail(), "Kích hoạt tài khoản", u.getFullname(), null);
			//gui mail kich hoat tai khoan
			return "account/login";
			}else {
				m.addAttribute("visible", "true");
				m.addAttribute("thongbao", "Tên đăng nhập không tồn tại");
				return "account/login";
			}
		}
	}



	@RequestMapping("/login/action/success")
	public String postLogin(Model m) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> authList = new ArrayList<>();

		// Check if the user is authenticated
		if (authentication != null && authentication.isAuthenticated()) {
			//System.out.println(authentication+ "197");
			//System.out.println(authentication.isAuthenticated() + "197");
			//System.out.println(authentication.getName() + "197");
			List<String> roleNames = userService.getRolesByUsername(authentication.getName());
			//System.out.println(roleNames);
			
			for (String roleName : roleNames) {
				authList.add("ROLE_" + roleName);
			}
		}
		//Users u = userService.findByEmailOrPhone(ss.getAttribute("usermail"), null);
		//u.setFail_login(u.getFail_login()+1);
		//System.out.println(authList);
		Users u = userService.findByEmailOrPhone(ss.getAttribute("usermail"), null);
		u.setFail_login(0);
		userService.update(u);
		if (authList.contains("ROLE_admin")) {
			return "redirect:/admin";
		} else {
			return "redirect:/home";
		}
	}
	// Đăng nhập
	
	
	//Đổi mật khẩu trong profile
	@GetMapping("/doimk")
	public String doiMK() {
		return "home/profile_pass";
	}
	// Cập nhật thông tin tài khoản
	@PostMapping("/profile/changeprofile")
	public String ChangeProfile(Model m, Users u, @Param("username") String username) {
//		Users user = (Users) ss.getAttribute("users");
//		m.addAttribute("u", userService.findById(user.getUsername()));
		Users us = userService.findById(u.getUsername());
		Pay p = payService.findByID(us.getPay_id().getPay_id());
		Ranks r = rankService.findById(us.getRanks_id().getRanks_id());
		u.setPay_id(p);
		u.setRanks_id(r);
		userService.update(u);
		ss.setAttribute("user", u);
		return "redirect:/home/manager/profile";
	}

		// Đổi mật khẩu
	@PostMapping("/profile/changePass")
	public String ChangePassProfile(Model m, Users u, @Param("passhientai") String passhientai) {
		Users user = (Users) ss.getAttribute("user");

		String passmoi = paramService.getString("passmoi", "");
		String nhaplaipassmoi = paramService.getString("nhaplaipassmoi", "");

		System.out.println(passhientai);
		System.out.println(user.getPasswords());

		if (passwordEncoder.matches(passhientai, user.getPasswords())) {
			if (passmoi.equalsIgnoreCase(nhaplaipassmoi)) {
				u.setPasswords(passwordEncoder.encode(passmoi));// db
				user.setPasswords(passwordEncoder.encode(passmoi));// session
				userService.update(user);
				ss.setAttribute("visible", "true");
				ss.setAttribute("thongbao", "Thành công");
				return "redirect:/home/manager/profile";
			} else {
				m.addAttribute("errorPass", true);
				return "home/profile_pass";
			}
		} else {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Sai mật khẩu củ");
			return "home/profile_pass";
		}
	}
		// Đổi mật khẩu
	// Cập nhật thông tin tài khoản

	// Đăng xuất
	@RequestMapping("/logout/success")
	public String logoutSuccess() {
		ss.removeAttribute("user");
		ss.removeAttribute("user");
		return "redirect:/login";
	}
	// Đăng xuất

	// Quên mật khẩu
	@GetMapping("/forget-password")
	public String getForgetPassword() {
		return "account/forgetPassword";
	}
	
	//RESEND OTP
	@GetMapping("/resend-otp")
	public String resendOTP(Model m) throws MessagingException {
		String mail_reOTP = ss.getAttribute("resendOTP");
		if (validator.isEmailValid(mail_reOTP)) {
			Users uFind = userService.findByEmailOrPhone(mail_reOTP, null);
			if (uFind == null) {
				// Set Notification Failed
				m.addAttribute("visible", "true");
				m.addAttribute("thongbao", "Email/SĐT không tồn tài!");
				return "account/forgetPassword";
			} else {
				// Send ID OTP Email
				Random random = new Random();
				StringBuilder stringNumber = new StringBuilder();
				for (int i = 0; i < 4; i++) {
					int numberRandom = random.nextInt(10);

					stringNumber.append(numberRandom);
				}
				String title = "Dịch Vụ Tài Khoản";
				String body = stringNumber.toString();
				ss.setAttribute("UFind", uFind);
				ss.setAttribute("otp", body);
				ss.setAttribute("resendOTP", mail_reOTP);
				
				mailService.send(mail_reOTP, title, body);
				return "redirect:/OTP";
			}
		} else {
			Users uFind = userService.findByEmailOrPhone(null, mail_reOTP);
			if (uFind == null) {
				// Set Notification Failed
				m.addAttribute("visible", "true");
				m.addAttribute("thongbao", "Email/SĐT không tồn tài!");
				return "account/forgetPassword";
			} else {
				// Send ID OTP Phone
				Random random = new Random();
				StringBuilder stringNumber = new StringBuilder();
				for (int i = 0; i < 4; i++) {
					int numberRandom = random.nextInt(10);

					stringNumber.append(numberRandom);
				}
				String body = "Mã xác thực Real Estate của bạn là: " + stringNumber.toString();
				ss.setAttribute("UFind", uFind);
				ss.setAttribute("otp", body);
				ss.setAttribute("resendOTP", mail_reOTP);
				
				String phone = "+84" + mail_reOTP.substring(1);
				//System.out.println(phone);
				smsService.sendSms(phone, body);
				return "redirect:/OTP";
			}
		}
	} 
		// Gửi mail xác nhận
	@PostMapping("/forget-password-action")
	public String getForgetPasswordAction(Model m, @RequestParam("email") String email) throws Exception {
		if (validator.isEmailValid(email)) {
			Users uFind = userService.findByEmailOrPhone(email, null);
			if (uFind == null) {
				// Set Notification Failed
				m.addAttribute("visible", "true");
				m.addAttribute("thongbao", "Email/SĐT không tồn tài!");
				return "account/forgetPassword";
			} else {
				// Send ID OTP Email
				Random random = new Random();
				StringBuilder stringNumber = new StringBuilder();
				for (int i = 0; i < 4; i++) {
					int numberRandom = random.nextInt(10);

					stringNumber.append(numberRandom);
				}
				String title = "Dịch Vụ Tài Khoản";
				String body = stringNumber.toString();
				ss.setAttribute("UFind", uFind);
				ss.setAttribute("otp", body);
				ss.setAttribute("resendOTP", email);
				ss.setAttribute("type", "email của bạn " + email.substring(0, email.length() - 14) + "******");
				mailService.send(email, title, body);
				return "redirect:/OTP";
			}
		} else {
			Users uFind = userService.findByEmailOrPhone(null, email);
			if (uFind == null) {
				// Set Notification Failed
				m.addAttribute("visible", "true");
				m.addAttribute("thongbao", "Email/SĐT không tồn tài!");
				return "account/forgetPassword";
			} else {
				// Send ID OTP Phone
				Random random = new Random();
				StringBuilder stringNumber = new StringBuilder();
				for (int i = 0; i < 4; i++) {
					int numberRandom = random.nextInt(10);

					stringNumber.append(numberRandom);
				}
				String body = "Mã xác thực Real Estate của bạn là: " + stringNumber.toString();
				ss.setAttribute("UFind", uFind);
				ss.setAttribute("otp", body);
				ss.setAttribute("resendOTP", email);
				String phone = "+84" + email.substring(1);
				ss.setAttribute("type", "sms qua số điện thoại " + phone.substring(0, phone.length() - 4) + "****");
				//System.out.println(phone);
				smsService.sendSms(phone, body);
				return "redirect:/OTP";
			}
		}
		
	}
		// Gửi mail xác nhận
	// Quên mật khẩu

	// OTP
	@GetMapping("/OTP")
	public String getOTP() {
		return "account/otp";
	}

	@PostMapping("/OTP/action")
	public String postOTP(Model m, @RequestParam("so1") String so1, @RequestParam("so2") String so2,
			@RequestParam("so3") String so3, @RequestParam("so4") String so4) {
		String OTP = so1 + so2 + so3 + so4;
		String OTPss = ss.getAttribute("otp");
		if (OTPss.equalsIgnoreCase(OTP)) {
			return "redirect:/change-password";
		} else {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Sai OTP");
			return "account/otp";
		}
	}
	// OTP

	// Đổi mật khẩu
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "account/changePassword";
	}

	@PostMapping("/change-password-post")
	public String postChangePassword(Model m, @RequestParam("mkmoi") String mkmoi,
			@RequestParam("nhaplaimkmoi") String nhaplaimkmoi) {
		Users u = ss.getAttribute("UFind");
		if (!nhaplaimkmoi.equalsIgnoreCase(mkmoi)) {
			m.addAttribute("visible", "true");
			m.addAttribute("thongbao", "Kiểm tra lại mật khẩu");
			return "account/changePassword";
		} else {
			if (passwordEncoder.matches(mkmoi, u.getPasswords())) {
				m.addAttribute("visible", "true");
				m.addAttribute("thongbao", "Vui lòng nhập mật khẩu chưa từng sử dụng!");
				return "account/changePassword";
			}else {
				u.setFail_login(0);
				u.setPasswords(passwordEncoder.encode(mkmoi));
				userService.update(u);
				return "redirect:/login";
			}
		}
	}
	// Đổi mật khẩu
}
