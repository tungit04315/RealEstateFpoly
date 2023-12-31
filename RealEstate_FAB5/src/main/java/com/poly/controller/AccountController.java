package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
//Ok la
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
	@PostMapping("signup/action")
	public String Register(Model m, Users u, @Param("username") String username) {
		// System.out.println("xuất mẫu:"+userService.findById(username));
		Users ufind = userService.findById(username);
		if (ufind != null) {
			m.addAttribute("errorUsername", "true");
		} else {
			//payment
			Pay newpay = new Pay();
			newpay.setPay_money((long)0.00);
			payService.Create(newpay);
			Pay payFind =  payService.findByTop1Desc();
			
			//rank
			Ranks rank = rankService.findById(1);
			//OTP dangky, xac nhan email
			String password = paramService.getString("passwords", "");
			u.setPasswords(passwordEncoder.encode(password));
			u.setPay_id(payFind);
			u.setRanks_id(rank);
			userService.create(u);

			Auth uAuth = new Auth();
			uAuth.setUsers(u);
			uAuth.setRoles(roleService.findbyId("admin"));
			authService.create(uAuth);

			m.addAttribute("successRegister", "true");
		}
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
	public String loginError(Model model) {
		return "account/login";
	}
	@RequestMapping("/login/action/success")
	public String postLogin(Model m) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> authList = new ArrayList<>();

		// Check if the user is authenticated
		if (authentication != null && authentication.isAuthenticated()) {
			System.out.println(authentication+ "197");
			System.out.println(authentication.isAuthenticated() + "197");
			System.out.println(authentication.getName() + "197");
			List<String> roleNames = userService.getRolesByUsername(authentication.getName());
			System.out.println(roleNames);
			
			for (String roleName : roleNames) {
				authList.add("ROLE_" + roleName);
			}
		}
		System.out.println(authList);
		if (authList.contains("ROLE_admin")) {
			return "redirect:/admin";
		} else {
			return "redirect:/home";
		}
	}

//	@PostMapping("/login/action")
//	public String login(Model m, @RequestParam("username") String username, @RequestParam("passwords") String passwords) {
//		
//		Users u = userService.findById(username);
//		if(u == null) {
//			System.out.println(u);
//			return "redirect:/login/action/error";
//		}else {
//			
//			if(passwordEncoder.matches(passwords, u.getPasswords())) {
//				ss.setAttribute("user", u);
//				return "redirect:/login/action/success";
//			}else {
//				System.out.println("Password Failded");
//				return "redirect:/login/action/error";
//			}
//		}
//	}
	// Đăng nhập

	// Cập nhật thông tin tài khoản
	@PostMapping("/profile/changeprofile")
	public String ChangeProfile(Model m, Users u, @Param("username") String username) {
		userService.update(u);
		ss.setAttribute("user", u);
//		Users user = (Users) ss.getAttribute("users");
//		m.addAttribute("u", userService.findById(user.getUsername()));
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
				u.setUsername(user.getUsername());
				u.setEmail(user.getEmail());
				u.setGender(user.isGender());
				u.setPhone(user.getPhone());
				u.setFullname(user.getFullname());
				u.setAvatar(user.getAvatar());
				u.setAddresss(user.getAddresss());
				u.setFail_login(user.getFail_login());
				u.setActive(user.isActive());
				u.setCreate_block(user.getCreate_block());
				u.setRanks_id(user.getRanks_id());
				u.setPay_id(user.getPay_id());

				u.setPasswords(passwordEncoder.encode(passmoi));// db
				user.setPasswords(passwordEncoder.encode(passmoi));// session
				userService.update(user);
				m.addAttribute("successPass", true);
				return "redirect:/home/manager/profile";
			} else {
				m.addAttribute("errorPass", true);
				return "redirect:/home/pay";
			}
		} else {
			m.addAttribute("errorPass", true);
			return "redirect:/home";
		}
	}
	// Đổi mật khẩu
	// Cập nhật thông tin tài khoản

	// Đăng xuất
	@RequestMapping("/logout/success")
	public String logoutSuccess() {
		return "redirect:/login";
	}
	// Đăng xuất

	// Quên mật khẩu
	@GetMapping("/forget-password")
	public String getForgetPassword() {
		return "account/forgetPassword";
	}

	// Gửi mail xác nhận
	@PostMapping("/forget-password-action")
	public String getForgetPasswordAction(Model m, @RequestParam("email") String email) throws Exception {
		if (validator.isEmailValid(email)) {
			Users uFind = userService.findByEmailOrPhone(email, null);
			if (uFind == null) {
				// Set Notification Failed
				m.addAttribute("notitication", false);
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

				mailService.send(email, title, body);
				return "redirect:/OTP";
			}
		} else {
			Users uFind = userService.findByEmailOrPhone(null, email);
			if (uFind == null) {
				// Set Notification Failed
				return "redirect:/forget-password";
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

				String phone = "+84" + email.substring(1);
				System.out.println(phone);
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
			return "redirect:/OTP";
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
			System.out.println("mk nhap lai khong trung");
			return "redirect:/change-password";
		} else {
			if (passwordEncoder.matches(mkmoi, u.getPasswords())) {
				System.out.println("mk moi trung vs mk cu");
				return "redirect:/change-password";
			}else {
				u.setPasswords(passwordEncoder.encode(mkmoi));
				userService.update(u);
				return "redirect:/login";
			}
		}
	}
	// Đổi mật khẩu
}
