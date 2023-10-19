package com.poly.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.poly.bean.MailHappyBirthday;
import com.poly.bean.MailInfo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailerService{

	@Autowired
	JavaMailSender sender;
	
	@Autowired
	TemplateEngine templateEngine;

	
	public void send(MailInfo mail) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		
		Context context = new Context();
        context.setVariable("content", mail.getBody());
        String html = templateEngine.process("email/index", context);

		
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(html, true);
		helper.setReplyTo(mail.getFrom());

		String[] cc = mail.getCc();
		if (cc != null && cc.length > 0) {
			helper.setCc(cc);
		}

		String[] bcc = mail.getBcc();
		if (bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}

		String[] attachments = mail.getAttachments();
		if (attachments != null && attachments.length > 0) {
			for (String path : attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}

		sender.send(message);
	}
	
	public void sendHappyBirthday(MailHappyBirthday mail) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		
		Context context = new Context();
		context.setVariable("fullname", mail.getFullName());
		context.setVariable("old", mail.getOld());
        String html = templateEngine.process("email/emailHappyBirthday", context);

		
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(html, true);
		helper.setReplyTo(mail.getFrom());

		String[] cc = mail.getCc();
		
		if (cc != null && cc.length > 0) {
			helper.setCc(cc);
		}

		String[] bcc = mail.getBcc();
		if (bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}

		String[] attachments = mail.getAttachments();
		if (attachments != null && attachments.length > 0) {
			for (String path : attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}

		sender.send(message);
	}

	
	public void send(String to, String subject, String body) throws MessagingException {
		this.send(new MailInfo(to, subject, body));
	}
	
	public void sendMailHappyBirthday(String to, String subject, String fullName, String old) throws MessagingException {
		this.sendHappyBirthday(new MailHappyBirthday(to, subject, fullName, old));
	}

	List<MailInfo> list = new ArrayList<>();

	
	public void queue(MailInfo mail) {
		list.add(mail);
	}

	
	public void queue(String to, String subject, String body) {
		queue(new MailInfo(to, subject, body));

	}

	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			MailInfo mail = list.remove(0);
			try {
				this.send(mail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
