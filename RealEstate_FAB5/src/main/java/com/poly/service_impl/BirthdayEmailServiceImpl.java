package com.poly.service_impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.poly.bean.Users;
import com.poly.service.BirthdayEmailService;
import com.poly.service.UsersService;
import com.poly.util.MailerService;

@Service
public class BirthdayEmailServiceImpl implements BirthdayEmailService {

	@Autowired
	UsersService uService;

	@Autowired
	MailerService mailService;

	@Scheduled(cron = "0 0 8 * * ?")
	@Override
	public void sendEmailBirthday() {
		List<Users> users = uService.findAll();

		for (Users u : users) {
			Date now = new Date();
			Date birthday = u.getBirthday();
			SimpleDateFormat x = new SimpleDateFormat("dd/MM/yyyy");

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
				try {
					mailService.sendMailHappyBirthday(u.getEmail(), "Happy Birthday", u.getFullname(),
							String.valueOf(age));
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
