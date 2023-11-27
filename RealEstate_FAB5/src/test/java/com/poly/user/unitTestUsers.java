package com.poly.user;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.poly.bean.Users;
import com.poly.service.UsersService;
import com.twilio.rest.chat.v1.service.User;

@SpringBootTest
public class unitTestUsers {
	@Autowired
	private UsersService userService;
	private List<Users> users;
	
	@BeforeEach
	public void setUp() {
		users = userService.findAll();
	}
	
	@Test
	public void testFill() {	
		Assertions.assertThat(users).isNotNull();
	}
	
	@Test
	public void testFindID() {
		String username = "tuong";
		Users users = userService.findById(username);
		Assertions.assertThat(users).isNotNull();
	}
	
	@Test
	public void testCreateUser() {
		String username = "tuong123";
		String pass = "@Tuong123";
		String email = "tuong@gmail.com";
		String phone = "0332393773";
		
		Users u = new Users();
		u.setUsername(username);
		u.setPasswords(pass);
		u.setEmail(email);
		u.setPhone(phone);
		
		Assertions.assertThat(userService.create(u)).isNotNull();
	}
	
}
