package com.poly.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.poly.service.BirthdayEmailService;

public class BirthdayScheduler {

	@Autowired
	BirthdayEmailService birthdayService;
	
//	@Scheduled(cron = "0 12 21 * * ?")
//    public void sendBirthdayEmails() {
//		System.out.println("VO");
//        birthdayService.sendEmailBirthday();
//    }
}
