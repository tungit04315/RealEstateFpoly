package com.poly.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.bean.Users;
import com.poly.service.UsersService;
import com.poly.util.MailerService;

@RestController
public class MailHappyBirthdayRestController {

	@Autowired
	UsersService uService;
	
	@Autowired
	MailerService mailService;
	
	@GetMapping("/send-mail-happy-birthday")
	public String send() throws MessagingException {
		Users u = uService.findById("tungngayngo");
		Date now = new Date();
		Date birthday = u.getBirthday();
		SimpleDateFormat x =  new SimpleDateFormat ("dd/MM/yyyy");
		
		String formattedBirthday = x.format(birthday);
		String formattedNow = x.format(now);
		
		
		String[] partsBirthday = formattedBirthday.split("/");
		String[] partsNow = formattedNow.split("/");

		int dayBirthday = Integer.parseInt(partsBirthday[0]);
		int monthBirthday = Integer.parseInt(partsBirthday[1]);
		int yearBirthday = Integer.parseInt(partsBirthday[2]);
		
		int dayNow = Integer.parseInt(partsNow[0]);
		int monthNow = Integer.parseInt(partsNow[1]);
		int yearNow = Integer.parseInt(partsNow[2]);

		int age = yearNow - yearBirthday;
		
		if (dayBirthday == dayNow && monthBirthday == monthNow) {
			
			mailService.sendMailHappyBirthday(u.getEmail(), "Happy Birthday", u.getFullname(), String.valueOf(age));
			return "Sinh nhật cùng ngày và tháng." + age;
		} else {
			return "Sinh nhật không cùng ngày và tháng.";
		}

	}
}
