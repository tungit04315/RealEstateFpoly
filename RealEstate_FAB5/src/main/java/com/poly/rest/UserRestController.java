package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.Users;
import com.poly.service.UsersService;
import com.poly.util.SessionService;

@CrossOrigin("*")
@RestController
public class UserRestController {

	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	UsersService uService;
	
	@Autowired
	SessionService ss;
	
	@RequestMapping("/rest/user")
	public Users getUser() {
		Users u = (Users) ss.getAttribute("user");
		return uService.findById(u.getUsername());
	}
	
	@RequestMapping("/login-test")
	public String Users() {
		
		Users u = uService.findById("user1");
		
		u.setPasswords(pe.encode(u.getPasswords()));
		
		uService.create(u);
		//userS1@
		return u.getPasswords();
	}
	
	@RequestMapping("/login-test-2")
	public String UsersMatches() {
		
		Users u = uService.findById("user1");
		
		if(pe.matches("userS1@", u.getPasswords())) {
			return u.getPasswords();
		}else {
			return "Failed";
		}
		//userS1@
		
	}
}
