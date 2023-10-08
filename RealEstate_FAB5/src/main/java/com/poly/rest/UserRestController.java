package com.poly.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
	UsersService uService;
	
	@Autowired
	SessionService ss;
	
	@RequestMapping("/rest/user")
	public Users getUser() {
		Users u = (Users) ss.getAttribute("user");
		return uService.findById(u.getUsername());
	}
}
